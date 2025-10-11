#!/usr/bin/env python3
"""
Sistema Simplificado de Rastreamento
Apenas 2 funcionalidades: Gerar trajeto e Retornar JSON
"""

import sys
import os
from datetime import datetime
from config import ROUTE_CONFIG, SIMULATION_CONFIG
from database import db_manager
from simulador_dados import SimuladorRastreamento
from mapa_interativo import MapaInterativo
from exportar_geojson import exportar_geojson, exportar_linestring

def verificar_dependencias():
    """Verifica se as dependências estão instaladas"""
    dependencias = [
        ('pymongo', 'pymongo'),
        ('folium', 'folium'),
        ('python-dotenv', 'dotenv')
    ]
    faltando = []
    
    for nome_pacote, nome_import in dependencias:
        try:
            __import__(nome_import)
        except ImportError:
            faltando.append(nome_pacote)
    
    if faltando:
        print("❌ Dependências faltando:")
        for dep in faltando:
            print(f"   • {dep}")
        print("\n💡 Execute: pip install -r requirements.txt")
        return False
    
    return True

def gerar_trajeto():
    """Gera dados simulados e cria mapa"""
    print("🔄 Gerando trajeto...")
    
    # Solicita ID do usuário
    usuario_id = input("👤 Digite o ID do usuário: ").strip()
    if not usuario_id:
        usuario_id = "motoboy_001"
        print(f"⚠️  Usando ID padrão: {usuario_id}")
    
    # Gera dados simulados
    simulador = SimuladorRastreamento(usuario_id)
    sucesso = simulador.inserir_dados_no_mongodb()
    
    if not sucesso:
        print("❌ Erro ao gerar dados simulados")
        return False
    
    # Cria mapa
    if not db_manager.connect():
        print("❌ Erro ao conectar ao MongoDB")
        return False
    
    try:
        localizacoes = db_manager.get_all_locations()
        if not localizacoes:
            print("❌ Nenhuma localização encontrada")
            return False
        
        # Cria mapa
        mapa = MapaInterativo()
        arquivo = mapa.criar_mapa_completo(localizacoes)
        
        print(f"✅ Trajeto gerado com sucesso!")
        print(f"🗺️  Mapa: {arquivo}")
        print(f"📊 Total de pontos: {len(localizacoes)}")
        
        return True
        
    except Exception as e:
        print(f"❌ Erro ao criar mapa: {e}")
        return False
    finally:
        db_manager.disconnect()

def retornar_json():
    """Retorna dados em formato GeoJSON"""
    print("📄 Gerando JSON do trajeto...")
    
    # Solicita ID do usuário para filtrar
    usuario_filtro = input("👤 Digite o ID do usuário (ou Enter para todos): ").strip()
    
    if not db_manager.connect():
        print("❌ Erro ao conectar ao MongoDB")
        return False
    
    try:
        localizacoes = db_manager.get_all_locations()
        if not localizacoes:
            print("❌ Nenhuma localização encontrada")
            print("💡 Execute primeiro: Gerar trajeto")
            return False
        
        # Filtra por usuário se especificado
        if usuario_filtro:
            localizacoes = [loc for loc in localizacoes if loc['usuario'] == usuario_filtro]
            if not localizacoes:
                print(f"❌ Nenhuma localização encontrada para o usuário: {usuario_filtro}")
                return False
        
        # Exporta como LineString (trajeto completo)
        timestamp_str = datetime.now().strftime('%Y%m%d_%H%M%S')
        usuario_str = f"_{usuario_filtro}" if usuario_filtro else ""
        arquivo = f"trajeto{usuario_str}_{timestamp_str}.geojson"
        
        sucesso = exportar_linestring(usuario_filtro, arquivo)
        
        if sucesso:
            print(f"✅ JSON gerado: {arquivo}")
            print(f"📊 Total de pontos: {len(localizacoes)}")
            
            # Mostra estatísticas
            velocidades = [loc.get('velocidade', 0) for loc in localizacoes]
            usuarios = list(set([loc['usuario'] for loc in localizacoes]))
            
            print(f"👥 Usuários: {', '.join(usuarios)}")
            print(f"🚗 Velocidade média: {sum(velocidades)/len(velocidades):.2f} km/h")
            print(f"🚗 Velocidade máxima: {max(velocidades)} km/h")
            print(f"🚗 Velocidade mínima: {min(velocidades)} km/h")
            
            return True
        else:
            print("❌ Erro ao gerar JSON")
            return False
            
    except Exception as e:
        print(f"❌ Erro: {e}")
        return False
    finally:
        db_manager.disconnect()

def exibir_menu():
    """Exibe menu simplificado"""
    print("\n" + "="*50)
    print("🚀 SISTEMA DE RASTREAMENTO SIMPLIFICADO")
    print("="*50)
    print("📍 Rota: IFTM → Center Shopping")
    print("🗺️  Cidade: Uberlândia - MG")
    print("="*50)
    print("\nOpções disponíveis:")
    print("1. 🔄 Gerar trajeto (dados + mapa)")
    print("2. 📄 Retornar JSON do trajeto")
    print("0. ❌ Sair")
    print("-"*50)

def main():
    """Função principal"""
    print("🚀 Iniciando Sistema de Rastreamento Simplificado...")
    
    # Verifica dependências
    if not verificar_dependencias():
        sys.exit(1)
    
    # Verifica configuração do MongoDB
    print("🔧 Verificando configuração...")
    print(f"   • MongoDB URI: {os.getenv('MONGODB_URI', 'mongodb://localhost:27017/')}")
    print(f"   • Database: {os.getenv('DATABASE_NAME', 'rastreamento_entregas')}")
    print(f"   • Collection: {os.getenv('COLLECTION_NAME', 'localizacoes')}")
    
    while True:
        try:
            exibir_menu()
            opcao = input("\nEscolha uma opção: ").strip()
            
            if opcao == "0":
                print("\n👋 Encerrando sistema...")
                break
            elif opcao == "1":
                gerar_trajeto()
            elif opcao == "2":
                retornar_json()
            else:
                print("❌ Opção inválida! Tente novamente.")
            
            input("\n⏸️  Pressione Enter para continuar...")
            
        except KeyboardInterrupt:
            print("\n\n👋 Sistema interrompido pelo usuário.")
            break
        except Exception as e:
            print(f"\n❌ Erro inesperado: {e}")
            input("⏸️  Pressione Enter para continuar...")

if __name__ == "__main__":
    main()
