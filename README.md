# SI — IFTM

Repositório com materiais, exercícios, provas e projetos do curso de **Sistemas para Internet** do IFTM.

Cada pasta na raiz é (quase sempre) uma **disciplina**. Dentro delas você encontra aulas, listas, atividades e trabalhos. Use este README como mapa pra achar rápido o que precisa.

> Material de estudo compartilhado entre colegas. Se algo estiver incompleto ou desatualizado, é porque o semestre ainda está rolando 🙂

---

## Como navegar

1. Escolha a disciplina na lista abaixo
2. Entre na pasta correspondente
3. Leia o `README` interno (quando existir) — alguns projetos já têm instruções de como rodar

---

## Disciplinas e pastas

### [`agrupamento-dados/`](agrupamento-dados/)
**Agrupamento de Dados** — clustering, medidas de distância e algoritmos particionais.

| Subpasta | O que tem |
| --- | --- |
| `examples/` | Exemplos em Python (`dictionary`, `tuplas`) |
| `exercices/` | Exercícios: medidas de distância, K-Means / K-Medoids, análises com Iris e datasets sintéticos, visualizações e relatórios |

---

### [`db1/`](db1/)
**Banco de Dados 1** — ambiente MySQL via Docker.

| Arquivo | O que tem |
| --- | --- |
| `docker-compose.yaml` | Sobe um MySQL local na porta `3306` |

---

### [`db2/`](db2/)
**Banco de Dados 2** — SQL avançado / funções (PostgreSQL / PL/pgSQL) + MySQL via Docker.

| Arquivo | O que tem |
| --- | --- |
| `docker-compose.yaml` | MySQL local para a disciplina |
| `list3.md` | Lista 3: funções SQL (`eh_ano_atual`, `extrair_frase`, etc.) |

---

### [`desafios/`](desafios/)
Desafios e provas de **proficiência** (fora do fluxo normal das disciplinas).

| Subpasta | O que tem |
| --- | --- |
| `proficiencia-poo/` | Exercícios de POO em Java (`exercicio1` e `exercicio2`) |

---

### [`dispositivos-moveis/`](dispositivos-moveis/)
**Dispositivos Móveis** — apps com React Native e chatbot.

| Subpasta | O que tem |
| --- | --- |
| `my-app-ReactNative/` | App React Native (Expo) |
| `chatbot/` | Chatbot com `app/` (mobile) + `backend/` (Node) |

---

### [`fundamentos-si/`](fundamentos-si/)
**Fundamentos de Sistemas para Internet** — Git e GitHub.

| Arquivo | O que tem |
| --- | --- |
| `readme.md` | Anotações: comandos Git, fork, colaboração no GitHub |

---

### [`github-graphql/`](github-graphql/)
Script de coleta / tabulação via **API GraphQL do GitHub**.

| Arquivo | O que tem |
| --- | --- |
| `script_tabulacao.py` | Busca PRs na API GraphQL e organiza os dados |
| `Candidate samples.csv` | Amostra / saída tabulada |

---

### [`inteligencia-computacional/`](inteligencia-computacional/)
**Inteligência Computacional** — Perceptron, algoritmos genéticos, busca e roteamento.

| Subpasta | O que tem |
| --- | --- |
| `atividade_01/` | Perceptron em Python |
| `atividade_02/` | Continuação / variação do Perceptron |
| `escala-semanal-medicos/` | Escala de médicos com **algoritmo genético** + interface Streamlit |
| `GeradorDeHorarios-v1/` | Gerador de horários em Java (NetBeans / Ant) |
| `roteamento/` | Roteamento entre cidades com árvore de busca (A → F) |

---

### [`js/`](js/)
**JavaScript Básico** (material atual) — listas e exercícios das aulas.

| Subpasta | O que tem |
| --- | --- |
| `aulas/listas/` | Listas de exercícios (ex.: lista `01` com `ex1`…`ex10`) |
| `aulas/listas/readme.md` | Instruções / matriz da UC |

---

### [`js-avancado/`](js-avancado/)
**JavaScript Avançado** — Node, AJAX, projetos de aula e prova.

| Subpasta | O que tem |
| --- | --- |
| `aulas/` | Aulas e projetos (`aula01`, `aula02`, `projeto-aula02`, `trabalho`) |
| `atividades/` | Atividades (ex.: AJAX) |
| `desafios/` | Desafios extras |
| `prova/` | Prova (`prova01`) |

---

### [`js-old/`](js-old/)
**JavaScript** — material antigo (listas, vetores e provas de semestres anteriores).

| Subpasta | O que tem |
| --- | --- |
| `exercicios/` | Listas antigas |
| `vetores/` | Exercícios de vetores (`ex1`…`ex8`) |
| `prova-bimestral-1/` / `prova-bimestral-2/` / `prova-final/` | Provas |
| `AndreLuizVicenteSilva/` | Entrega empacotada da prova final |

Se estiver no semestre atual de JS, comece por [`js/`](js/). Esta pasta serve mais como referência / histórico.

---

### [`microservicos/`](microservicos/)
**Microsserviços** — APIs Spring Boot (Java / Maven).

| Subpasta | O que tem |
| --- | --- |
| `example/` | Exemplo inicial da disciplina |
| `atividade01/` | Atividade 01 |
| `prova01/` | Prova 01 |
| `product-api/` | Microsserviço de produtos |
| `shopping-api/` | Microsserviço de pedidos / compras (MongoDB) |

---

### [`nosql/`](nosql/)
**NoSQL** — MongoDB, consultas e projeto com dados geoespaciais.

| Item | O que tem |
| --- | --- |
| `docker-compose.yaml` | Ambiente MongoDB |
| `atividade_01.md` | Anotações / exemplos de operações (`insertOne`, etc.) |
| `megasena_queries.js` | Queries da Mega-Sena |
| `atividade-by-vitao/` | Sistema de rastreamento de entregas (MongoDB + Folium + mapas) |

---

### [`projeto-monolitico-orm/`](projeto-monolitico-orm/)
**Projeto Monolítico com ORM** — Spring Boot + JPA.

| Subpasta | O que tem |
| --- | --- |
| `sprint-init/` | Projeto inicial com CRUD de contatos (`Contato`, controller, etc.) |

---

### [`projeto-web-estatico/`](projeto-web-estatico/)
**Projeto Web Estático** — HTML / CSS (e um pouco de JS nas provas/listas).

| Subpasta | O que tem |
| --- | --- |
| `notes/` | Anotações por aula (`aula01` … `aula16`) |
| `projects/` | Listas (`lista03` … `lista12`), provas e revisão |

---

### [`sd/`](sd/)
**Sistemas Distribuídos / concorrência** — níveis de isolamento e controle de concorrência.

| Subpasta | O que tem |
| --- | --- |
| `exerciciois_niveis_isolamento/` | Spring Boot com contas otimista/pessimista, pedidos e estoque |

---

### [`sistemas-distribuidos/`](sistemas-distribuidos/)
**Sistemas Distribuídos** — exercícios iniciais em Java.

| Subpasta | O que tem |
| --- | --- |
| `exercicio01/` | Exercícios `ex01`, `ex02`, `ex03` |

---

### [`testes-automatizados/`](testes-automatizados/)
**Testes Automatizados** — testes funcionais com Selenium.

| Subpasta | O que tem |
| --- | --- |
| `Atividade8/` | App veterinário (Spring Boot + H2) + scripts Selenium IDE (cadastrar, pesquisar, excluir) |

---

## Outros arquivos na raiz

| Arquivo | O que é |
| --- | --- |
| `proficiencia_AndreLuizVicente.rar` | Arquivo compactado da prova / desafio de proficiência |
| `.gitignore` | Arquivos e pastas ignorados pelo Git |

---

## Dicas rápidas

- **Quer rodar algo com banco?** Procure `docker-compose.yaml` / `docker-compose.yml` na pasta da disciplina e rode `docker compose up -d`.
- **Projeto Java (Maven)?** Em geral: `./mvnw spring-boot:run` ou abra no IntelliJ / VS Code.
- **Projeto Node / React Native?** Entre na pasta, rode `npm install` e siga o `README` local.
- **Python?** Veja se tem `requirements.txt` e use um `venv`.

---

## Observação

Este repo é um “caderno de curso” vivo: pastas novas entram conforme as disciplinas avançam. Se for clonar pra estudar junto, foque na pasta da matéria do semestre e ignore o resto.
