#!/usr/bin/env python3
"""
Script para exportar dados do MongoDB em formato GeoJSON
"""

import json
from datetime import datetime
from database import db_manager

def exportar_geojson(usuario=None, arquivo_saida=None):
    """
    Exporta dados do MongoDB em formato GeoJSON
    
    Args:
        usuario: Filtro por usuário (opcional)
        arquivo_saida: Nome do arquivo de saída (opcional)
    """
    
    # Conecta ao banco
    if not db_manager.connect():
        print("❌ Erro ao conectar ao MongoDB")
        return None
    
    try:
        # Recupera dados
        if usuario:
            localizacoes = db_manager.get_locations_by_user(usuario)
            print(f"📊 Recuperadas {len(localizacoes)} localizações para usuário: {usuario}")
        else:
            localizacoes = db_manager.get_all_locations()
            print(f"📊 Recuperadas {len(localizacoes)} localizações totais")
        
        if not localizacoes:
            print("⚠️  Nenhuma localização encontrada!")
            return None
        
        # Cria estrutura GeoJSON
        geojson = {
            "type": "FeatureCollection",
            "features": []
        }
        
        # Processa cada localização
        for i, loc in enumerate(localizacoes):
            coords = loc['localizacao']['coordinates']
            timestamp = loc['timestamp']
            velocidade = loc.get('velocidade', 0)
            progresso = loc.get('progresso_rota', 0)
            
            # Cria feature GeoJSON
            feature = {
                "type": "Feature",
                "geometry": {
                    "type": "Point",
                    "coordinates": coords
                },
                "properties": {
                    "id": i + 1,
                    "usuario": loc['usuario'],
                    "timestamp": timestamp.isoformat(),
                    "velocidade": velocidade,
                    "progresso_rota": progresso,
                    "metadados": loc.get('metadados', {})
                }
            }
            
            geojson["features"].append(feature)
        
        # Define nome do arquivo (sempre no diretório do projeto)
        diretorio_projeto = "/home/andre-luiz/iftm/si-iftm/nosql/atividade-by-vitao"
        if not arquivo_saida:
            timestamp_str = datetime.now().strftime('%Y%m%d_%H%M%S')
            usuario_str = f"_{usuario}" if usuario else ""
            arquivo_saida = f"{diretorio_projeto}/dados_rastreamento{usuario_str}_{timestamp_str}.geojson"
        elif not arquivo_saida.startswith('/'):
            arquivo_saida = f"{diretorio_projeto}/{arquivo_saida}"
        
        # Salva arquivo
        with open(arquivo_saida, 'w', encoding='utf-8') as f:
            json.dump(geojson, f, indent=2, ensure_ascii=False)
        
        print(f"✅ GeoJSON exportado: {arquivo_saida}")
        print(f"📊 Total de features: {len(geojson['features'])}")
        
        # Estatísticas
        if geojson['features']:
            velocidades = [f['properties']['velocidade'] for f in geojson['features']]
            usuarios = list(set([f['properties']['usuario'] for f in geojson['features']]))
            
            print(f"👥 Usuários: {', '.join(usuarios)}")
            print(f"🚗 Velocidade média: {sum(velocidades)/len(velocidades):.2f} km/h")
            print(f"🚗 Velocidade máxima: {max(velocidades)} km/h")
            print(f"🚗 Velocidade mínima: {min(velocidades)} km/h")
        
        return arquivo_saida
        
    except Exception as e:
        print(f"❌ Erro ao exportar GeoJSON: {e}")
        return None
    finally:
        db_manager.disconnect()

def exportar_linestring(usuario=None, arquivo_saida=None):
    """
    Exporta dados como LineString (trajeto completo)
    
    Args:
        usuario: Filtro por usuário (opcional)
        arquivo_saida: Nome do arquivo de saída (opcional)
    """
    
    # Conecta ao banco
    if not db_manager.connect():
        print("❌ Erro ao conectar ao MongoDB")
        return None
    
    try:
        # Recupera dados
        if usuario:
            localizacoes = db_manager.get_locations_by_user(usuario)
        else:
            localizacoes = db_manager.get_all_locations()
        
        if not localizacoes:
            print("⚠️  Nenhuma localização encontrada!")
            return None
        
        # Ordena por timestamp
        localizacoes.sort(key=lambda x: x['timestamp'])
        
        # Extrai coordenadas
        coordenadas = []
        propriedades = []
        
        for loc in localizacoes:
            coords = loc['localizacao']['coordinates']
            coordenadas.append(coords)
            
            propriedades.append({
                "timestamp": loc['timestamp'].isoformat(),
                "velocidade": loc.get('velocidade', 0),
                "usuario": loc['usuario']
            })
        
        # Cria GeoJSON com LineString
        geojson = {
            "type": "FeatureCollection",
            "features": [
                {
                    "type": "Feature",
                    "geometry": {
                        "type": "LineString",
                        "coordinates": coordenadas
                    },
                    "properties": {
                        "usuario": localizacoes[0]['usuario'] if localizacoes else "desconhecido",
                        "total_pontos": len(coordenadas),
                        "velocidade_media": sum(p['velocidade'] for p in propriedades) / len(propriedades),
                        "inicio": propriedades[0]['timestamp'] if propriedades else None,
                        "fim": propriedades[-1]['timestamp'] if propriedades else None,
                        "detalhes": propriedades
                    }
                }
            ]
        }
        
        # Define nome do arquivo (sempre no diretório do projeto)
        diretorio_projeto = "/home/andre-luiz/iftm/si-iftm/nosql/atividade-by-vitao"
        if not arquivo_saida:
            timestamp_str = datetime.now().strftime('%Y%m%d_%H%M%S')
            usuario_str = f"_{usuario}" if usuario else ""
            arquivo_saida = f"{diretorio_projeto}/trajeto_rastreamento{usuario_str}_{timestamp_str}.geojson"
        elif not arquivo_saida.startswith('/'):
            arquivo_saida = f"{diretorio_projeto}/{arquivo_saida}"
        
        # Salva arquivo
        with open(arquivo_saida, 'w', encoding='utf-8') as f:
            json.dump(geojson, f, indent=2, ensure_ascii=False)
        
        print(f"✅ Trajeto GeoJSON exportado: {arquivo_saida}")
        print(f"📊 Total de pontos: {len(coordenadas)}")
        
        return arquivo_saida
        
    except Exception as e:
        print(f"❌ Erro ao exportar trajeto: {e}")
        return None
    finally:
        db_manager.disconnect()

def main():
    """Função principal"""
    print("🗺️  EXPORTADOR GEOJSON - SISTEMA DE RASTREAMENTO")
    print("=" * 50)
    
    # Lista usuários disponíveis
    if not db_manager.connect():
        print("❌ Erro ao conectar ao MongoDB")
        return
    
    try:
        localizacoes = db_manager.get_all_locations()
        usuarios = list(set([loc['usuario'] for loc in localizacoes]))
        
        print(f"👥 Usuários disponíveis: {', '.join(usuarios)}")
        print()
        
        # Pergunta sobre tipo de exportação
        print("Tipos de exportação:")
        print("1. Pontos individuais (FeatureCollection com Points)")
        print("2. Trajeto completo (LineString)")
        print("3. Ambos")
        
        opcao = input("\nEscolha uma opção (1-3): ").strip()
        
        # Pergunta sobre usuário
        if len(usuarios) > 1:
            print(f"\nUsuários disponíveis: {', '.join(usuarios)}")
            usuario = input("Digite o usuário (ou Enter para todos): ").strip()
            if not usuario:
                usuario = None
        else:
            usuario = usuarios[0] if usuarios else None
            print(f"Usuário: {usuario}")
        
        # Executa exportação
        if opcao in ['1', '3']:
            print("\n🔄 Exportando pontos individuais...")
            exportar_geojson(usuario)
        
        if opcao in ['2', '3']:
            print("\n🔄 Exportando trajeto completo...")
            exportar_linestring(usuario)
        
        print("\n✅ Exportação concluída!")
        
    except Exception as e:
        print(f"❌ Erro: {e}")
    finally:
        db_manager.disconnect()

if __name__ == "__main__":
    main()
