# Escala Semanal de Medicos com Algoritmo Genetico

Projeto em Python para resolver o problema do PDF "ATIVIDADE DE AG":

- 25 medicos distribuidos em 5 especialidades:
  - Clinica Geral
  - Pediatria
  - Ginecologia
  - Ortopedia
  - Cardiologia
- 3 unidades de saude
- 7 dias da semana
- 3 turnos por dia (Manha, Tarde, Noite)

## Regras de negocio implementadas

1. **Cobertura minima por turno/unidade**
   - Cada unidade em cada turno deve ter:
     - pelo menos 1 medico de Clinica Geral
     - 3 medicos no total
2. **Carga horaria maxima**
   - Cada medico pode trabalhar no maximo 40 horas semanais
   - Modelagem usada: 1 turno = 8 horas
3. **Proibicao de turnos consecutivos**
   - O mesmo medico nao pode aparecer em dois turnos consecutivos
   - Inclui transicao noite -> manha do dia seguinte

## Modelagem do AG

- **Cromossomo**: matriz `schedule[dia, turno, unidade, posicao]` com IDs de medicos
- **Fitness** (minimizacao): soma penalidades de:
  - cobertura minima
  - excesso de carga horaria
  - turnos consecutivos
- **Selecao**: torneio ou roleta
- **Crossover**: troca de blocos de dias entre dois individuos
- **Mutacao**: realocacao aleatoria de medicos em slots especificos
- **Parada**: maximo de geracoes ou estagnacao

## Interface visual (Streamlit)

O app mostra:
- penalidades finais por criterio
- grafico de evolucao da aptidao
- tabela completa da escala semanal
- tabela de carga horaria por medico
- grafico de barras de horas por medico

## Como executar

1. (Opcional) criar ambiente virtual:

```bash
python -m venv .venv
source .venv/bin/activate
```

2. Instalar dependencias:

```bash
pip install -r requirements.txt
```

3. Rodar o app:

```bash
streamlit run app.py
```

O navegador abrira automaticamente com o painel visual da escala.
