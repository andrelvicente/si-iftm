"""
Módulo para simulação de dados de rastreamento
Gera dados simulados de um motoboy se deslocando pela Av. João Naves de Ávila
"""
import random
import math
import uuid
from datetime import datetime, timedelta
from typing import List, Dict, Any
from config import ROUTE_CONFIG, SIMULATION_CONFIG
from database import db_manager

class SimuladorRastreamento:
    """Classe para simular dados de rastreamento de entregas"""
    
    def __init__(self, usuario: str = "motoboy_001"):
        self.usuario = usuario
        self.sessao_id = str(uuid.uuid4())[:8]  # ID único para esta sessão
        self.pontos_rota = []
        self._gerar_pontos_rota()
    
    def _gerar_pontos_rota(self):
        """Gera pontos interpolados ao longo da rota IFTM → Center Shopping"""
        inicio = ROUTE_CONFIG['inicio']
        fim = ROUTE_CONFIG['fim']
        
        # Calcula diferenças
        delta_lat = fim['latitude'] - inicio['latitude']
        delta_lon = fim['longitude'] - inicio['longitude']
        
        # Gera pontos interpolados
        for i in range(SIMULATION_CONFIG['total_pontos']):
            # Progressão linear com pequenas variações
            progresso = i / (SIMULATION_CONFIG['total_pontos'] - 1)
            
            # Adiciona pequenas variações para simular movimento real
            variacao_lat = random.uniform(-SIMULATION_CONFIG['variacao_posicao'], 
                                        SIMULATION_CONFIG['variacao_posicao'])
            variacao_lon = random.uniform(-SIMULATION_CONFIG['variacao_posicao'], 
                                        SIMULATION_CONFIG['variacao_posicao'])
            
            latitude = inicio['latitude'] + (delta_lat * progresso) + variacao_lat
            longitude = inicio['longitude'] + (delta_lon * progresso) + variacao_lon
            
            self.pontos_rota.append({
                'latitude': latitude,
                'longitude': longitude,
                'progresso': progresso
            })
    
    def _calcular_velocidade(self, ponto_atual: int) -> float:
        """Calcula velocidade simulada baseada na posição na rota"""
        # Velocidade varia entre 20-50 km/h com base na posição
        base_velocidade = SIMULATION_CONFIG['velocidade_media_kmh']
        variacao = random.uniform(-15, 15)  # ±15 km/h
        
        # Velocidade menor no início e fim (semáforos, trânsito)
        if ponto_atual < 10 or ponto_atual > 40:
            variacao -= 10
        
        return max(5, base_velocidade + variacao)  # Mínimo 5 km/h
    
    def _gerar_timestamp(self, ponto_atual: int) -> datetime:
        """Gera timestamp sequencial para cada ponto"""
        # Usa timestamp atual como base para evitar conflitos
        tempo_inicial = datetime.now() - timedelta(minutes=30)  # Simula 30 minutos atrás
        intervalo = timedelta(seconds=SIMULATION_CONFIG['intervalo_segundos'])
        return tempo_inicial + (intervalo * ponto_atual)
    
    def gerar_dados_simulados(self) -> List[Dict[str, Any]]:
        """Gera lista de dados simulados para inserção no MongoDB"""
        dados = []
        
        for i, ponto in enumerate(self.pontos_rota):
            # Estrutura do documento MongoDB com GeoJSON
            documento = {
                "usuario": self.usuario,
                "sessao_id": self.sessao_id,  # ID único da sessão
                "timestamp": self._gerar_timestamp(i),
                "localizacao": {
                    "type": "Point",
                    "coordinates": [ponto['longitude'], ponto['latitude']]
                },
                "velocidade": round(self._calcular_velocidade(i), 2),
                "progresso_rota": round(ponto['progresso'] * 100, 2),  # Percentual
                "metadados": {
                    "ponto_sequencial": i + 1,
                    "total_pontos": len(self.pontos_rota),
                    "rua": "Av. João Naves de Ávila",
                    "cidade": "Uberlândia",
                    "estado": "MG",
                    "sessao_timestamp": datetime.now().isoformat()
                }
            }
            dados.append(documento)
        
        return dados
    
    def inserir_dados_no_mongodb(self) -> bool:
        """Insere os dados simulados no MongoDB"""
        if not db_manager.connect():
            print("Erro: Não foi possível conectar ao MongoDB")
            return False
        
        try:
            # Gera e insere dados
            dados = self.gerar_dados_simulados()
            inseridos = 0
            
            for documento in dados:
                if db_manager.insert_location(documento):
                    inseridos += 1
            
            print(f"✅ {inseridos} localizações inseridas com sucesso!")
            print(f"📊 Usuário: {self.usuario}")
            print(f"🆔 Sessão ID: {self.sessao_id}")
            print(f"🗺️  Rota: {ROUTE_CONFIG['inicio']['nome']} → {ROUTE_CONFIG['fim']['nome']}")
            print(f"📍 Total de pontos: {len(dados)}")
            
            return True
            
        except Exception as e:
            print(f"❌ Erro ao inserir dados: {e}")
            return False
        finally:
            db_manager.disconnect()
    
    def obter_estatisticas_rota(self) -> Dict[str, Any]:
        """Retorna estatísticas da rota simulada"""
        dados = self.gerar_dados_simulados()
        
        velocidades = [d['velocidade'] for d in dados]
        coordenadas = [d['localizacao']['coordinates'] for d in dados]
        
        return {
            'total_pontos': len(dados),
            'velocidade_media': round(sum(velocidades) / len(velocidades), 2),
            'velocidade_maxima': max(velocidades),
            'velocidade_minima': min(velocidades),
            'distancia_aproximada_km': self._calcular_distancia_rota(coordenadas),
            'tempo_total_minutos': len(dados) * (SIMULATION_CONFIG['intervalo_segundos'] / 60)
        }
    
    def _calcular_distancia_rota(self, coordenadas: List[List[float]]) -> float:
        """Calcula distância aproximada da rota em km"""
        def haversine(lat1, lon1, lat2, lon2):
            """Fórmula de Haversine para calcular distância entre dois pontos"""
            R = 6371  # Raio da Terra em km
            
            dlat = math.radians(lat2 - lat1)
            dlon = math.radians(lon2 - lon1)
            
            a = (math.sin(dlat/2) * math.sin(dlat/2) + 
                 math.cos(math.radians(lat1)) * math.cos(math.radians(lat2)) * 
                 math.sin(dlon/2) * math.sin(dlon/2))
            
            c = 2 * math.atan2(math.sqrt(a), math.sqrt(1-a))
            return R * c
        
        distancia_total = 0
        for i in range(1, len(coordenadas)):
            lat1, lon1 = coordenadas[i-1][1], coordenadas[i-1][0]
            lat2, lon2 = coordenadas[i][1], coordenadas[i][0]
            distancia_total += haversine(lat1, lon1, lat2, lon2)
        
        return round(distancia_total, 2)

def main():
    """Função principal para testar o simulador"""
    print("🚀 Iniciando simulação de rastreamento...")
    
    simulador = SimuladorRastreamento("motoboy_001")
    
    # Exibe estatísticas
    stats = simulador.obter_estatisticas_rota()
    print("\n📈 Estatísticas da Rota:")
    print(f"   • Total de pontos: {stats['total_pontos']}")
    print(f"   • Velocidade média: {stats['velocidade_media']} km/h")
    print(f"   • Velocidade máxima: {stats['velocidade_maxima']} km/h")
    print(f"   • Velocidade mínima: {stats['velocidade_minima']} km/h")
    print(f"   • Distância aproximada: {stats['distancia_aproximada_km']} km")
    print(f"   • Tempo total: {stats['tempo_total_minutos']:.1f} minutos")
    
    # Insere dados no MongoDB
    print("\n💾 Inserindo dados no MongoDB...")
    sucesso = simulador.inserir_dados_no_mongodb()
    
    if sucesso:
        print("\n✅ Simulação concluída com sucesso!")
    else:
        print("\n❌ Erro na simulação!")

if __name__ == "__main__":
    main()
