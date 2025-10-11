// Script de inicialização do MongoDB
// Cria usuário específico para a aplicação e configurações iniciais

// Conecta ao banco de dados da aplicação
db = db.getSiblingDB('rastreamento_entregas');

// Cria usuário específico para a aplicação
db.createUser({
  user: 'rastreamento_user',
  pwd: 'rastreamento_pass',
  roles: [
    {
      role: 'readWrite',
      db: 'rastreamento_entregas'
    }
  ]
});

// Cria a coleção de localizações
db.createCollection('localizacoes');

// Cria índices para otimização
db.localizacoes.createIndex({ "localizacao": "2dsphere" });
db.localizacoes.createIndex({ "timestamp": 1 });
db.localizacoes.createIndex({ "usuario": 1 });
db.localizacoes.createIndex({ "usuario": 1, "timestamp": 1 });

// Insere documento de exemplo
db.localizacoes.insertOne({
  "usuario": "exemplo_motoboy",
  "timestamp": new Date(),
  "localizacao": {
    "type": "Point",
    "coordinates": [-48.2672, -18.9176]
  },
  "velocidade": 0,
  "progresso_rota": 0,
  "metadados": {
    "ponto_sequencial": 0,
    "total_pontos": 0,
    "rua": "Av. João Naves de Ávila",
    "cidade": "Uberlândia",
    "estado": "MG",
    "tipo": "documento_exemplo"
  }
});

print('✅ Banco de dados inicializado com sucesso!');
print('📊 Coleção "localizacoes" criada');
print('🔍 Índices geoespaciais configurados');
print('👤 Usuário da aplicação criado');
