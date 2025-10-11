"""
Configurações do sistema de rastreamento de entregas
"""
import os
from dotenv import load_dotenv

# Carrega variáveis de ambiente
load_dotenv()

# Configurações do MongoDB
MONGODB_URI = os.getenv('MONGODB_URI', 'mongodb://localhost:27017/')
DATABASE_NAME = 'rastreamento_entregas'
COLLECTION_NAME = 'localizacoes'

# Configurações da rota (coordenadas aproximadas da Av. João Naves de Ávila)
# IFTM (ponto inicial) → Center Shopping (ponto final)
ROUTE_CONFIG = {
    'inicio': {
        'nome': 'IFTM',
        'latitude': -18.929845,
        'longitude': -48.277619
    },
    'fim': {
        'nome': 'Center Shopping',
        'latitude': -18.9201,
        'longitude': -48.2523
    }
}

# Configurações da simulação
SIMULATION_CONFIG = {
    'total_pontos': 50,
    'intervalo_segundos': 30,  # Intervalo entre pontos em segundos
    'velocidade_media_kmh': 35,  # Velocidade média em km/h
    'variacao_posicao': 0.0001  # Variação aleatória nas coordenadas
}
