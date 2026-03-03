import csv
import random
from pathlib import Path

dados_pesquisa = [
    {'id_cliente': 1, 'idade': 35, 'genero': 'Feminino', 'escolaridade': 'Superior', 'renda_mensal': 5200.0, 'avaliacao': 4, 'cidade': 'Uberlândia'},
    {'id_cliente': 2, 'idade': -42, 'genero': 'Masculino', 'escolaridade': 'Médio', 'renda_mensal': 4800.0, 'avaliacao': 5, 'cidade': 'Uberlândia'},
    {'id_cliente': 3, 'idade': '28', 'genero': 'Feminino', 'escolaridade': None, 'renda_mensal': None, 'avaliacao': None, 'cidade': 'Ituiutaba'},
    {'id_cliente': 4, 'idade': 55, 'genero': 'Masculino', 'escolaridade': 'Superior', 'renda_mensal': 12000.0, 'avaliacao': 3, 'cidade': 'Patos de Minas'},
    {'id_cliente': 5, 'idade': 150, 'genero': 'Feminino', 'escolaridade': 'Mestrado', 'renda_mensal': 999999.0, 'avaliacao': 5, 'cidade': 'Uberaba'},
    {'id_cliente': 6, 'idade': 45, 'genero': 'Não-binário', 'escolaridade': 'Superior', 'renda_mensal': 6500.0, 'avaliacao': 4, 'cidade': 'Uberlândia'},
    {'id_cliente': 7, 'idade': 33, 'genero': 'Masculino', 'escolaridade': 'Fundamental', 'renda_mensal': 2100.0, 'avaliacao': None, 'cidade': 'Araguari'},
    {'id_cliente': 8, 'idade': 68, 'genero': 'Feminino', 'escolaridade': 'Médio', 'renda_mensal': 3200.0, 'avaliacao': 2, 'cidade': 'Uberlândia'},
    {'id_cliente': 9, 'idade': 22, 'genero': 'Feminino', 'escolaridade': 'Superior', 'renda_mensal': -100.0, 'avaliacao': 4, 'cidade': 'Uberlândia'},
    {'id_cliente': 10, 'idade': None, 'genero': 'Masculino', 'escolaridade': 'Médio', 'renda_mensal': 4100.0, 'avaliacao': 3, 'cidade': 'Ituiutaba'},
    {'id_cliente': 11, 'idade': 41, 'genero': 'Masculino', 'escolaridade': 'Doutorado', 'renda_mensal': 18000.0, 'avaliacao': 5, 'cidade': 'Uberlândia'},
    {'id_cliente': 12, 'idade': 29, 'genero': 'Feminino', 'escolaridade': 'Superior', 'renda_mensal': 5600.0, 'avaliacao': 4, 'cidade': 'Uberaba'},
]

# 1) INSPEÇÃO E DIAGNÓSTICO
print("=" * 60)
print("1) INSPEÇÃO E DIAGNÓSTICO")
print("=" * 60)
print("Registros brutos:")
for r in dados_pesquisa:
    print(r)

# Problemas: (1) id 2 idade -42 ruído (2) id 3 tipo incorreto + ausentes (3) id 5 outlier (4) id 7 avaliacao ausente (5) id 9 renda ruído (6) id 10 idade ausente.
# Atributos: nominal (genero, cidade); ordinal (escolaridade); numérico (idade, renda, avaliacao). Representar errado distorce médias/contagens/ordem.

# 2) FUNÇÕES DE CONVERSÃO E VALIDAÇÃO

def to_int_safe(x):
    if x is None:
        return None
    if isinstance(x, int) and not isinstance(x, bool):
        return x
    try:
        return int(float(x))
    except (TypeError, ValueError):
        return None


def to_float_safe(x):
    if x is None:
        return None
    if isinstance(x, (int, float)) and not isinstance(x, bool):
        return float(x)
    try:
        return float(x)
    except (TypeError, ValueError):
        return None


def registro_valido(registro):
    """True se idade em [0,120] e renda em [0,50000] (após conversão segura)."""
    idade = to_int_safe(registro.get('idade'))
    renda = to_float_safe(registro.get('renda_mensal'))
    if idade is None or idade < 0 or idade > 120:
        return False
    if renda is None or renda < 0 or renda > 50000:
        return False
    return True


# 3) LIMPEZA
print("\n" + "=" * 60)
print("3) LIMPEZA")
print("=" * 60)

removidos_idade = 0
removidos_renda = 0
removidos_ambos = 0
dados_limpos = []

for r in dados_pesquisa:
    idade = to_int_safe(r.get('idade'))
    renda = to_float_safe(r.get('renda_mensal'))
    ok_idade = idade is not None and 0 <= idade <= 120
    ok_renda = renda is not None and 0 <= renda <= 50000

    if registro_valido(r):
        copia = dict(r)
        copia['idade'] = idade
        copia['renda_mensal'] = renda
        dados_limpos.append(copia)
    else:
        if not ok_idade and not ok_renda:
            removidos_ambos += 1
        elif not ok_idade:
            removidos_idade += 1
        else:
            removidos_renda += 1

total_removidos = len(dados_pesquisa) - len(dados_limpos)
print(f"Registros originais: {len(dados_pesquisa)}")
print(f"Registros após limpeza: {len(dados_limpos)}")
print(f"Total removidos: {total_removidos}")
print(f"  - por idade inválida (apenas): {removidos_idade}")
print(f"  - por renda inválida (apenas): {removidos_renda}")
print(f"  - por idade e renda inválidas: {removidos_ambos}")

# 4) IMPUTAÇÃO — avaliacao=média, escolaridade=moda, renda=mediana. Mediana para renda (robusta a outliers); média para avaliação (escala limitada).

def imputar_ausentes(dados):
    avaliacoes = [r.get('avaliacao') for r in dados if r.get('avaliacao') is not None]
    media_avaliacao = sum(avaliacoes) / len(avaliacoes) if avaliacoes else 3.0
    media_avaliacao = max(1, min(5, round(media_avaliacao)))

    esc_count = {}
    for r in dados:
        e = r.get('escolaridade')
        if e is not None and e != '':
            esc_count[e] = esc_count.get(e, 0) + 1
    moda_escolaridade = max(esc_count, key=esc_count.get) if esc_count else 'Superior'

    rendas = sorted([r.get('renda_mensal') for r in dados if r.get('renda_mensal') is not None])
    n = len(rendas)
    if n == 0:
        mediana_renda = 5000.0
    elif n % 2 == 1:
        mediana_renda = rendas[n // 2]
    else:
        mediana_renda = (rendas[n // 2 - 1] + rendas[n // 2]) / 2

    for r in dados:
        if r.get('avaliacao') is None:
            r['avaliacao'] = media_avaliacao
        if r.get('escolaridade') is None or r.get('escolaridade') == '':
            r['escolaridade'] = moda_escolaridade
        if r.get('renda_mensal') is None:
            r['renda_mensal'] = mediana_renda

    return dados


dados_limpos = imputar_ausentes(dados_limpos)

# 5) DISCRETIZAÇÃO + ORDINAL

MAP_ESCOLARIDADE = {
    'Fundamental': 1,
    'Médio': 2,
    'Superior': 3,
    'Mestrado': 4,
    'Doutorado': 5,
}


def faixa_idade(idade):
    if idade is None:
        return None
    if idade <= 17:
        return '0-17'
    if idade <= 25:
        return '18-25'
    if idade <= 35:
        return '26-35'
    if idade <= 50:
        return '36-50'
    return '51+'


for r in dados_limpos:
    r['faixa_idade'] = faixa_idade(r.get('idade'))
    r['escolaridade_nivel'] = MAP_ESCOLARIDADE.get(r.get('escolaridade'), 3)

# 6) AMOSTRAGEM
print("\n" + "=" * 60)
print("6) AMOSTRAGEM")
print("=" * 60)

random.seed(42)
n_amostra = min(5, len(dados_limpos))
amostra_aleatoria = random.sample(dados_limpos, n_amostra)
print("Amostra aleatória (5):")
for a in amostra_aleatoria:
    print(a)

generos = {}
for r in dados_limpos:
    g = r.get('genero') or 'N/A'
    generos.setdefault(g, []).append(r)
amostra_estratificada = []
for g, lista in generos.items():
    amostra_estratificada.append(random.choice(lista))
print("\nAmostra estratificada por gênero (1 por gênero):")
for a in amostra_estratificada:
    print(a)

# 7) RESUMO EXPLORATÓRIO
print("\n" + "=" * 60)
print("7) RESUMO EXPLORATÓRIO")
print("=" * 60)

idades = [r['idade'] for r in dados_limpos]
rendas = [r['renda_mensal'] for r in dados_limpos]
idade_media = sum(idades) / len(idades) if idades else 0
rendas_ord = sorted(rendas)
n_r = len(rendas_ord)
renda_mediana = (rendas_ord[n_r // 2] if n_r % 2 == 1 else (rendas_ord[n_r // 2 - 1] + rendas_ord[n_r // 2]) / 2) if n_r else 0

print(f"Idade média (dados limpos): {idade_media:.2f}")
print(f"Renda mediana (dados limpos): {renda_mediana:.2f}")

dist_genero = {}
for r in dados_limpos:
    g = r.get('genero') or 'N/A'
    dist_genero[g] = dist_genero.get(g, 0) + 1
print("Distribuição por gênero:", dist_genero)

dist_faixa = {}
for r in dados_limpos:
    f = r.get('faixa_idade') or 'N/A'
    dist_faixa[f] = dist_faixa.get(f, 0) + 1
print("Distribuição por faixa etária:", dist_faixa)

# 8) EXPORTAÇÃO
print("\n" + "=" * 60)
print("8) EXPORTAÇÃO")
print("=" * 60)

campos = [
    'id_cliente', 'idade', 'genero', 'escolaridade', 'renda_mensal',
    'avaliacao', 'cidade', 'faixa_idade', 'escolaridade_nivel'
]
dir_saida = Path(__file__).resolve().parent
arquivo_csv = dir_saida / "dados_limpos.csv"

with open(arquivo_csv, 'w', newline='', encoding='utf-8') as f:
    writer = csv.DictWriter(f, fieldnames=campos, extrasaction='ignore')
    writer.writeheader()
    writer.writerows(dados_limpos)

print(f"Arquivo exportado: {arquivo_csv}")
print(f"Total de linhas (sem cabeçalho): {len(dados_limpos)}")
