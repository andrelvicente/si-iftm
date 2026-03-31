import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
from sklearn.preprocessing import MinMaxScaler
from sklearn.cluster import KMeans
from sklearn.metrics.pairwise import euclidean_distances


def main() -> None:
    # 1. criar a tabela com 5 clientes contendo idade e salário
    clientes = pd.DataFrame(
        {
            "idade": [20, 23, 27, 45, 50],
            "salario": [1800, 2100, 2500, 7000, 8500],
        }
    )
    print("Tabela de clientes:")
    print(clientes)
    print()

    # 2. normalizar (re-escala MinMax) conforme estrutura do tutorial
    scaler = MinMaxScaler()
    dados_norm = scaler.fit_transform(clientes)
    print("Dados normalizados (idade, salário):")
    print(dados_norm)
    print()

    # 3. calcular matriz de distâncias Euclidianas entre todos os pares
    matriz_distancias = euclidean_distances(dados_norm)
    print("Matriz de distâncias Euclidianas entre os clientes (dados normalizados):")
    print(matriz_distancias)
    print()

    # 4. aplicar k-means com 2 grupos
    kmeans = KMeans(n_clusters=2, random_state=42, n_init=10)
    grupos = kmeans.fit_predict(dados_norm)
    clientes["grupo"] = grupos
    print("Clientes com rótulo de grupo (k-means, k=2):")
    print(clientes)
    print()

    # 5. exibir o resultado em gráfico
    plt.figure(figsize=(8, 5))
    scatter = plt.scatter(
        clientes["idade"],
        clientes["salario"],
        c=clientes["grupo"],
        cmap="viridis",
    )
    plt.xlabel("Idade")
    plt.ylabel("Salário")
    plt.title("Agrupamento de clientes (k-means, k=2)")
    plt.colorbar(scatter, label="Grupo")
    plt.tight_layout()
    plt.show()


if __name__ == "__main__":
    main()

