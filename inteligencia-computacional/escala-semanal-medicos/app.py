import pandas as pd
import streamlit as st
from html import escape

from ga_scheduler import (
    DAYS,
    SHIFTS,
    UNITS,
    GAConfig,
    WeeklyDoctorSchedulerGA,
    build_default_doctors,
)


st.set_page_config(page_title="Escala Semanal de Medicos (AG)", layout="wide")
st.title("Escala Semanal de Medicos com Algoritmo Genetico")
st.caption(
    "Cobre 25 medicos (5 especialidades), 3 unidades, 7 dias e 3 turnos por dia, "
    "com penalizacao por cobertura, carga horaria e turnos consecutivos."
)

with st.sidebar:
    st.header("Parametros essenciais do AG")
    pop_size = st.slider("Tamanho da populacao", min_value=40, max_value=300, value=120, step=10)
    max_gen = st.slider("Maximo de geracoes", min_value=50, max_value=5000, value=250, step=50)
    crossover_rate = st.slider("Taxa de crossover", min_value=0.1, max_value=1.0, value=0.9, step=0.05)
    mutation_rate = st.slider("Taxa de mutacao", min_value=0.01, max_value=1.0, value=0.2, step=0.01)

    run_button = st.button("Gerar Escala", type="primary")


def run_scheduler() -> dict:
    doctors = build_default_doctors()
    config = GAConfig(
        population_size=pop_size,
        max_generations=max_gen,
        crossover_rate=crossover_rate,
        mutation_rate=mutation_rate,
    )
    scheduler = WeeklyDoctorSchedulerGA(doctors=doctors, config=config)
    result = scheduler.run()
    result["scheduler"] = scheduler
    return result


if "result" not in st.session_state:
    st.session_state["result"] = run_scheduler()

if run_button:
    with st.spinner("Otimizando escala semanal..."):
        st.session_state["result"] = run_scheduler()

result = st.session_state["result"]
scheduler = result["scheduler"]
best_schedule = result["best_schedule"]
penalties = result["penalties"]
schedule_df = scheduler.schedule_to_dataframe(best_schedule)

st.subheader("Escala visual")


def build_grid_dataframe(df: pd.DataFrame, unit_name: str) -> pd.DataFrame:
    unit_df = df[df["Unidade"] == unit_name].copy()
    grid = pd.DataFrame(index=SHIFTS, columns=DAYS)
    for _, row in unit_df.iterrows():
        grid.loc[row["Turno"], row["Dia"]] = row["Medicos"]
    return grid


unit_colors = {
    "Unidade 1": "#dff6e4",
    "Unidade 2": "#fff6c7",
    "Unidade 3": "#d7ebff",
}


def format_cell(value: str) -> str:
    if not isinstance(value, str) or not value.strip():
        return "-"
    lines = [escape(part.strip()) for part in value.split(", ")]
    return "<br>".join(lines)


def render_schedule_table(grid_df: pd.DataFrame, unit_name: str) -> None:
    bg = unit_colors.get(unit_name, "#f5f5f5")
    html_parts = []
    html_parts.append(
        f"""
<div style="
    border: 2px solid #222;
    border-radius: 10px;
    overflow-x: auto;
    margin-bottom: 20px;
    background: {bg};
">
<table style="
    width: 100%;
    border-collapse: collapse;
    table-layout: fixed;
    font-size: 12px;
">
<thead>
<tr>
<th style="background:#111;color:#fff;border:1px solid #222;padding:8px;width:90px;">Turno</th>
"""
    )
    for day in DAYS:
        html_parts.append(
            f'<th style="background:#111;color:#fff;border:1px solid #222;padding:8px;">{escape(day)}</th>'
        )
    html_parts.append("</tr></thead><tbody>")

    for shift in SHIFTS:
        html_parts.append("<tr>")
        html_parts.append(
            f'<td style="background:#1f1f1f;color:#fff;border:1px solid #222;padding:8px;font-weight:700;">{escape(shift)}</td>'
        )
        for day in DAYS:
            html_parts.append(
                f'<td style="border:1px solid #222;padding:8px;vertical-align:top;line-height:1.35;color:#000;">{format_cell(grid_df.loc[shift, day])}</td>'
            )
        html_parts.append("</tr>")
    html_parts.append("</tbody></table></div>")
    st.markdown("".join(html_parts), unsafe_allow_html=True)


for unit in UNITS:
    st.markdown(f"#### {unit}")
    grid_df = build_grid_dataframe(schedule_df, unit)
    render_schedule_table(grid_df, unit)

col1, col2, col3, col4 = st.columns(4)
col1.metric("Penalidade total", int(penalties["total"]))
col2.metric("Cobertura minima", int(penalties["coverage"]))
col3.metric("Carga horaria", int(penalties["workload"]))
col4.metric("Turnos consecutivos", int(penalties["consecutive"]))

st.subheader("Curva de evolucao")
history_best = result["history_best"]
history_avg = result["history_avg"]
if history_best and history_avg and len(history_best) == len(history_avg):
    evolution_df = pd.DataFrame(
        {
            "Melhor fitness": history_best,
            "Media da populacao": history_avg,
        },
        index=range(1, len(history_best) + 1),
    )
    evolution_df.index.name = "Geracao"
    st.caption("Fitness total (soma das penalidades): quanto menor, melhor. Duas series por geracao: melhor individuo e media da populacao.")
    st.line_chart(evolution_df)
else:
    st.warning("Historico de geracoes indisponivel para plotar.")

st.subheader("Escala semanal por unidade/turno")
st.dataframe(schedule_df, use_container_width=True, hide_index=True)

st.subheader("Carga horaria por medico")
workload_df = scheduler.doctor_workload_dataframe(best_schedule)
st.dataframe(workload_df, use_container_width=True, hide_index=True)

st.info(
    "Regras cobertas no fitness: "
    "(1) minimo 1 Clinica Geral e 3 medicos por unidade/turno; "
    "(2) maximo de 40 horas semanais por medico; "
    "(3) proibicao de turnos consecutivos, incluindo noite-manha do dia seguinte."
)
