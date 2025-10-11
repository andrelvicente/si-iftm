"""
Módulo para criação de mapas interativos com Folium
Visualiza dados de rastreamento em tempo real
"""
import folium
from folium import plugins
import json
from typing import List, Dict, Any, Tuple
from datetime import datetime
from config import ROUTE_CONFIG
from database import db_manager

class MapaInterativo:
    """Classe para criar mapas interativos com dados de rastreamento"""
    
    def __init__(self, centro_lat: float = -18.9188, centro_lon: float = -48.2597, zoom: int = 14):
        """
        Inicializa o mapa com coordenadas centrais de Uberlândia
        
        Args:
            centro_lat: Latitude central do mapa
            centro_lon: Longitude central do mapa
            zoom: Nível de zoom inicial
        """
        self.centro_lat = centro_lat
        self.centro_lon = centro_lon
        self.zoom = zoom
        self.mapa = None
        self._criar_mapa_base()
    
    def _criar_mapa_base(self):
        """Cria o mapa base com configurações iniciais"""
        self.mapa = folium.Map(
            location=[self.centro_lat, self.centro_lon],
            zoom_start=self.zoom,
            tiles='CartoDB positron'  # Fundo branco
        )
        
        # Adiciona controle de camadas
        folium.TileLayer('OpenStreetMap').add_to(self.mapa)
        folium.TileLayer('CartoDB dark_matter').add_to(self.mapa)
        
        # Adiciona controle de camadas
        folium.LayerControl().add_to(self.mapa)
    
    def adicionar_marcadores_rota(self):
        """Adiciona marcadores de início e fim da rota"""
        # Marcador de início (IFTM)
        folium.Marker(
            location=[ROUTE_CONFIG['inicio']['latitude'], ROUTE_CONFIG['inicio']['longitude']],
            popup=f"<b>{ROUTE_CONFIG['inicio']['nome']}</b><br>Ponto de partida",
            tooltip="Início da rota",
            icon=folium.Icon(color='green', icon='play', prefix='fa')
        ).add_to(self.mapa)
        
        # Marcador de fim (Center Shopping)
        folium.Marker(
            location=[ROUTE_CONFIG['fim']['latitude'], ROUTE_CONFIG['fim']['longitude']],
            popup=f"<b>{ROUTE_CONFIG['fim']['nome']}</b><br>Ponto de destino",
            tooltip="Fim da rota",
            icon=folium.Icon(color='red', icon='stop', prefix='fa')
        ).add_to(self.mapa)
    
    def adicionar_trajeto_completo(self, localizacoes: List[Dict[str, Any]]):
        """Adiciona linha do trajeto completo"""
        if not localizacoes:
            return
        
        # Extrai coordenadas das localizações
        coordenadas = []
        for loc in localizacoes:
            coords = loc['localizacao']['coordinates']
            coordenadas.append([coords[1], coords[0]])  # [lat, lon] para Folium
        
        # Adiciona linha do trajeto
        folium.PolyLine(
            locations=coordenadas,
            color='blue',
            weight=3,
            opacity=0.7,
            popup="Trajeto completo do motoboy"
        ).add_to(self.mapa)
    
    def adicionar_pontos_sequenciais(self, localizacoes: List[Dict[str, Any]], 
                                   mostrar_todos: bool = False):
        """Adiciona pontos sequenciais do motoboy"""
        if not localizacoes:
            return
        
        # Se mostrar_todos=False, mostra apenas pontos em intervalos
        pontos_para_mostrar = localizacoes
        if not mostrar_todos and len(localizacoes) > 20:
            # Mostra apenas a cada 5 pontos para não sobrecarregar o mapa
            pontos_para_mostrar = localizacoes[::5]
        
        for i, loc in enumerate(pontos_para_mostrar):
            coords = loc['localizacao']['coordinates']
            timestamp = loc['timestamp']
            velocidade = loc.get('velocidade', 0)
            progresso = loc.get('progresso_rota', 0)
            
            # Cor baseada na velocidade
            if velocidade < 20:
                cor = 'orange'
            elif velocidade < 40:
                cor = 'blue'
            else:
                cor = 'green'
            
            # Popup com informações detalhadas
            popup_html = f"""
            <div style="width: 200px;">
                <h4>📍 Posição {i+1}</h4>
                <p><b>Usuário:</b> {loc['usuario']}</p>
                <p><b>Data/Hora:</b> {timestamp.strftime('%d/%m/%Y %H:%M:%S')}</p>
                <p><b>Velocidade:</b> {velocidade} km/h</p>
                <p><b>Progresso:</b> {progresso}%</p>
                <p><b>Coordenadas:</b><br>
                   Lat: {coords[1]:.6f}<br>
                   Lon: {coords[0]:.6f}</p>
            </div>
            """
            
            folium.CircleMarker(
                location=[coords[1], coords[0]],
                radius=6,
                popup=folium.Popup(popup_html, max_width=250),
                tooltip=f"Posição {i+1} - {velocidade} km/h",
                color='white',
                weight=2,
                fillColor=cor,
                fillOpacity=0.8
            ).add_to(self.mapa)
    
    def adicionar_marcador_atual(self, localizacao_atual: Dict[str, Any]):
        """Adiciona marcador da posição atual do motoboy"""
        if not localizacao_atual:
            return
        
        coords = localizacao_atual['localizacao']['coordinates']
        timestamp = localizacao_atual['timestamp']
        velocidade = localizacao_atual.get('velocidade', 0)
        
        # Marcador especial para posição atual
        folium.Marker(
            location=[coords[1], coords[0]],
            popup=f"""
            <div style="width: 200px;">
                <h4>🚀 POSIÇÃO ATUAL</h4>
                <p><b>Usuário:</b> {localizacao_atual['usuario']}</p>
                <p><b>Data/Hora:</b> {timestamp.strftime('%d/%m/%Y %H:%M:%S')}</p>
                <p><b>Velocidade:</b> {velocidade} km/h</p>
                <p><b>Status:</b> Em trânsito</p>
            </div>
            """,
            tooltip="Posição atual do motoboy",
            icon=folium.Icon(color='red', icon='motorcycle', prefix='fa')
        ).add_to(self.mapa)
    
    def adicionar_heatmap_velocidade(self, localizacoes: List[Dict[str, Any]]):
        """Adiciona heatmap baseado na velocidade"""
        if not localizacoes:
            return
        
        # Prepara dados para heatmap
        heat_data = []
        for loc in localizacoes:
            coords = loc['localizacao']['coordinates']
            velocidade = loc.get('velocidade', 0)
            # Normaliza velocidade para intensidade do heatmap (0-1)
            intensidade = min(velocidade / 60, 1.0)  # Máximo 60 km/h = intensidade 1
            heat_data.append([coords[1], coords[0], intensidade])
        
        # Adiciona heatmap
        plugins.HeatMap(
            heat_data,
            name='Heatmap de Velocidade',
            min_opacity=0.2,
            max_zoom=18,
            radius=25,
            blur=15,
            gradient={0.2: 'blue', 0.4: 'cyan', 0.6: 'lime', 0.8: 'yellow', 1.0: 'red'}
        ).add_to(self.mapa)
    
    def adicionar_controles_tempo(self, localizacoes: List[Dict[str, Any]]):
        """Adiciona controles de tempo para visualização sequencial"""
        if not localizacoes:
            return
        
        # Prepara dados para timeline
        features = []
        for i, loc in enumerate(localizacoes):
            coords = loc['localizacao']['coordinates']
            timestamp = loc['timestamp']
            velocidade = loc.get('velocidade', 0)
            
            feature = {
                "type": "Feature",
                "geometry": {
                    "type": "Point",
                    "coordinates": [coords[0], coords[1]]
                },
                "properties": {
                    "time": timestamp.isoformat(),
                    "popup": f"Posição {i+1}<br>Velocidade: {velocidade} km/h",
                    "icon": "circle",
                    "iconstyle": {
                        "color": "red" if velocidade > 40 else "blue",
                        "fillColor": "red" if velocidade > 40 else "blue",
                        "radius": 5
                    }
                }
            }
            features.append(feature)
        
        # Adiciona timeline
        plugins.TimestampedGeoJson(
            {
                "type": "FeatureCollection",
                "features": features
            },
            period="PT30S",  # Intervalo de 30 segundos
            duration="PT1M",  # Duração de 1 minuto
            auto_play=True,
            loop=True,
            max_speed=1,
            loop_button=True,
            date_options="YYYY/MM/DD HH:mm:ss",
            time_slider_drag_update=True
        ).add_to(self.mapa)
    
    def salvar_mapa(self, nome_arquivo: str = "mapa_rastreamento.html"):
        """Salva o mapa em arquivo HTML"""
        # Define caminho no diretório do projeto
        diretorio_projeto = "/home/andre-luiz/iftm/si-iftm/nosql/atividade-by-vitao"
        if not nome_arquivo.startswith('/'):
            caminho_completo = f"{diretorio_projeto}/{nome_arquivo}"
        else:
            caminho_completo = nome_arquivo
        
        self.mapa.save(caminho_completo)
        print(f"🗺️  Mapa salvo como: {caminho_completo}")
        return caminho_completo
    
    def criar_mapa_completo(self, localizacoes: List[Dict[str, Any]], 
                          usuario: str = None) -> str:
        """
        Cria mapa completo com todos os elementos
        
        Args:
            localizacoes: Lista de localizações do MongoDB
            usuario: Filtro por usuário (opcional)
            
        Returns:
            Nome do arquivo HTML gerado
        """
        # Filtra por usuário se especificado
        if usuario:
            localizacoes = [loc for loc in localizacoes if loc['usuario'] == usuario]
        
        if not localizacoes:
            print("⚠️  Nenhuma localização encontrada!")
            return None
        
        print(f"🗺️  Criando mapa com {len(localizacoes)} localizações...")
        
        # Adiciona elementos ao mapa
        self.adicionar_marcadores_rota()
        self.adicionar_trajeto_completo(localizacoes)
        self.adicionar_pontos_sequenciais(localizacoes, mostrar_todos=False)
        
        # Adiciona posição atual (última localização)
        if localizacoes:
            self.adicionar_marcador_atual(localizacoes[-1])
        
        # Adiciona controles extras
        self.adicionar_heatmap_velocidade(localizacoes)
        self.adicionar_controles_tempo(localizacoes)
        
        # Adiciona legenda
        self._adicionar_legenda()
        
        # Salva mapa
        nome_arquivo = f"mapa_rastreamento_{datetime.now().strftime('%Y%m%d_%H%M%S')}.html"
        return self.salvar_mapa(nome_arquivo)
    
    def _adicionar_legenda(self):
        """Adiciona legenda ao mapa"""
        legenda_html = """
        <div style="position: fixed; 
                    bottom: 50px; left: 50px; width: 200px; height: 120px; 
                    background-color: white; border:2px solid grey; z-index:9999; 
                    font-size:14px; padding: 10px">
        <h4>Legenda</h4>
        <p><i class="fa fa-play" style="color:green"></i> Início da rota</p>
        <p><i class="fa fa-stop" style="color:red"></i> Fim da rota</p>
        <p><i class="fa fa-motorcycle" style="color:red"></i> Posição atual</p>
        <p><span style="color:blue">●</span> Pontos do trajeto</p>
        </div>
        """
        self.mapa.get_root().html.add_child(folium.Element(legenda_html))

def main():
    """Função principal para testar o mapa"""
    print("🗺️  Criando mapa interativo...")
    
    # Conecta ao banco e recupera dados
    if not db_manager.connect():
        print("❌ Erro ao conectar ao MongoDB")
        return
    
    try:
        # Recupera todas as localizações
        localizacoes = db_manager.get_all_locations()
        
        if not localizacoes:
            print("⚠️  Nenhuma localização encontrada no banco!")
            print("💡 Execute primeiro o simulador_dados.py")
            return
        
        # Cria mapa
        mapa = MapaInterativo()
        arquivo_gerado = mapa.criar_mapa_completo(localizacoes)
        
        if arquivo_gerado:
            print(f"✅ Mapa criado com sucesso: {arquivo_gerado}")
            print("🌐 Abra o arquivo HTML no seu navegador para visualizar!")
        
    except Exception as e:
        print(f"❌ Erro ao criar mapa: {e}")
    finally:
        db_manager.disconnect()

if __name__ == "__main__":
    main()
