"""
Atividade Pratica - Algoritmos Particionais (Parte 2)
Tema: Influencia de Outliers no K-Means e comparacao com K-Medoids

Experimentos realizados:
1. Geracao do dataset sintetico "Blobs com Outliers"
2. K-Means k=3 apenas com pontos normais
3. K-Means k=3 com todos os dados (incluindo outliers)
4. Comparacao de centroide, SSE/Inertia e distribuicao dos clusters
5. Simulacao do comportamento do K-Medoids via sklearn_extra
"""

from __future__ import annotations

from pathlib import Path

import matplotlib.pyplot as plt
import matplotlib.patches as mpatches
import numpy as np
import pandas as pd
from sklearn.cluster import KMeans
from sklearn.datasets import make_blobs

KMEDOIDS_DISPONIVEL = True  # implementacao propria, sem dependencia externa

BASE_DIR = Path(__file__).resolve().parent
OUT_DIR = BASE_DIR / "saida_visualizacao"
OUT_DIR.mkdir(parents=True, exist_ok=True)

CSV_PATH = BASE_DIR / "dataset_blobs_com_outliers.csv"

CORES_CLUSTER = ["#2196F3", "#FF9800", "#4CAF50"]
COR_OUTLIER = "#E53935"
COR_CENTROIDE = "black"


# ---------------------------------------------------------------------------
# 1. Geracao / carregamento do dataset
# ---------------------------------------------------------------------------

def gerar_dataset() -> pd.DataFrame:
    X, y = make_blobs(
        n_samples=60,
        centers=[(-5, -5), (0, 5), (5, -4)],
        cluster_std=0.8,
        random_state=42,
    )
    outliers = np.array([
        [10, 10],
        [12, -8],
        [-10, 8],
        [0, -12],
        [15, 3],
    ])
    X_com_outliers = np.vstack([X, outliers])
    rotulos = np.concatenate([y, [-1, -1, -1, -1, -1]])

    df = pd.DataFrame(X_com_outliers, columns=["x", "y"])
    df["rotulo_original"] = rotulos
    df["tipo"] = df["rotulo_original"].apply(lambda v: "outlier" if v == -1 else "normal")
    df.to_csv(CSV_PATH, index=False)
    print(f"Dataset salvo em: {CSV_PATH}")
    return df


def carregar_dataset() -> pd.DataFrame:
    if CSV_PATH.exists():
        return pd.read_csv(CSV_PATH)
    return gerar_dataset()


# ---------------------------------------------------------------------------
# 2. K-Means
# ---------------------------------------------------------------------------

def executar_kmeans(X: np.ndarray, k: int, titulo: str) -> dict:
    modelo = KMeans(n_clusters=k, random_state=42, n_init=20)
    labels = modelo.fit_predict(X)

    unicos, contagens = np.unique(labels, return_counts=True)
    distribuicao = dict(zip(unicos.tolist(), contagens.tolist()))

    print(f"\n{'='*55}")
    print(f"  {titulo}  |  K={k}")
    print(f"{'='*55}")
    print(f"  Pontos analisados : {len(X)}")
    print(f"  Inertia (SSE)     : {modelo.inertia_:.2f}")
    print(f"  Distribuicao      : {distribuicao}")
    print("  Centroides:")
    for i, c in enumerate(modelo.cluster_centers_):
        print(f"    C{i}: ({c[0]:.4f}, {c[1]:.4f})")

    return {
        "titulo": titulo,
        "k": k,
        "labels": labels,
        "centroides": modelo.cluster_centers_,
        "inertia": modelo.inertia_,
        "distribuicao": distribuicao,
        "modelo": modelo,
    }


# ---------------------------------------------------------------------------
# 3. K-Medoids (implementacao PAM simplificada, sem dependencia externa)
# ---------------------------------------------------------------------------

def _pam_kmedoids(X: np.ndarray, k: int, random_state: int = 42, max_iter: int = 300):
    """
    Algoritmo PAM (Partitioning Around Medoids) simplificado.
    O medoid de cada cluster e sempre um ponto real do dataset, o que o
    torna menos sensivel a outliers do que o K-Means.
    """
    rng = np.random.RandomState(random_state)
    n = len(X)
    medoid_idx = rng.choice(n, size=k, replace=False)

    for _ in range(max_iter):
        # Atribuicao: cada ponto vai para o medoid mais proximo
        dists = np.array([
            [np.linalg.norm(X[i] - X[m]) for m in medoid_idx]
            for i in range(n)
        ])
        labels = np.argmin(dists, axis=1)

        # Atualizacao: para cada cluster, escolhe o ponto que minimiza a soma
        # das distancias aos demais membros do cluster
        new_medoid_idx = medoid_idx.copy()
        for c in range(k):
            membros = np.where(labels == c)[0]
            if len(membros) == 0:
                continue
            custos = [
                np.sum([np.linalg.norm(X[i] - X[j]) for j in membros])
                for i in membros
            ]
            new_medoid_idx[c] = membros[np.argmin(custos)]

        if np.all(new_medoid_idx == medoid_idx):
            break
        medoid_idx = new_medoid_idx

    # Rotulos finais
    dists = np.array([
        [np.linalg.norm(X[i] - X[m]) for m in medoid_idx]
        for i in range(n)
    ])
    labels = np.argmin(dists, axis=1)
    centers = X[medoid_idx]
    return labels, centers, medoid_idx


def executar_kmedoids(X: np.ndarray, k: int, titulo: str) -> dict | None:
    labels, centers, _ = _pam_kmedoids(X, k, random_state=42)

    unicos, contagens = np.unique(labels, return_counts=True)
    distribuicao = dict(zip(unicos.tolist(), contagens.tolist()))

    inertia = sum(
        np.sum((X[labels == i] - centers[i]) ** 2)
        for i in range(k)
    )

    print(f"\n{'='*55}")
    print(f"  {titulo}  |  K={k}")
    print(f"{'='*55}")
    print(f"  Pontos analisados : {len(X)}")
    print(f"  SSE (calculado)   : {inertia:.2f}")
    print(f"  Distribuicao      : {distribuicao}")
    print("  Medoids (pontos reais do dataset):")
    for i, c in enumerate(centers):
        print(f"    M{i}: ({c[0]:.4f}, {c[1]:.4f})")

    return {
        "titulo": titulo,
        "k": k,
        "labels": labels,
        "centroides": centers,
        "inertia": inertia,
        "distribuicao": distribuicao,
    }


# ---------------------------------------------------------------------------
# 4. Visualizacoes
# ---------------------------------------------------------------------------

def _scatter_base(ax, X, labels, centroides, titulo, mostrar_centroides=True):
    k = len(np.unique(labels))
    for i in range(k):
        mascara = labels == i
        cor = CORES_CLUSTER[i % len(CORES_CLUSTER)]
        ax.scatter(
            X[mascara, 0], X[mascara, 1],
            color=cor, alpha=0.75, s=60,
            edgecolors="white", linewidth=0.4,
            label=f"Cluster {i} ({mascara.sum()} pts)",
        )
    if mostrar_centroides:
        ax.scatter(
            centroides[:, 0], centroides[:, 1],
            color=COR_CENTROIDE, marker="*", s=250, zorder=5,
            label="Centroides",
        )
    ax.set_title(titulo, fontsize=11, fontweight="bold")
    ax.set_xlabel("x")
    ax.set_ylabel("y")
    ax.legend(fontsize=8)
    ax.grid(alpha=0.3)


def plotar_dataset_inicial(df: pd.DataFrame) -> None:
    normais = df[df["tipo"] == "normal"]
    outliers_df = df[df["tipo"] == "outlier"]

    fig, ax = plt.subplots(figsize=(8, 6))
    ax.scatter(normais["x"], normais["y"], color="#2196F3", alpha=0.75,
               s=60, edgecolors="white", linewidth=0.4, label="Pontos normais (60)")
    ax.scatter(outliers_df["x"], outliers_df["y"], color=COR_OUTLIER,
               marker="X", s=130, edgecolors="white", linewidth=0.4,
               label="Outliers (5)")
    ax.set_title("Dataset sintetico: 3 grupos + 5 outliers", fontsize=12, fontweight="bold")
    ax.set_xlabel("x")
    ax.set_ylabel("y")
    ax.legend()
    ax.grid(alpha=0.3)
    plt.tight_layout()
    plt.savefig(OUT_DIR / "01_dataset_inicial.png", dpi=140)
    plt.close()
    print(f"Imagem salva: {OUT_DIR / '01_dataset_inicial.png'}")


def plotar_comparacao_kmeans(
    X_sem: np.ndarray,
    res_sem: dict,
    X_com: np.ndarray,
    res_com: dict,
    outliers_idx: np.ndarray,
) -> None:
    fig, axes = plt.subplots(1, 2, figsize=(14, 6))

    _scatter_base(axes[0], X_sem, res_sem["labels"], res_sem["centroides"],
                  f"K-Means SEM outliers\nSSE = {res_sem['inertia']:.1f}")

    _scatter_base(axes[1], X_com, res_com["labels"], res_com["centroides"],
                  f"K-Means COM outliers\nSSE = {res_com['inertia']:.1f}")

    # Destaca outliers no grafico direito
    axes[1].scatter(
        X_com[outliers_idx, 0], X_com[outliers_idx, 1],
        color=COR_OUTLIER, marker="X", s=150, zorder=6,
        edgecolors="white", linewidth=0.4, label="Outliers",
    )
    axes[1].legend(fontsize=8)

    plt.suptitle("Comparacao K-Means k=3: com vs. sem outliers", fontsize=13, fontweight="bold")
    plt.tight_layout()
    plt.savefig(OUT_DIR / "02_comparacao_kmeans.png", dpi=140)
    plt.close()
    print(f"Imagem salva: {OUT_DIR / '02_comparacao_kmeans.png'}")


def plotar_deslocamento_centroides(
    res_sem: dict,
    res_com: dict,
    X_com: np.ndarray,
    outliers_idx: np.ndarray,
) -> None:
    """
    Exibe apenas os centroides dos dois cenarios lado a lado no mesmo espaco,
    permitindo visualizar o deslocamento causado pelos outliers.
    """
    fig, ax = plt.subplots(figsize=(9, 7))

    ax.scatter(X_com[:, 0], X_com[:, 1], color="#BDBDBD", alpha=0.35, s=40,
               edgecolors="none", label="Todos os pontos")
    ax.scatter(X_com[outliers_idx, 0], X_com[outliers_idx, 1],
               color=COR_OUTLIER, marker="X", s=150, zorder=4,
               edgecolors="white", linewidth=0.5, label="Outliers")

    for i, c in enumerate(res_sem["centroides"]):
        cor = CORES_CLUSTER[i % len(CORES_CLUSTER)]
        ax.scatter(c[0], c[1], color=cor, marker="*", s=300, zorder=5,
                   edgecolors="black", linewidth=0.6)

    for i, c in enumerate(res_com["centroides"]):
        cor = CORES_CLUSTER[i % len(CORES_CLUSTER)]
        ax.scatter(c[0], c[1], color=cor, marker="P", s=200, zorder=5,
                   edgecolors="black", linewidth=0.6)

    # Setas de deslocamento (tenta parear centroides pela proximidade)
    usados = set()
    for i, c_sem in enumerate(res_sem["centroides"]):
        dists = [
            (j, np.linalg.norm(c_sem - c_com))
            for j, c_com in enumerate(res_com["centroides"])
            if j not in usados
        ]
        if not dists:
            continue
        j, _ = min(dists, key=lambda t: t[1])
        usados.add(j)
        c_com = res_com["centroides"][j]
        ax.annotate(
            "", xy=c_com, xytext=c_sem,
            arrowprops=dict(arrowstyle="->", color="black", lw=1.4),
        )
        ax.text(
            (c_sem[0] + c_com[0]) / 2 + 0.15,
            (c_sem[1] + c_com[1]) / 2 + 0.15,
            f"{np.linalg.norm(c_sem - c_com):.2f}",
            fontsize=8, color="black",
        )

    legenda = [
        mpatches.Patch(color=CORES_CLUSTER[i], label=f"Cluster {i}") for i in range(3)
    ]
    legenda += [
        plt.Line2D([0], [0], marker="*", color="w", markerfacecolor="gray",
                   markersize=12, label="Centroide SEM outliers"),
        plt.Line2D([0], [0], marker="P", color="w", markerfacecolor="gray",
                   markersize=10, label="Centroide COM outliers"),
        plt.Line2D([0], [0], marker="X", color="w", markerfacecolor=COR_OUTLIER,
                   markersize=10, label="Outliers"),
    ]
    ax.legend(handles=legenda, fontsize=9, loc="upper left")
    ax.set_title("Deslocamento dos centroides causado pelos outliers", fontsize=12, fontweight="bold")
    ax.set_xlabel("x")
    ax.set_ylabel("y")
    ax.grid(alpha=0.3)
    plt.tight_layout()
    plt.savefig(OUT_DIR / "03_deslocamento_centroides.png", dpi=140)
    plt.close()
    print(f"Imagem salva: {OUT_DIR / '03_deslocamento_centroides.png'}")


def plotar_comparacao_kmedoids(
    X_com: np.ndarray,
    res_kmeans_com: dict,
    res_kmedoids: dict,
    outliers_idx: np.ndarray,
) -> None:
    fig, axes = plt.subplots(1, 2, figsize=(14, 6))

    _scatter_base(axes[0], X_com, res_kmeans_com["labels"],
                  res_kmeans_com["centroides"],
                  f"K-Means (com outliers)\nSSE = {res_kmeans_com['inertia']:.1f}")
    axes[0].scatter(X_com[outliers_idx, 0], X_com[outliers_idx, 1],
                    color=COR_OUTLIER, marker="X", s=150, zorder=6,
                    edgecolors="white", linewidth=0.4)

    _scatter_base(axes[1], X_com, res_kmedoids["labels"],
                  res_kmedoids["centroides"],
                  f"K-Medoids (com outliers)\nSSE = {res_kmedoids['inertia']:.1f}",
                  mostrar_centroides=False)
    axes[1].scatter(res_kmedoids["centroides"][:, 0],
                    res_kmedoids["centroides"][:, 1],
                    color=COR_CENTROIDE, marker="D", s=180, zorder=5,
                    label="Medoids")
    axes[1].scatter(X_com[outliers_idx, 0], X_com[outliers_idx, 1],
                    color=COR_OUTLIER, marker="X", s=150, zorder=6,
                    edgecolors="white", linewidth=0.4)
    axes[1].legend(fontsize=8)

    plt.suptitle("K-Means vs K-Medoids na presenca de outliers", fontsize=13, fontweight="bold")
    plt.tight_layout()
    plt.savefig(OUT_DIR / "04_kmeans_vs_kmedoids.png", dpi=140)
    plt.close()
    print(f"Imagem salva: {OUT_DIR / '04_kmeans_vs_kmedoids.png'}")


# ---------------------------------------------------------------------------
# 5. Tabela de comparacao textual
# ---------------------------------------------------------------------------

def imprimir_tabela_comparativa(res_sem: dict, res_com: dict, res_kmedoids: dict | None) -> None:
    linhas = []
    print(f"\n{'='*70}")
    print("  TABELA COMPARATIVA")
    print(f"{'='*70}")
    cabecalho = f"{'Cenario':<35} {'SSE':>12} {'Dist. clusters'}"
    print(cabecalho)
    print("-" * 70)

    for res in [res_sem, res_com]:
        dist = " | ".join(f"C{k}: {v}" for k, v in sorted(res["distribuicao"].items()))
        print(f"{res['titulo']:<35} {res['inertia']:>12.2f}  {dist}")
        linhas.append({
            "cenario": res["titulo"],
            "n_pontos": sum(res["distribuicao"].values()),
            "SSE_inertia": round(res["inertia"], 2),
            "distribuicao": str(res["distribuicao"]),
            "centroides": str([(round(c[0], 4), round(c[1], 4)) for c in res["centroides"]]),
        })

    if res_kmedoids:
        dist = " | ".join(f"C{k}: {v}" for k, v in sorted(res_kmedoids["distribuicao"].items()))
        print(f"{res_kmedoids['titulo']:<35} {res_kmedoids['inertia']:>12.2f}  {dist}")
        linhas.append({
            "cenario": res_kmedoids["titulo"],
            "n_pontos": sum(res_kmedoids["distribuicao"].values()),
            "SSE_inertia": round(res_kmedoids["inertia"], 2),
            "distribuicao": str(res_kmedoids["distribuicao"]),
            "centroides": str([(round(c[0], 4), round(c[1], 4)) for c in res_kmedoids["centroides"]]),
        })

    print("=" * 70)

    resumo = pd.DataFrame(linhas)
    resumo.to_csv(OUT_DIR / "resumo_comparativo.csv", index=False)
    print(f"\nResumo salvo em: {OUT_DIR / 'resumo_comparativo.csv'}")


def imprimir_deslocamento(res_sem: dict, res_com: dict) -> None:
    print(f"\n{'='*55}")
    print("  DESLOCAMENTO DOS CENTROIDES (sem -> com outliers)")
    print(f"{'='*55}")
    usados: set[int] = set()
    for i, c_sem in enumerate(res_sem["centroides"]):
        dists = [
            (j, np.linalg.norm(c_sem - res_com["centroides"][j]))
            for j in range(len(res_com["centroides"]))
            if j not in usados
        ]
        j, d = min(dists, key=lambda t: t[1])
        usados.add(j)
        c_com = res_com["centroides"][j]
        print(
            f"  C{i}: ({c_sem[0]:.3f}, {c_sem[1]:.3f})"
            f" -> ({c_com[0]:.3f}, {c_com[1]:.3f})"
            f"  |  deslocamento = {d:.4f}"
        )


# ---------------------------------------------------------------------------
# main
# ---------------------------------------------------------------------------

def main() -> None:
    print("\n>>> Carregando / gerando dataset...")
    df = carregar_dataset()

    normais = df[df["tipo"] == "normal"]
    outliers_df = df[df["tipo"] == "outlier"]
    outliers_idx = df[df["tipo"] == "outlier"].index.to_numpy()

    X_sem = normais[["x", "y"]].to_numpy()
    X_com = df[["x", "y"]].to_numpy()

    print(f"\nDataset: {len(df)} pontos totais")
    print(f"  Pontos normais : {len(normais)}")
    print(f"  Outliers       : {len(outliers_df)}")

    # ---- Visualizacao inicial ----
    print("\n>>> Gerando visualizacao inicial...")
    plotar_dataset_inicial(df)

    # ---- K-Means sem outliers ----
    print("\n>>> Executando K-Means SEM outliers (k=3)...")
    res_sem = executar_kmeans(X_sem, k=3, titulo="K-Means SEM outliers")

    # ---- K-Means com outliers ----
    print("\n>>> Executando K-Means COM outliers (k=3)...")
    res_com = executar_kmeans(X_com, k=3, titulo="K-Means COM outliers")

    # ---- K-Medoids com outliers (se disponivel) ----
    print("\n>>> Executando K-Medoids COM outliers (k=3)...")
    res_kmedoids = executar_kmedoids(X_com, k=3, titulo="K-Medoids COM outliers")

    # ---- Tabela comparativa ----
    imprimir_tabela_comparativa(res_sem, res_com, res_kmedoids)
    imprimir_deslocamento(res_sem, res_com)

    # ---- Visualizacoes ----
    print("\n>>> Gerando graficos comparativos...")
    plotar_comparacao_kmeans(X_sem, res_sem, X_com, res_com, outliers_idx)
    plotar_deslocamento_centroides(res_sem, res_com, X_com, outliers_idx)

    if res_kmedoids:
        plotar_comparacao_kmedoids(X_com, res_com, res_kmedoids, outliers_idx)

    print(f"\n>>> Concluido. Arquivos em: {OUT_DIR}")


if __name__ == "__main__":
    main()
