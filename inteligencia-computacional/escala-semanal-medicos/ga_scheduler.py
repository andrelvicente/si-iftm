from __future__ import annotations

from dataclasses import dataclass
from typing import Dict, List, Literal, Tuple
import random

import numpy as np
import pandas as pd


SelectionMethod = Literal["tournament", "roulette"]


DAYS = ["Seg", "Ter", "Qua", "Qui", "Sex", "Sab", "Dom"]
SHIFTS = ["Manha", "Tarde", "Noite"]
UNITS = ["Unidade 1", "Unidade 2", "Unidade 3"]
SPECIALTIES = ["Clinica Geral", "Pediatria", "Ginecologia", "Ortopedia", "Cardiologia"]

DOCTORS_PER_UNIT_SHIFT = 3
HOURS_PER_SHIFT = 8
MAX_WEEKLY_HOURS = 40
MIN_TOTAL_PER_UNIT_SHIFT = 3


@dataclass(frozen=True)
class Doctor:
    id: int
    name: str
    specialty: str


@dataclass
class GAConfig:
    population_size: int = 120
    max_generations: int = 250
    crossover_rate: float = 0.9
    mutation_rate: float = 0.2
    elitism_size: int = 2
    tournament_size: int = 3
    selection_method: SelectionMethod = "tournament"
    stagnation_limit: int = 50
    seed: int = 42


def build_default_doctors() -> List[Doctor]:
    doctors: List[Doctor] = []
    doc_id = 0
    for specialty in SPECIALTIES:
        for i in range(1, 6):
            doctors.append(Doctor(id=doc_id, name=f"Dr(a). {specialty.split()[0]} {i}", specialty=specialty))
            doc_id += 1
    return doctors


def slot_iter():
    for day_idx in range(len(DAYS)):
        for shift_idx in range(len(SHIFTS)):
            for unit_idx in range(len(UNITS)):
                yield day_idx, shift_idx, unit_idx


class WeeklyDoctorSchedulerGA:
    def __init__(self, doctors: List[Doctor], config: GAConfig):
        self.doctors = doctors
        self.config = config
        self.rng = random.Random(config.seed)
        self.np_rng = np.random.default_rng(config.seed)
        self.generalists = [d.id for d in doctors if d.specialty == "Clinica Geral"]

        self.shape = (
            len(DAYS),
            len(SHIFTS),
            len(UNITS),
            DOCTORS_PER_UNIT_SHIFT,
        )

        self.history_best: List[float] = []
        self.history_avg: List[float] = []

    def _create_individual(self) -> np.ndarray:
        """
        Chromosome model:
        schedule[day, shift, unit, position] = doctor_id
        """
        schedule = np.zeros(self.shape, dtype=np.int16)
        n_doctors = len(self.doctors)

        for day_idx in range(len(DAYS)):
            for shift_idx in range(len(SHIFTS)):
                used_in_shift = set()
                for unit_idx in range(len(UNITS)):
                    # Guarantee at least one Clinica Geral per unit/shift.
                    general_candidates = [d for d in self.generalists if d not in used_in_shift]
                    if not general_candidates:
                        general_candidates = self.generalists[:]

                    general_doc = self.rng.choice(general_candidates)
                    schedule[day_idx, shift_idx, unit_idx, 0] = general_doc
                    used_in_shift.add(general_doc)

                    # Fill remaining 2 positions.
                    remaining_candidates = [d for d in range(n_doctors) if d not in used_in_shift]
                    if len(remaining_candidates) < 2:
                        remaining_candidates = list(range(n_doctors))
                    picks = self.rng.sample(remaining_candidates, k=2)
                    schedule[day_idx, shift_idx, unit_idx, 1] = picks[0]
                    schedule[day_idx, shift_idx, unit_idx, 2] = picks[1]
                    used_in_shift.update(picks)
        return schedule

    def _flatten_shift_sets(self, schedule: np.ndarray) -> List[set]:
        shift_sets: List[set] = []
        for day_idx in range(len(DAYS)):
            for shift_idx in range(len(SHIFTS)):
                doctor_ids = set(schedule[day_idx, shift_idx].flatten().tolist())
                shift_sets.append(doctor_ids)
        return shift_sets

    def _fitness(self, schedule: np.ndarray) -> Tuple[float, Dict[str, int]]:
        penalty_coverage = 0
        penalty_workload = 0
        penalty_consecutive = 0

        # 1) Cobertura minima por unidade e turno.
        for day_idx, shift_idx, unit_idx in slot_iter():
            assigned = schedule[day_idx, shift_idx, unit_idx]
            assigned_list = assigned.tolist()
            total_assigned = len(assigned_list)
            if total_assigned < MIN_TOTAL_PER_UNIT_SHIFT:
                penalty_coverage += (MIN_TOTAL_PER_UNIT_SHIFT - total_assigned) * 200

            has_generalist = any(self.doctors[doc_id].specialty == "Clinica Geral" for doc_id in assigned_list)
            if not has_generalist:
                penalty_coverage += 300

        # 2) Carga horaria maxima (40h por medico).
        hours_per_doctor = np.zeros(len(self.doctors), dtype=np.int16)
        for doc_id in schedule.flatten():
            hours_per_doctor[doc_id] += HOURS_PER_SHIFT

        for hours in hours_per_doctor:
            if hours > MAX_WEEKLY_HOURS:
                penalty_workload += int(hours - MAX_WEEKLY_HOURS) * 5

        # 3) Proibicao de turnos consecutivos.
        shift_sets = self._flatten_shift_sets(schedule)
        for i in range(len(shift_sets) - 1):
            consecutive = shift_sets[i].intersection(shift_sets[i + 1])
            penalty_consecutive += len(consecutive) * 120

        total = penalty_coverage + penalty_workload + penalty_consecutive
        components = {
            "coverage": penalty_coverage,
            "workload": penalty_workload,
            "consecutive": penalty_consecutive,
            "total": total,
        }
        return float(total), components

    def _evaluate_population(self, population: List[np.ndarray]):
        fitness_values = np.zeros(len(population), dtype=np.float64)
        component_cache: List[Dict[str, int]] = []
        for i, individual in enumerate(population):
            fit, components = self._fitness(individual)
            fitness_values[i] = fit
            component_cache.append(components)
        return fitness_values, component_cache

    def _select_one(self, population: List[np.ndarray], fitness_values: np.ndarray) -> np.ndarray:
        if self.config.selection_method == "roulette":
            # Lower fitness is better, invert with +1 to avoid div by zero.
            inv = 1.0 / (fitness_values + 1.0)
            probs = inv / inv.sum()
            idx = self.np_rng.choice(len(population), p=probs)
            return population[int(idx)].copy()

        # Tournament selection (default).
        idxs = self.np_rng.choice(len(population), size=self.config.tournament_size, replace=False)
        best_idx = idxs[np.argmin(fitness_values[idxs])]
        return population[int(best_idx)].copy()

    def _crossover(self, parent_a: np.ndarray, parent_b: np.ndarray) -> Tuple[np.ndarray, np.ndarray]:
        if self.rng.random() > self.config.crossover_rate:
            return parent_a.copy(), parent_b.copy()

        child_a = parent_a.copy()
        child_b = parent_b.copy()

        # Crossover by swapping day blocks (as required in assignment).
        d1, d2 = sorted(self.rng.sample(range(len(DAYS)), 2))
        child_a[d1 : d2 + 1] = parent_b[d1 : d2 + 1]
        child_b[d1 : d2 + 1] = parent_a[d1 : d2 + 1]
        return child_a, child_b

    def _mutate(self, individual: np.ndarray) -> np.ndarray:
        mutated = individual.copy()
        if self.rng.random() > self.config.mutation_rate:
            return mutated

        n_mutations = self.rng.randint(1, 6)
        for _ in range(n_mutations):
            day_idx = self.rng.randrange(len(DAYS))
            shift_idx = self.rng.randrange(len(SHIFTS))
            unit_idx = self.rng.randrange(len(UNITS))

            # Reallocate doctors on selected unit/day/shift.
            must_keep_general = self.rng.random() < 0.8
            if must_keep_general:
                general_doc = self.rng.choice(self.generalists)
                others = self.rng.sample(range(len(self.doctors)), k=2)
                mutated[day_idx, shift_idx, unit_idx] = np.array([general_doc, others[0], others[1]], dtype=np.int16)
            else:
                picks = self.rng.sample(range(len(self.doctors)), k=DOCTORS_PER_UNIT_SHIFT)
                mutated[day_idx, shift_idx, unit_idx] = np.array(picks, dtype=np.int16)
        return mutated

    def run(self):
        population = [self._create_individual() for _ in range(self.config.population_size)]
        best_individual = None
        best_fitness = float("inf")
        best_components: Dict[str, int] = {}
        stagnation = 0

        for _ in range(self.config.max_generations):
            fitness_values, component_cache = self._evaluate_population(population)
            order = np.argsort(fitness_values)
            population = [population[i] for i in order]
            fitness_values = fitness_values[order]
            component_cache = [component_cache[i] for i in order]

            current_best = float(fitness_values[0])
            self.history_best.append(current_best)
            self.history_avg.append(float(np.mean(fitness_values)))

            if current_best < best_fitness:
                best_fitness = current_best
                best_individual = population[0].copy()
                best_components = component_cache[0].copy()
                stagnation = 0
            else:
                stagnation += 1

            if stagnation >= self.config.stagnation_limit:
                break

            next_population: List[np.ndarray] = []
            elites = [population[i].copy() for i in range(min(self.config.elitism_size, len(population)))]
            next_population.extend(elites)

            while len(next_population) < self.config.population_size:
                p1 = self._select_one(population, fitness_values)
                p2 = self._select_one(population, fitness_values)
                c1, c2 = self._crossover(p1, p2)
                c1 = self._mutate(c1)
                c2 = self._mutate(c2)
                next_population.append(c1)
                if len(next_population) < self.config.population_size:
                    next_population.append(c2)
            population = next_population

        if best_individual is None:
            # Safety fallback (should not happen).
            best_individual = population[0]
            best_fitness, best_components = self._fitness(best_individual)

        return {
            "best_schedule": best_individual,
            "best_fitness": best_fitness,
            "penalties": best_components,
            "history_best": self.history_best,
            "history_avg": self.history_avg,
        }

    def schedule_to_dataframe(self, schedule: np.ndarray) -> pd.DataFrame:
        rows = []
        for day_idx, shift_idx, unit_idx in slot_iter():
            ids = schedule[day_idx, shift_idx, unit_idx].tolist()
            doctors_txt = ", ".join(f"{self.doctors[d].name} ({self.doctors[d].specialty})" for d in ids)
            rows.append(
                {
                    "Dia": DAYS[day_idx],
                    "Turno": SHIFTS[shift_idx],
                    "Unidade": UNITS[unit_idx],
                    "Medicos": doctors_txt,
                }
            )
        return pd.DataFrame(rows)

    def doctor_workload_dataframe(self, schedule: np.ndarray) -> pd.DataFrame:
        counts = np.zeros(len(self.doctors), dtype=np.int16)
        for doc_id in schedule.flatten():
            counts[doc_id] += 1
        hours = counts * HOURS_PER_SHIFT
        return pd.DataFrame(
            {
                "Medico": [d.name for d in self.doctors],
                "Especialidade": [d.specialty for d in self.doctors],
                "Turnos": counts.tolist(),
                "Horas": hours.tolist(),
                "Acima_40h": (hours > MAX_WEEKLY_HOURS).tolist(),
            }
        ).sort_values(["Horas", "Medico"], ascending=[False, True])
