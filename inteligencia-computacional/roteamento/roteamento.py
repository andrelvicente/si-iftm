"""
Exercício — Roteamento entre Cidades com Árvore de Busca
=========================================================
Algoritmos implementados: DFS | BFS | Busca de Custo Uniforme (UCS)
"""

from collections import deque
import heapq


# ─────────────────────────────────────────────
#  Grafo de exemplo do exercício
# ─────────────────────────────────────────────

GRAFO_EXEMPLO = {
    "A": [("B", 4), ("C", 2)],
    "B": [("D", 5), ("E", 10)],
    "C": [("E", 3)],
    "D": [("F", 11)],
    "E": [("D", 4), ("F", 5)],
    "F": [],
}

INICIO_EXEMPLO = "A"
DESTINO_EXEMPLO = "F"


# ─────────────────────────────────────────────
#  Utilitários de exibição
# ─────────────────────────────────────────────

LINHA = "=" * 65
SUBLINHA = "-" * 65


def cabecalho(titulo: str) -> None:
    print(f"\n{LINHA}")
    print(f"  {titulo}")
    print(LINHA)


def formatar_caminho(caminho: list[str], custo: int) -> str:
    return f"  {'  →  '.join(caminho)}   [custo: {custo} km]"


def exibir_grafo(grafo: dict, inicio: str, destino: str) -> None:
    cabecalho("MAPA DO GRAFO — Cidades e Estradas")
    nos = sorted(grafo.keys())
    print(f"\n  Nós: {', '.join(nos)}")
    print(f"  Início: {inicio}   |   Destino: {destino}\n")
    print("  Conexões e distâncias:")
    for origem in nos:
        for dst, custo in grafo[origem]:
            print(f"    {origem} → {dst}  ({custo} km)")


# ─────────────────────────────────────────────
#  Visualização da Árvore de Busca
# ─────────────────────────────────────────────

def imprimir_arvore(raiz: dict, destino: str) -> None:
    def _rec(no: dict, prefixo: str, ultimo: bool) -> None:
        conector = "└──" if ultimo else "├──"
        cidade = no["cidade"]
        custo = no["custo"]
        status = " ✓ DESTINO" if cidade == destino else ""
        print(f"{prefixo}{conector} {cidade}  (custo acum.: {custo} km){status}")
        prefixo_filho = prefixo + ("    " if ultimo else "│   ")
        filhos = no.get("filhos", [])
        for i, filho in enumerate(filhos):
            _rec(filho, prefixo_filho, i == len(filhos) - 1)

    print(f"\n  Árvore de estados explorados:\n")
    print(f"  {raiz['cidade']}  (custo acum.: {raiz['custo']} km)")
    filhos = raiz.get("filhos", [])
    for i, filho in enumerate(filhos):
        _rec(filho, "  ", i == len(filhos) - 1)


def _exibir_resultado(caminhos: list[tuple[list[str], int]], algoritmo: str, inicio: str, destino: str) -> None:
    print(f"\n  Todos os caminhos encontrados de {inicio} até {destino}:")
    print(f"  {SUBLINHA}")

    if not caminhos:
        print("  Nenhum caminho encontrado.")
        return

    caminhos_ordenados = sorted(caminhos, key=lambda x: x[1])
    melhor = caminhos_ordenados[0]

    for caminho, custo in caminhos_ordenados:
        linha = formatar_caminho(caminho, custo)
        sufixo = "  ← MELHOR" if (caminho, custo) == melhor else ""
        print(linha + sufixo)

    rota_str = "  →  ".join(melhor[0])
    custo_str = str(melhor[1])
    print(f"\n  ┌{'─' * 45}┐")
    print(f"  │  [{algoritmo}] Melhor rota encontrada:          │")
    print(f"  │                                             │")
    print(f"  │  {rota_str:<43} │")
    print(f"  │  Custo total: {custo_str} km{' ' * (29 - len(custo_str))} │")
    print(f"  └{'─' * 45}┘")


# ─────────────────────────────────────────────
#  DFS — Busca em Profundidade
# ─────────────────────────────────────────────

def dfs(grafo: dict, inicio: str, destino: str) -> None:
    cabecalho("DFS — Busca em Profundidade (Depth-First Search)")
    print("""
  Estratégia: empilha o nó mais recente e sempre
  expande o filho mais fundo antes de voltar.
  Usa uma PILHA (LIFO).
""")

    pilha = [(inicio, 0, [inicio])]
    todos_caminhos: list[tuple[list[str], int]] = []
    arvore_nos: dict[str, dict] = {inicio: {"cidade": inicio, "custo": 0, "filhos": []}}
    raiz_arvore = arvore_nos[inicio]
    passo = 0

    print(f"  {'Passo':<6} {'Nó expandido':<16} {'Caminho até aqui':<35} {'Custo'}")
    print(f"  {SUBLINHA}")

    while pilha:
        cidade, custo, caminho = pilha.pop()
        passo += 1
        caminho_str = " → ".join(caminho)
        print(f"  {passo:<6} {cidade:<16} {caminho_str:<35} {custo} km")

        if cidade == destino:
            todos_caminhos.append((caminho, custo))
            continue

        for vizinho, peso in reversed(grafo.get(cidade, [])):
            if vizinho not in caminho:
                novo_custo = custo + peso
                novo_caminho = caminho + [vizinho]
                chave = f"{vizinho}@{','.join(novo_caminho)}"
                novo_no = {"cidade": vizinho, "custo": novo_custo, "filhos": []}
                arvore_nos[chave] = novo_no

                if len(caminho) == 1:
                    raiz_arvore["filhos"].append(novo_no)
                else:
                    chave_pai = f"{caminho[-1]}@{','.join(caminho)}"
                    if chave_pai in arvore_nos:
                        arvore_nos[chave_pai]["filhos"].append(novo_no)

                pilha.append((vizinho, novo_custo, novo_caminho))

    imprimir_arvore(raiz_arvore, destino)
    _exibir_resultado(todos_caminhos, "DFS", inicio, destino)


# ─────────────────────────────────────────────
#  BFS — Busca em Largura
# ─────────────────────────────────────────────

def bfs(grafo: dict, inicio: str, destino: str) -> None:
    cabecalho("BFS — Busca em Largura (Breadth-First Search)")
    print("""
  Estratégia: explora todos os vizinhos do nível atual
  antes de descer para o próximo nível.
  Usa uma FILA (FIFO) — garante o caminho com menor
  número de arestas (não necessariamente menor custo).
""")

    fila: deque = deque([(inicio, 0, [inicio])])
    todos_caminhos: list[tuple[list[str], int]] = []
    raiz_arvore = {"cidade": inicio, "custo": 0, "filhos": []}
    arvore_nos: dict[str, dict] = {inicio: raiz_arvore}
    passo = 0

    print(f"  {'Passo':<6} {'Nó expandido':<16} {'Caminho até aqui':<35} {'Custo'}")
    print(f"  {SUBLINHA}")

    while fila:
        cidade, custo, caminho = fila.popleft()
        passo += 1
        caminho_str = " → ".join(caminho)
        print(f"  {passo:<6} {cidade:<16} {caminho_str:<35} {custo} km")

        if cidade == destino:
            todos_caminhos.append((caminho, custo))
            continue

        for vizinho, peso in grafo.get(cidade, []):
            if vizinho not in caminho:
                novo_custo = custo + peso
                novo_caminho = caminho + [vizinho]
                chave = f"{vizinho}@{','.join(novo_caminho)}"
                novo_no = {"cidade": vizinho, "custo": novo_custo, "filhos": []}
                arvore_nos[chave] = novo_no
                chave_pai = f"{caminho[-1]}@{','.join(caminho)}"
                pai = arvore_nos.get(chave_pai) or arvore_nos.get(caminho[-1])
                if pai:
                    pai["filhos"].append(novo_no)
                fila.append((vizinho, novo_custo, novo_caminho))

    imprimir_arvore(raiz_arvore, destino)
    _exibir_resultado(todos_caminhos, "BFS", inicio, destino)


# ─────────────────────────────────────────────
#  UCS — Busca de Custo Uniforme
# ─────────────────────────────────────────────

def ucs(grafo: dict, inicio: str, destino: str) -> None:
    cabecalho("UCS — Busca de Custo Uniforme (Uniform Cost Search)")
    print("""
  Estratégia: sempre expande o nó com MENOR custo
  acumulado. Usa uma FILA DE PRIORIDADE (min-heap).
  Garante o caminho de MENOR CUSTO TOTAL.
  Equivale ao algoritmo de Dijkstra para este problema.
""")

    contador = 0
    heap = [(0, contador, inicio, [inicio])]
    visitados: set[str] = set()
    todos_caminhos: list[tuple[list[str], int]] = []
    raiz_arvore = {"cidade": inicio, "custo": 0, "filhos": []}
    arvore_nos: dict = {inicio: raiz_arvore}
    passo = 0

    print(f"  {'Passo':<6} {'Custo':<8} {'Nó expandido':<16} {'Caminho até aqui':<35} {'Status'}")
    print(f"  {SUBLINHA}")

    while heap:
        custo, _, cidade, caminho = heapq.heappop(heap)
        passo += 1
        caminho_str = " → ".join(caminho)

        if cidade in visitados:
            print(f"  {passo:<6} {custo:<8} {cidade:<16} {caminho_str:<35} (ignorado — já visitado)")
            continue

        visitados.add(cidade)

        if cidade == destino:
            print(f"  {passo:<6} {custo:<8} {cidade:<16} {caminho_str:<35} ✓ DESTINO ALCANÇADO")
            todos_caminhos.append((caminho, custo))
            continue

        print(f"  {passo:<6} {custo:<8} {cidade:<16} {caminho_str:<35} expandindo...")

        for vizinho, peso in grafo.get(cidade, []):
            if vizinho not in visitados and vizinho not in caminho:
                novo_custo = custo + peso
                novo_caminho = caminho + [vizinho]
                contador += 1
                heapq.heappush(heap, (novo_custo, contador, vizinho, novo_caminho))
                chave = f"{vizinho}@{','.join(novo_caminho)}"
                novo_no = {"cidade": vizinho, "custo": novo_custo, "filhos": []}
                arvore_nos[chave] = novo_no
                chave_pai = f"{caminho[-1]}@{','.join(caminho)}"
                pai = arvore_nos.get(chave_pai) or arvore_nos.get(caminho[-1])
                if pai:
                    pai["filhos"].append(novo_no)

    imprimir_arvore(raiz_arvore, destino)
    _exibir_resultado(todos_caminhos, "UCS", inicio, destino)


# ─────────────────────────────────────────────
#  Comparativo final
# ─────────────────────────────────────────────

def _melhor_dfs(grafo, inicio, destino):
    pilha = [(inicio, 0, [inicio])]
    melhor = ([], float("inf"))
    while pilha:
        cidade, custo, caminho = pilha.pop()
        if cidade == destino:
            if custo < melhor[1]:
                melhor = (caminho, custo)
            continue
        for vizinho, peso in reversed(grafo.get(cidade, [])):
            if vizinho not in caminho:
                pilha.append((vizinho, custo + peso, caminho + [vizinho]))
    return melhor


def _melhor_bfs(grafo, inicio, destino):
    fila = deque([(inicio, 0, [inicio])])
    melhor = ([], float("inf"))
    while fila:
        cidade, custo, caminho = fila.popleft()
        if cidade == destino:
            if custo < melhor[1]:
                melhor = (caminho, custo)
            continue
        for vizinho, peso in grafo.get(cidade, []):
            if vizinho not in caminho:
                fila.append((vizinho, custo + peso, caminho + [vizinho]))
    return melhor


def _melhor_ucs(grafo, inicio, destino):
    heap = [(0, 0, inicio, [inicio])]
    visitados: set = set()
    while heap:
        custo, _, cidade, caminho = heapq.heappop(heap)
        if cidade in visitados:
            continue
        visitados.add(cidade)
        if cidade == destino:
            return (caminho, custo)
        for vizinho, peso in grafo.get(cidade, []):
            if vizinho not in visitados and vizinho not in caminho:
                heapq.heappush(heap, (custo + peso, len(heap), vizinho, caminho + [vizinho]))
    return ([], float("inf"))


def comparativo_final(grafo: dict, inicio: str, destino: str) -> None:
    cabecalho("COMPARATIVO — Todos os Algoritmos")
    algoritmos = {
        "DFS (Profundidade)": _melhor_dfs,
        "BFS (Largura)     ": _melhor_bfs,
        "UCS (Custo Unif.) ": _melhor_ucs,
    }
    print(f"\n  {'Algoritmo':<25} {'Melhor caminho encontrado':<40} {'Custo'}")
    print(f"  {SUBLINHA}")
    for nome, fn in algoritmos.items():
        caminho, custo = fn(grafo, inicio, destino)
        rota = "  →  ".join(caminho) if caminho else "(sem caminho)"
        custo_str = f"{custo} km" if custo != float("inf") else "—"
        print(f"  {nome:<25} {rota:<40} {custo_str}")
    print()


def rodar_algoritmos(grafo: dict, inicio: str, destino: str, escolha: str) -> None:
    if escolha in ("1", "4"):
        dfs(grafo, inicio, destino)
    if escolha in ("2", "4"):
        bfs(grafo, inicio, destino)
    if escolha in ("3", "4"):
        ucs(grafo, inicio, destino)
    if escolha == "4":
        comparativo_final(grafo, inicio, destino)


# ─────────────────────────────────────────────
#  Entrada dinâmica do grafo
# ─────────────────────────────────────────────

def input_str(prompt: str) -> str:
    return input(prompt).strip()


def construir_grafo_interativo() -> tuple[dict, str, str]:
    """Guia o usuário a construir um grafo personalizado."""
    print(f"""
  {SUBLINHA}
  Como definir as conexões:
    • Digite cada aresta no formato:  ORIGEM DESTINO CUSTO
    • Exemplo:  A B 4   (significa A → B com custo 4)
    • As letras podem ser palavras: SP RJ 400
    • Digite  "fim"  quando terminar
  {SUBLINHA}
""")

    grafo: dict[str, list] = {}

    while True:
        entrada = input_str("  Conexão: ").upper()
        if entrada in ("FIM", ""):
            if not grafo:
                print("  Nenhuma conexão adicionada. Tente novamente.\n")
                continue
            break

        partes = entrada.split()
        if len(partes) != 3:
            print("  Formato inválido. Use: ORIGEM DESTINO CUSTO  (ex: A B 4)\n")
            continue

        origem, destino_no, custo_str = partes
        try:
            custo = int(custo_str)
            if custo < 0:
                raise ValueError
        except ValueError:
            print("  Custo deve ser um número inteiro positivo.\n")
            continue

        if origem not in grafo:
            grafo[origem] = []
        if destino_no not in grafo:
            grafo[destino_no] = []

        # Evita aresta duplicada
        if not any(v == destino_no for v, _ in grafo[origem]):
            grafo[origem].append((destino_no, custo))
            print(f"  ✓ {origem} → {destino_no} ({custo} km) adicionado.")
        else:
            print(f"  Conexão {origem} → {destino_no} já existe, ignorada.")

    nos = sorted(grafo.keys())
    print(f"\n  Nós disponíveis: {', '.join(nos)}")

    while True:
        inicio = input_str("  Nó de INÍCIO: ").upper()
        if inicio in grafo:
            break
        print(f"  Nó '{inicio}' não encontrado. Escolha entre: {', '.join(nos)}")

    while True:
        destino = input_str("  Nó de DESTINO: ").upper()
        if destino in grafo:
            break
        print(f"  Nó '{destino}' não encontrado. Escolha entre: {', '.join(nos)}")

    return grafo, inicio, destino


def menu_algoritmo() -> str:
    print("""
  Qual algoritmo deseja executar?

    [1] DFS — Busca em Profundidade
    [2] BFS — Busca em Largura
    [3] UCS — Busca de Custo Uniforme
    [4] Todos (com comparativo final)
""")
    while True:
        escolha = input_str("  Opção: ")
        if escolha in ("1", "2", "3", "4"):
            return escolha
        print("  Opção inválida. Digite 1, 2, 3 ou 4.")


# ─────────────────────────────────────────────
#  Menu principal
# ─────────────────────────────────────────────

def menu_principal() -> None:
    print(f"\n{'=' * 65}")
    print("  ROTEAMENTO ENTRE CIDADES — Busca em Árvore")
    print(f"{'=' * 65}")
    print("""
  Como deseja definir o grafo?

    [1] Usar o exemplo do exercício  (A → B → ... → F)
    [2] Definir meu próprio grafo
""")

    while True:
        opcao = input_str("  Opção: ")
        if opcao in ("1", "2"):
            break
        print("  Opção inválida. Digite 1 ou 2.")

    if opcao == "1":
        grafo = GRAFO_EXEMPLO
        inicio = INICIO_EXEMPLO
        destino = DESTINO_EXEMPLO
    else:
        cabecalho("DEFINIÇÃO DO GRAFO")
        grafo, inicio, destino = construir_grafo_interativo()

    exibir_grafo(grafo, inicio, destino)
    escolha = menu_algoritmo()
    rodar_algoritmos(grafo, inicio, destino, escolha)

    print(f"\n{'=' * 65}")
    print("  Execução concluída.")
    print(f"{'=' * 65}\n")


# ─────────────────────────────────────────────
#  Ponto de entrada
# ─────────────────────────────────────────────

if __name__ == "__main__":
    while True:
        menu_principal()
        novamente = input_str("\n  Deseja executar novamente? [s/n]: ").lower()
        if novamente != "s":
            break
    print("\n  Até logo!\n")
