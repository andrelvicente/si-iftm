import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
from sklearn.cluster import KMeans
from sklearn.datasets import load_iris
from sklearn.metrics.pairwise import euclidean_distances
from sklearn.preprocessing import MinMaxScaler


def main() -> None:
    # 1. carregar a base Iris (conforme código inicial do tutorial)
    iris = load_iris()
    df = pd.DataFrame(iris.data, columns=iris.feature_names)
    df["target"] = iris.target

    # 2. normalizar os dados
    scaler = MinMaxScaler()
    df_norm = scaler.fit_transform(df[iris.feature_names])

    # 3. calcular matriz de distâncias Euclidianas
    matriz_distancias = euclidean_distances(df_norm)
    print("Matriz de distâncias Euclidianas (primeiras 5 linhas x 5 colunas):")
    print(matriz_distancias[:5, :5])
    print()

    # 4. aplicar K-means (k=3)
    kmeans = KMeans(n_clusters=3, n_init=10, random_state=42)
    clusters = kmeans.fit_predict(df_norm)
    df["cluster"] = clusters
    print("Primeiras linhas do DataFrame com clusters:")
    print(df.head())
    print()

    # 5. visualizar os clusters (usando duas características para o plano 2D)
    x_col = "sepal length (cm)"
    y_col = "sepal width (cm)"

    plt.figure(figsize=(8, 5))
    scatter = plt.scatter(
        df[x_col],
        df[y_col],
        c=df["cluster"],
        cmap="viridis",
    )
    plt.xlabel(x_col)
    plt.ylabel(y_col)
    plt.title("Clusters encontrados pelo K-means na base Iris (k=3)")
    plt.colorbar(scatter, label="Cluster")
    plt.tight_layout()
    plt.show()

    # 6. comparação simples entre clusters e espécies reais (desafio do PDF)
    print("Contagem cruzada entre espécie real e cluster encontrado:")
    especies = pd.Categorical.from_codes(iris.target, iris.target_names)
    tabela_cruzada = pd.crosstab(especies, df["cluster"], rownames=["espécie"], colnames=["cluster"])
    print(tabela_cruzada)


if __name__ == "__main__":
    main()

