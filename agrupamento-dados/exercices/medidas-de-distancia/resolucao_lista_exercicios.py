"""Resolucao da lista de exercicios de medidas de distancia e agrupamento."""

from math import sqrt
from statistics import mean


def titulo(texto):
    print("\n" + "=" * 72)
    print(texto)
    print("=" * 72)


def exercicio_1():
    titulo("Exercicio 1 - Re-escala e padronizacao")
    dados = [18, 24, 30, 42, 60]
    minimo = min(dados)
    maximo = max(dados)
    amplitude = maximo - minimo

    reescala = [(x - minimo) / amplitude for x in dados]
    media = mean(dados)
    variancia = sum((x - media) ** 2 for x in dados) / len(dados)
    desvio = sqrt(variancia)
    zscores = [(x - media) / desvio for x in dados]

    print(f"Dados originais: {dados}")
    print(f"Re-escala [0,1]: {[round(x, 4) for x in reescala]}")
    print(f"Media: {media:.4f}")
    print(f"Desvio padrao (populacional): {desvio:.4f}")
    print(f"Z-scores: {[round(x, 2) for x in zscores]}")
    print("Importancia: evita que atributos de escala maior dominem as distancias.")


def distancia_euclidiana(p, q):
    return sqrt(sum((a - b) ** 2 for a, b in zip(p, q)))


def distancia_manhattan(p, q):
    return sum(abs(a - b) for a, b in zip(p, q))


def exercicio_2():
    titulo("Exercicio 2 - Distancia Euclidiana e Manhattan")
    a = (1, 2)
    b = (4, 6)
    c = (2, 3)

    de_ab = distancia_euclidiana(a, b)
    dm_ab = distancia_manhattan(a, b)
    de_ac = distancia_euclidiana(a, c)
    dm_ac = distancia_manhattan(a, c)

    print(f"A={a}, B={b}, C={c}")
    print(f"Distancia Euclidiana(A,B): {de_ab:.4f}")
    print(f"Distancia Manhattan(A,B): {dm_ab:.4f}")
    print(f"Distancia Euclidiana(A,C): {de_ac:.4f}")
    print(f"Distancia Manhattan(A,C): {dm_ac:.4f}")
    print("Mais proximo de A (em ambas): C")
    print("Metricas diferentes podem gerar agrupamentos diferentes.")


def exercicio_3():
    titulo("Exercicio 3 - Matriz de distancias para clustering")
    pontos = {"P1": (0, 0), "P2": (1, 1), "P3": (5, 5)}
    chaves = list(pontos.keys())
    matriz = {
        i: {j: distancia_euclidiana(pontos[i], pontos[j]) for j in chaves} for i in chaves
    }

    print("Matriz de distancias Euclidianas:")
    print("       " + "  ".join(f"{k:>7}" for k in chaves))
    for i in chaves:
        linha = "  ".join(f"{matriz[i][j]:7.3f}" for j in chaves)
        print(f"{i:>3}   {linha}")

    print("Primeiro grupo no hierarquico: P1 e P2 (menor distancia).")


def exercicio_4():
    titulo("Exercicio 4 - Vetores binarios: SMC e Jaccard")
    x = (1, 0, 1, 0, 1, 0)
    y = (1, 1, 0, 0, 1, 0)

    f11 = f10 = f01 = f00 = 0
    for xi, yi in zip(x, y):
        if xi == 1 and yi == 1:
            f11 += 1
        elif xi == 1 and yi == 0:
            f10 += 1
        elif xi == 0 and yi == 1:
            f01 += 1
        else:
            f00 += 1

    smc = (f11 + f00) / (f11 + f10 + f01 + f00)
    jaccard = f11 / (f11 + f10 + f01)

    print(f"x={x}")
    print(f"y={y}")
    print(f"f11={f11}, f10={f10}, f01={f01}, f00={f00}")
    print(f"SMC: {smc:.4f}")
    print(f"Jaccard: {jaccard:.4f}")
    print("Para produtos comprados, Jaccard costuma ser mais adequado.")


def exercicio_5():
    titulo("Exercicio 5 - Similaridade cosseno")
    u = (1, 2, 1)
    v = (2, 4, 2)

    produto_escalar = sum(a * b for a, b in zip(u, v))
    norma_u = sqrt(sum(a * a for a in u))
    norma_v = sqrt(sum(b * b for b in v))
    cosseno = produto_escalar / (norma_u * norma_v)

    print(f"u={u}, v={v}")
    print(f"Produto escalar: {produto_escalar}")
    print(f"||u||={norma_u:.4f}, ||v||={norma_v:.4f}")
    print(f"Similaridade cosseno: {cosseno:.4f}")
    print("Interpretacao: vetores na mesma direcao (proporcionalidade perfeita).")


def exercicio_6():
    titulo("Exercicio 6 - Correlacao e comportamento conjunto")
    x = (1, 2, 3, 4)
    y = (2, 4, 6, 8)
    print(f"x={x}")
    print(f"y={y}")
    print("Como y = 2x em todos os pontos, a correlacao e positiva maxima (r = +1).")


def exercicio_7():
    titulo("Exercicio 7 - Escolha da melhor medida para agrupamento")
    print("Atributos: idade, renda mensal, cidade, comprou premium (sim/nao).")
    print("Nao e adequado usar Euclidiana diretamente em todos os atributos.")
    print("Cuidados:")
    print("- tratar faltantes e outliers")
    print("- normalizar/padronizar numericos")
    print("- codificar categoricos (one-hot, binario)")
    print("- usar metrica para dados mistos (ex.: Gower) ou k-prototypes")


def exercicio_8():
    titulo("Exercicio 8 - Impacto da normalizacao no agrupamento")
    a = (20, 2000)
    b = (30, 2500)
    c = (21, 8000)

    d_ab = distancia_euclidiana(a, b)
    d_ac = distancia_euclidiana(a, c)

    print(f"A={a}, B={b}, C={c}")
    print(f"d(A,B)={d_ab:.2f}")
    print(f"d(A,C)={d_ac:.2f}")
    print("Sem normalizacao, B parece mais proximo de A.")
    print("Isso pode ser enganoso porque a renda domina a distancia.")


def main():
    exercicio_1()
    exercicio_2()
    exercicio_3()
    exercicio_4()
    exercicio_5()
    exercicio_6()
    exercicio_7()
    exercicio_8()


if __name__ == "__main__":
    main()
