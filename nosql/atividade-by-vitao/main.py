"""
Sistema de Rastreamento de Entregas em Tempo Real
Script principal para executar o sistema completo

Autor: Sistema de Rastreamento
Data: 2024
"""

import sys
import os
from datetime import datetime
from config import ROUTE_CONFIG, SIMULATION_CONFIG
from database import db_manager
from simulador_dados import SimuladorRastreamento
from mapa_interativo import MapaInterativo

class SistemaRastreamento:
    """Classe principal do sistema de rastreamento"""
    
    def __init__(self):
        self.simulador = None
        self.mapa = None
    
    def exibir_menu(self):
        """Exibe menu principal do sistema"""
        print("\n" + "="*60)
        print("🚀 SISTEMA DE RASTREAMENTO DE ENTREGAS EM TEMPO REAL")
        print("="*60)
        print("📍 Rota: IFTM → Center Shopping (Av. João Naves de Ávila)")
        print("🗺️  Cidade: Uberlândia - MG")
        print("="*60)
        print("\nOpções disponíveis:")
        print("1. 🔄 Gerar dados simulados")
        print("2. 🗺️  Criar mapa interativo")
        print("3. 📊 Exibir estatísticas")
        print("4. 🧹 Limpar dados do banco")
        print("5. 🔍 Consultar localizações")
        print("6. 🚀 Executar sistema completo")
        print("0. ❌ Sair")
        print("-"*60)
    
    def gerar_dados_simulados(self):
        """Gera dados simulados de rastreamento"""
        print("\n🔄 Gerando dados simulados...")
        
        usuario = input("Digite o ID do motoboy (ou Enter para 'motoboy_001'): ").strip()
        if not usuario:
            usuario = "motoboy_001"
        
        self.simulador = SimuladorRastreamento(usuario)
        
        # Exibe estatísticas antes de inserir
        stats = self.simulador.obter_estatisticas_rota()
        print(f"\n📈 Estatísticas da rota:")
        print(f"   • Total de pontos: {stats['total_pontos']}")
        print(f"   • Velocidade média: {stats['velocidade_media']} km/h")
        print(f"   • Distância: {stats['distancia_aproximada_km']} km")
        print(f"   • Tempo total: {stats['tempo_total_minutos']:.1f} minutos")
        
        confirmar = input("\nDeseja inserir estes dados no MongoDB? (s/N): ").strip().lower()
        if confirmar in ['s', 'sim', 'y', 'yes']:
            sucesso = self.simulador.inserir_dados_no_mongodb()
            if sucesso:
                print("✅ Dados inseridos com sucesso!")
            else:
                print("❌ Erro ao inserir dados!")
        else:
            print("⏭️  Operação cancelada.")
    
    def criar_mapa_interativo(self):
        """Cria mapa interativo com os dados"""
        print("\n🗺️  Criando mapa interativo...")
        
        if not db_manager.connect():
            print("❌ Erro ao conectar ao MongoDB")
            return
        
        try:
            # Verifica se há dados
            localizacoes = db_manager.get_all_locations()
            if not localizacoes:
                print("⚠️  Nenhuma localização encontrada!")
                print("💡 Execute primeiro a opção 1 para gerar dados simulados.")
                return
            
            # Pergunta sobre filtro por usuário
            usuarios = list(set([loc['usuario'] for loc in localizacoes]))
            if len(usuarios) > 1:
                print(f"\n👥 Usuários encontrados: {', '.join(usuarios)}")
                usuario_filtro = input("Digite o usuário para filtrar (ou Enter para todos): ").strip()
            else:
                usuario_filtro = None
            
            # Cria mapa
            self.mapa = MapaInterativo()
            arquivo_gerado = self.mapa.criar_mapa_completo(localizacoes, usuario_filtro)
            
            if arquivo_gerado:
                print(f"✅ Mapa criado: {arquivo_gerado}")
                print("🌐 Abra o arquivo HTML no navegador para visualizar!")
            
        except Exception as e:
            print(f"❌ Erro ao criar mapa: {e}")
        finally:
            db_manager.disconnect()
    
    def exibir_estatisticas(self):
        """Exibe estatísticas dos dados"""
        print("\n📊 Estatísticas do sistema...")
        
        if not db_manager.connect():
            print("❌ Erro ao conectar ao MongoDB")
            return
        
        try:
            localizacoes = db_manager.get_all_locations()
            if not localizacoes:
                print("⚠️  Nenhuma localização encontrada!")
                return
            
            # Estatísticas gerais
            total_pontos = len(localizacoes)
            usuarios = list(set([loc['usuario'] for loc in localizacoes]))
            
            # Estatísticas por usuário
            print(f"\n📈 Estatísticas Gerais:")
            print(f"   • Total de localizações: {total_pontos}")
            print(f"   • Usuários únicos: {len(usuarios)}")
            print(f"   • Período: {localizacoes[0]['timestamp']} → {localizacoes[-1]['timestamp']}")
            
            for usuario in usuarios:
                locs_usuario = [loc for loc in localizacoes if loc['usuario'] == usuario]
                velocidades = [loc.get('velocidade', 0) for loc in locs_usuario]
                
                print(f"\n👤 Usuário: {usuario}")
                print(f"   • Localizações: {len(locs_usuario)}")
                print(f"   • Velocidade média: {sum(velocidades)/len(velocidades):.2f} km/h")
                print(f"   • Velocidade máxima: {max(velocidades)} km/h")
                print(f"   • Velocidade mínima: {min(velocidades)} km/h")
            
        except Exception as e:
            print(f"❌ Erro ao obter estatísticas: {e}")
        finally:
            db_manager.disconnect()
    
    def limpar_dados(self):
        """Limpa todos os dados do banco"""
        print("\n🧹 Limpando dados do banco...")
        
        confirmar = input("⚠️  ATENÇÃO: Isso irá remover TODOS os dados! Confirma? (s/N): ").strip().lower()
        if confirmar not in ['s', 'sim', 'y', 'yes']:
            print("⏭️  Operação cancelada.")
            return
        
        if not db_manager.connect():
            print("❌ Erro ao conectar ao MongoDB")
            return
        
        try:
            removidos = db_manager.clear_collection()
            print(f"✅ {removidos} localizações removidas!")
        except Exception as e:
            print(f"❌ Erro ao limpar dados: {e}")
        finally:
            db_manager.disconnect()
    
    def consultar_localizacoes(self):
        """Consulta localizações no banco"""
        print("\n🔍 Consultando localizações...")
        
        if not db_manager.connect():
            print("❌ Erro ao conectar ao MongoDB")
            return
        
        try:
            # Pergunta sobre filtros
            usuario = input("Digite o usuário para filtrar (ou Enter para todos): ").strip()
            
            if usuario:
                localizacoes = db_manager.get_locations_by_user(usuario)
            else:
                localizacoes = db_manager.get_all_locations()
            
            if not localizacoes:
                print("⚠️  Nenhuma localização encontrada!")
                return
            
            # Exibe primeiras 10 localizações
            print(f"\n📍 Primeiras {min(10, len(localizacoes))} localizações:")
            print("-" * 80)
            
            for i, loc in enumerate(localizacoes[:10]):
                coords = loc['localizacao']['coordinates']
                timestamp = loc['timestamp']
                velocidade = loc.get('velocidade', 0)
                
                print(f"{i+1:2d}. {timestamp.strftime('%d/%m/%Y %H:%M:%S')} | "
                      f"Usuário: {loc['usuario']} | "
                      f"Velocidade: {velocidade:5.1f} km/h | "
                      f"Coords: {coords[1]:.6f}, {coords[0]:.6f}")
            
            if len(localizacoes) > 10:
                print(f"... e mais {len(localizacoes) - 10} localizações")
            
        except Exception as e:
            print(f"❌ Erro ao consultar localizações: {e}")
        finally:
            db_manager.disconnect()
    
    def executar_sistema_completo(self):
        """Executa o sistema completo: gera dados e cria mapa"""
        print("\n🚀 Executando sistema completo...")
        
        # Passo 1: Gerar dados simulados
        print("\n1️⃣  Gerando dados simulados...")
        self.simulador = SimuladorRastreamento("motoboy_001")
        sucesso_dados = self.simulador.inserir_dados_no_mongodb()
        
        if not sucesso_dados:
            print("❌ Falha na geração de dados. Abortando...")
            return
        
        # Passo 2: Criar mapa interativo
        print("\n2️⃣  Criando mapa interativo...")
        if not db_manager.connect():
            print("❌ Erro ao conectar ao MongoDB")
            return
        
        try:
            localizacoes = db_manager.get_all_locations()
            self.mapa = MapaInterativo()
            arquivo_gerado = self.mapa.criar_mapa_completo(localizacoes)
            
            if arquivo_gerado:
                print(f"✅ Sistema executado com sucesso!")
                print(f"🗺️  Mapa gerado: {arquivo_gerado}")
                print("🌐 Abra o arquivo HTML no navegador para visualizar!")
            else:
                print("❌ Erro ao criar mapa!")
                
        except Exception as e:
            print(f"❌ Erro no sistema completo: {e}")
        finally:
            db_manager.disconnect()
    
    def executar(self):
        """Executa o sistema principal"""
        while True:
            try:
                self.exibir_menu()
                opcao = input("\nEscolha uma opção: ").strip()
                
                if opcao == "0":
                    print("\n👋 Encerrando sistema...")
                    break
                elif opcao == "1":
                    self.gerar_dados_simulados()
                elif opcao == "2":
                    self.criar_mapa_interativo()
                elif opcao == "3":
                    self.exibir_estatisticas()
                elif opcao == "4":
                    self.limpar_dados()
                elif opcao == "5":
                    self.consultar_localizacoes()
                elif opcao == "6":
                    self.executar_sistema_completo()
                else:
                    print("❌ Opção inválida! Tente novamente.")
                
                input("\n⏸️  Pressione Enter para continuar...")
                
            except KeyboardInterrupt:
                print("\n\n👋 Sistema interrompido pelo usuário.")
                break
            except Exception as e:
                print(f"\n❌ Erro inesperado: {e}")
                input("⏸️  Pressione Enter para continuar...")

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

def main():
    """Função principal"""
    print("🚀 Iniciando Sistema de Rastreamento de Entregas...")
    
    # Verifica dependências
    if not verificar_dependencias():
        sys.exit(1)
    
    # Verifica configuração do MongoDB
    print("🔧 Verificando configuração...")
    print(f"   • MongoDB URI: {os.getenv('MONGODB_URI', 'mongodb://localhost:27017/')}")
    print(f"   • Database: {os.getenv('DATABASE_NAME', 'rastreamento_entregas')}")
    print(f"   • Collection: {os.getenv('COLLECTION_NAME', 'localizacoes')}")
    
    # Inicia sistema
    sistema = SistemaRastreamento()
    sistema.executar()

if __name__ == "__main__":
    main()
