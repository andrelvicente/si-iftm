"""
Analise de agrupamento K-Means na base Iris sem usar Weka.

Gera:
- resultados textuais para os cenarios solicitados;
- imagens para visualizacao dos grupos.
"""

from pathlib import Path

import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
from sklearn.cluster import KMeans
from sklearn.datasets import load_iris
from sklearn.decomposition import PCA
from sklearn.metrics import silhouette_score
from sklearn.preprocessing import MinMaxScaler


def executar_kmeans(x: np.ndarray, k: int, titulo: str) -> dict:
    modelo = KMeans(n_clusters=k, random_state=42, n_init=20)
    labels = modelo.fit_predict(x)

    unicos, contagens = np.unique(labels, return_counts=True)
    distribuicao = dict(zip(unicos.tolist(), contagens.tolist()))
    silhueta = silhouette_score(x, labels)

    print(f"\n=== {titulo} | K={k} ===")
    print(f"Numero de grupos gerados: {len(unicos)}")
    print(f"Distribuicao dos objetos por grupo: {distribuicao}")
    print(f"Silhouette: {silhueta:.4f}")
    print("Centroide(s):")
    for i, centro in enumerate(modelo.cluster_centers_):
        valores = ", ".join(f"{v:.4f}" for v in centro)
        print(f"  C{i}: [{valores}]")

    return {
        "titulo": titulo,
        "k": k,
        "labels": labels,
        "centroides": modelo.cluster_centers_,
        "distribuicao": distribuicao,
        "silhouette": silhueta,
    }


def plotar_clusters_pca(
    x: np.ndarray, labels: np.ndarray, titulo: str, arquivo_saida: Path
) -> None:
    pca = PCA(n_components=2, random_state=42)
    x_pca = pca.fit_transform(x)

    plt.figure(figsize=(8, 6))
    dispersao = plt.scatter(
        x_pca[:, 0],
        x_pca[:, 1],
        c=labels,
        cmap="tab10",
        s=55,
        alpha=0.85,
        edgecolors="k",
        linewidth=0.25,
    )
    plt.title(titulo)
    plt.xlabel("Componente principal 1")
    plt.ylabel("Componente principal 2")
    plt.grid(alpha=0.25)
    plt.legend(*dispersao.legend_elements(), title="Cluster")
    plt.tight_layout()
    plt.savefig(arquivo_saida, dpi=140)
    plt.close()


def plotar_relacao_petalas(
    x: np.ndarray, labels: np.ndarray, titulo: str, arquivo_saida: Path
) -> None:
    plt.figure(figsize=(8, 6))
    plt.scatter(
        x[:, 2],  # petal length
        x[:, 3],  # petal width
        c=labels,
        cmap="tab10",
        s=55,
        alpha=0.85,
        edgecolors="k",
        linewidth=0.25,
    )
    plt.title(titulo)
    plt.xlabel("petal length")
    plt.ylabel("petal width")
    plt.grid(alpha=0.25)
    plt.tight_layout()
    plt.savefig(arquivo_saida, dpi=140)
    plt.close()


def main() -> None:
    base_dir = Path(__file__).resolve().parent
    out_dir = base_dir / "saida_visualizacao"
    out_dir.mkdir(parents=True, exist_ok=True)

    iris = load_iris()
    x = iris.data
    colunas = iris.feature_names
    df = pd.DataFrame(x, columns=colunas)

    print("Atributos disponiveis na base Iris:")
    for c in colunas:
        print(f"- {c}")

    scaler = MinMaxScaler()
    x_norm = scaler.fit_transform(x)

    # Cenarios pedidos na atividade
    r1 = executar_kmeans(x, 3, "Sem normalizacao")
    r2 = executar_kmeans(x_norm, 3, "Com normalizacao")
    r3 = executar_kmeans(x_norm, 2, "Com normalizacao")
    r4 = executar_kmeans(x_norm, 4, "Com normalizacao")

    # Salva tabela auxiliar para consulta/relatorio
    resumo = pd.DataFrame(
        [
            {
                "cenario": r1["titulo"],
                "k": r1["k"],
                "distribuicao": r1["distribuicao"],
                "silhouette": round(r1["silhouette"], 4),
            },
            {
                "cenario": r2["titulo"],
                "k": r2["k"],
                "distribuicao": r2["distribuicao"],
                "silhouette": round(r2["silhouette"], 4),
            },
            {
                "cenario": r3["titulo"],
                "k": r3["k"],
                "distribuicao": r3["distribuicao"],
                "silhouette": round(r3["silhouette"], 4),
            },
            {
                "cenario": r4["titulo"],
                "k": r4["k"],
                "distribuicao": r4["distribuicao"],
                "silhouette": round(r4["silhouette"], 4),
            },
        ]
    )
    resumo_csv = out_dir / "resumo_kmeans_iris.csv"
    resumo.to_csv(resumo_csv, index=False)

    # Visualizacoes alternativas ao Weka
    plotar_clusters_pca(
        x_norm,
        r2["labels"],
        "Iris (normalizada) - KMeans K=3 em PCA 2D",
        out_dir / "pca_k3_normalizado.png",
    )
    plotar_clusters_pca(
        x_norm,
        r4["labels"],
        "Iris (normalizada) - KMeans K=4 em PCA 2D",
        out_dir / "pca_k4_normalizado.png",
    )
    plotar_relacao_petalas(
        x,
        r1["labels"],
        "Iris sem normalizacao - petal length x petal width (K=3)",
        out_dir / "petalas_k3_sem_normalizacao.png",
    )
    plotar_relacao_petalas(
        x_norm,
        r2["labels"],
        "Iris normalizada - petal length x petal width (K=3)",
        out_dir / "petalas_k3_normalizado.png",
    )

    print("\nArquivos gerados:")
    for p in sorted(out_dir.glob("*")):
        print(f"- {p}")

    print("\nFim. Use as imagens no relatorio como alternativa de visualizacao sem Weka.")


if __name__ == "__main__":
    main()
