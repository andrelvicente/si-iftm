# Escala semanal de médicos com algoritmo genético

Este trabalho é uma **prova / atividade de Inteligência Computacional**: modelar o problema de **escalação semanal de médicos** como **otimização por algoritmo genético (AG)** e apresentar os resultados numa **interface web** (Streamlit).

O código resolve a montagem de uma grade **7 dias × 3 turnos × 3 unidades**, com **3 médicos por célula** (turno em uma unidade em um dia), respeitando regras de negócio codificadas como **penalidades** numa função de *fitness* a **minimizar**.

---

## Objetivo do problema

Distribuir **25 médicos** (5 por especialidade: Clínica Geral, Pediatria, Ginecologia, Ortopedia, Cardiologia) ao longo da semana de forma que:

1. **Cada unidade**, em **cada turno de cada dia**, tenha **3 médicos** e **pelo menos um de Clínica Geral**.
2. Nenhum médico ultrapasse **40 horas semanais** (cada turno conta como **8 horas**).
3. O mesmo médico **não trabalhe em dois turnos consecutivos** na sequência adotada pelo programa (inclui a passagem **noite → manhã do dia seguinte**).

Como nem sempre existe escala **perfeita** que zere todas as tensões (por exemplo, o volume total de turnos pode exigir mais horas do que 25 × 40 h permitem), o AG busca a solução com **menor soma de penalidades**.

---

## Estrutura do projeto

| Arquivo | Papel |
|---------|--------|
| `ga_scheduler.py` | Modelo dos dados (`Doctor`, `GAConfig`), classe `WeeklyDoctorSchedulerGA` com codificação, *fitness*, operadores genéticos e laço evolutivo. |
| `app.py` | Aplicação **Streamlit**: parâmetros do AG, execução, tabelas visuais, métricas e gráfico de evolução. |
| `requirements.txt` | Dependências Python (`numpy`, `pandas`, `streamlit`). |

---

## Representação: cromossomo

Cada indivíduo é um arranjo NumPy de forma:

`(7 dias, 3 turnos, 3 unidades, 3 posições)`

- **Dimensão “posição”**: os três médicos alocados naquele **dia + turno + unidade** (três inteiros: IDs dos médicos).
- Valores: identificadores numéricos dos médicos (`0` a `24` no cenário padrão).

Ou seja: o cromossomo é uma **escala completa** da semana; não há um gene isolado por “um médico”, e sim **slot por slot** da grade.

### População inicial (`_create_individual`)

Para cada par **(dia, turno)** o algoritmo percorre as três unidades e:

- Garante **um médico de Clínica Geral** na primeira posição de cada unidade, escolhido de forma a **evitar repetir o mesmo médico em duas unidades diferentes** naquele mesmo dia e turno (uso de um conjunto `used_in_shift`).
- Preenche as outras duas posições com médicos ainda não usados naquele dia/turno; se faltar candidato, relaxa o critério.

Assim, a inicialização já empurra soluções para ter generalistas e menos colisões **dentro do mesmo turno**, mas **não** garante 40 h nem ausência de turnos consecutivos; isso fica a cargo do *fitness* e da evolução.

---

## Função de fitness (minimização)

O *fitness* é a **soma de três penalidades** (quanto **menor**, melhor). Os componentes são devolvidos separadamente para exibição na interface.

### 1. Cobertura (`penalty_coverage`)

Para **cada** combinação **(dia, turno, unidade)**:

- Se houver menos de 3 médicos no slot: **`(3 - total) × 200`**.
- Se **nenhum** dos três for de **Clínica Geral**: **`+300`**.

### 2. Carga horária (`penalty_workload`)

- Conta-se quantos turnos cada médico aparece em **toda** a matriz; cada ocorrência soma **8 horas**.
- Para cada médico com horas **acima de 40**: **`(horas − 40) × 5`** (parte fracionária truncada no excesso via `int` no código).

### 3. Turnos consecutivos (`penalty_consecutive`)

- Constrói-se uma lista ordenada de **21 “blocos”** de turno: para cada dia, na ordem Manhã → Tarde → Noite, junta-se o conjunto de **todos** os médicos que trabalham **naquele dia e turno** (nas três unidades).
- Para cada par de blocos **adjacentes** na lista (incluindo **Domingo Noite** seguido de **Segunda Manhã**), conta-se quantos médicos aparecem nos **dois** blocos; cada médico assim repetido contribui com **`× 120`**.

---

## Algoritmo genético implementado

### Avaliação

Em cada geração, **todos** os indivíduos são avaliados; os valores de *fitness* ordenam a população do melhor para o pior.

### Elitismo

Os **`elitism_size`** melhores indivíduos (padrão: **2**) são copiados **intactos** para a próxima geração.

### Seleção de pais (`_select_one`)

- **Torneio** (padrão): sorteia-se **`tournament_size`** indivíduos sem reposição e escolhe-se o de **menor** *fitness*.
- **Roleta** (`selection_method == "roulette"`): probabilidades proporcionais a **`1 / (fitness + 1)`**, favorecendo *fitness* menores.

Dois pais são escolhidos independentemente para cada par de filhos.

### Crossover (`_crossover`)

- Com probabilidade **`crossover_rate`**, aplica-se cruzamento; caso contrário os filhos são cópias dos pais.
- Cruzamento: escolhem-se **dois dias** `d1` e `d2` e troca-se o **intervalo de dias** `[d1 … d2]` inteiro entre os dois pais (blocos de todas as linhas da semana nesse intervalo).

### Mutação (`_mutate`)

- Com probabilidade **`mutation_rate`**, o indivíduo pode ser alterado; caso contrário permanece igual.
- Se mutar: entre **1 e 5** vezes, escolhe-se aleatoriamente uma célula **(dia, turno, unidade)** e **substituem-se os três médicos** daquela célula.
  - Com probabilidade **0,8**, mantém-se a ideia de ter um generalista na tripla; senão sorteiam-se três médicos quaisquer.

### Critérios de parada

O laço principal para quando:

- se atinge **`max_generations`**, ou
- o **melhor *fitness* global** não melhora durante **`stagnation_limit`** gerações seguidas (padrão: **50**).

### Aleatoriedade

**`seed`** fixa os geradores `random` e NumPy para **reprodutibilidade** (no app, usa-se o padrão `42` salvo alteração no código).

---

## Parâmetros

### Na barra lateral do Streamlit (`app.py`)

| Parâmetro | Significado |
|-----------|-------------|
| **Tamanho da população** | Número de escalas (indivíduos) por geração. |
| **Máximo de gerações** | Teto de iterações evolutivas (pode parar antes por estagnação). |
| **Taxa de crossover** | Probabilidade de aplicar troca de blocos de dias entre dois pais. |
| **Taxa de mutação** | Probabilidade de aplicar mutação em cada filho após o crossover. |

### Somente no código (`GAConfig` em `ga_scheduler.py`)

| Parâmetro | Padrão | Função |
|-----------|--------|--------|
| `elitism_size` | 2 | Quantos melhores passam sem alteração para a próxima geração. |
| `tournament_size` | 3 | Tamanho do torneio na seleção por torneio. |
| `selection_method` | `"tournament"` | `"tournament"` ou `"roulette"`. |
| `stagnation_limit` | 50 | Gerações sem melhora no melhor global antes de encerrar. |
| `seed` | 42 | Semente dos geradores aleatórios. |

---

## Interface Streamlit

Ao executar o app:

1. **Grade visual**: uma tabela **turnos × dias** por **unidade** (três blocos coloridos), com os nomes e especialidades dos médicos nas células.
2. **Métricas**: penalidade total e decomposição em cobertura, carga horária e turnos consecutivos.
3. **Curva de evolução**: gráfico de linhas com o **melhor *fitness*** e a **média da população** por geração.
4. **Tabela detalhada**: todas as linhas (dia, turno, unidade, texto dos médicos).
5. **Carga por médico**: turnos na semana, horas estimadas e indicador de ultrapassar 40 h.

O botão **“Gerar Escala”** reroda o AG com os parâmetros atuais da barra lateral.

---

## Observação sobre viabilidade e penalidade de horas

O número total de **atribuições** na grade é fixo (**7 × 3 × 3 × 3 = 189** turnos individuais). Cada um conta **8 h**, logo a **soma de todas as horas** distribuídas na semana é **189 × 8** horas. O limite **25 × 40 h** impõe um teto de capacidade menor que essa soma; por isso a penalidade de **carga horária** tende a permanecer alta até que se altere o problema (por exemplo, mais médicos, menos unidades/turnos ou limite semanal diferente) ou se aceite que o modelo penaliza o excesso em vez de proibir explicitamente.

Isso não invalida o AG: ele ainda **minimiza** a soma das penalidades e explora **alternativas melhores** na fronteira cobertura × consecutivos × distribuição de horas.

---

## Como executar

1. (Opcional) Ambiente virtual:

```bash
cd inteligencia-computacional/escala-semanal-medicos
python -m venv .venv
source .venv/bin/activate   # Linux/macOS
# .venv\Scripts\activate    # Windows
```

2. Dependências:

```bash
pip install -r requirements.txt
```

3. Aplicação:

```bash
streamlit run app.py
```

O navegador abre (ou exibe o URL local) com o painel da escala e da evolução.

---

## Dependências

Ver `requirements.txt`: **NumPy**, **Pandas**, **Streamlit**.
