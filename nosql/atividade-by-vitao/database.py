"""
Módulo para gerenciamento do banco de dados MongoDB
"""
from pymongo import MongoClient
from pymongo.errors import ConnectionFailure, ServerSelectionTimeoutError
import logging
from config import MONGODB_URI, DATABASE_NAME, COLLECTION_NAME

# Configuração de logging
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

class DatabaseManager:
    """Classe para gerenciar conexões e operações do MongoDB"""
    
    def __init__(self):
        self.client = None
        self.db = None
        self.collection = None
        
    def connect(self):
        """Estabelece conexão com o MongoDB"""
        try:
            self.client = MongoClient(MONGODB_URI, serverSelectionTimeoutMS=5000)
            # Testa a conexão
            self.client.admin.command('ping')
            self.db = self.client[DATABASE_NAME]
            self.collection = self.db[COLLECTION_NAME]
            logger.info(f"Conectado ao MongoDB: {DATABASE_NAME}.{COLLECTION_NAME}")
            return True
        except (ConnectionFailure, ServerSelectionTimeoutError) as e:
            logger.error(f"Erro ao conectar ao MongoDB: {e}")
            return False
    
    def disconnect(self):
        """Fecha a conexão com o MongoDB"""
        if self.client:
            self.client.close()
            logger.info("Conexão com MongoDB fechada")
    
    def create_geospatial_index(self):
        """Cria índice geoespacial 2dsphere na coleção"""
        try:
            # Remove índices existentes do tipo 2dsphere
            existing_indexes = self.collection.list_indexes()
            for index in existing_indexes:
                if 'localizacao' in index.get('key', {}):
                    self.collection.drop_index(index['name'])
                    logger.info(f"Índice removido: {index['name']}")
            
            # Cria novo índice 2dsphere
            index_result = self.collection.create_index([("localizacao", "2dsphere")])
            logger.info(f"Índice geoespacial criado: {index_result}")
            return True
        except Exception as e:
            logger.error(f"Erro ao criar índice geoespacial: {e}")
            return False
    
    def insert_location(self, location_data):
        """Insere uma localização na coleção"""
        try:
            result = self.collection.insert_one(location_data)
            logger.info(f"Localização inserida com ID: {result.inserted_id}")
            return result.inserted_id
        except Exception as e:
            logger.error(f"Erro ao inserir localização: {e}")
            return None
    
    def get_locations_by_user(self, usuario):
        """Recupera todas as localizações de um usuário"""
        try:
            locations = list(self.collection.find({"usuario": usuario}).sort("timestamp", 1))
            logger.info(f"Recuperadas {len(locations)} localizações para usuário: {usuario}")
            return locations
        except Exception as e:
            logger.error(f"Erro ao recuperar localizações: {e}")
            return []
    
    def get_all_locations(self):
        """Recupera todas as localizações da coleção"""
        try:
            locations = list(self.collection.find().sort("timestamp", 1))
            logger.info(f"Recuperadas {len(locations)} localizações totais")
            return locations
        except Exception as e:
            logger.error(f"Erro ao recuperar todas as localizações: {e}")
            return []
    
    def clear_collection(self):
        """Remove todos os documentos da coleção"""
        try:
            result = self.collection.delete_many({})
            logger.info(f"Coleção limpa: {result.deleted_count} documentos removidos")
            return result.deleted_count
        except Exception as e:
            logger.error(f"Erro ao limpar coleção: {e}")
            return 0
    
    def get_collection_stats(self):
        """Retorna estatísticas da coleção"""
        try:
            total_docs = self.collection.count_documents({})
            usuarios = self.collection.distinct("usuario")
            sessoes = self.collection.distinct("sessao_id")
            
            # Estatísticas por usuário
            stats_por_usuario = []
            for usuario in usuarios:
                count = self.collection.count_documents({"usuario": usuario})
                stats_por_usuario.append({"usuario": usuario, "total_pontos": count})
            
            # Estatísticas por sessão
            stats_por_sessao = []
            for sessao in sessoes:
                count = self.collection.count_documents({"sessao_id": sessao})
                # Pega o primeiro documento da sessão para obter timestamp
                primeiro_doc = self.collection.find_one({"sessao_id": sessao}, sort=[("timestamp", 1)])
                timestamp_sessao = primeiro_doc.get("timestamp") if primeiro_doc else None
                stats_por_sessao.append({
                    "sessao_id": sessao, 
                    "total_pontos": count,
                    "timestamp": timestamp_sessao
                })
            
            return {
                "total_documentos": total_docs,
                "total_usuarios": len(usuarios),
                "total_sessoes": len(sessoes),
                "usuarios": usuarios,
                "stats_por_usuario": stats_por_usuario,
                "stats_por_sessao": stats_por_sessao
            }
        except Exception as e:
            logger.error(f"Erro ao obter estatísticas: {e}")
            return None
    
    def get_locations_by_session(self, sessao_id):
        """Recupera todas as localizações de uma sessão específica"""
        try:
            locations = list(self.collection.find({"sessao_id": sessao_id}).sort("timestamp", 1))
            logger.info(f"Recuperadas {len(locations)} localizações para sessão: {sessao_id}")
            return locations
        except Exception as e:
            logger.error(f"Erro ao recuperar localizações da sessão: {e}")
            return []

# Instância global do gerenciador de banco
db_manager = DatabaseManager()
