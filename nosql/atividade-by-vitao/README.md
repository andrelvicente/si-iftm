# 🚀 Sistema de Rastreamento de Entregas em Tempo Real

Sistema completo para simulação e visualização de rastreamento de entregas usando MongoDB e Folium.

## 📋 Características

- **Dados GeoJSON**: Armazenamento de localizações em formato GeoJSON no MongoDB
- **Simulação Realista**: Gera dados simulados de um motoboy na Av. João Naves de Ávila (Uberlândia)
- **Mapa Interativo**: Visualização com Folium incluindo marcadores, trajetos e controles de tempo
- **Índices Geoespaciais**: Otimização de consultas com índices 2dsphere
- **Interface Modular**: Código organizado e comentado

## 🗺️ Rota Simulada

- **Início**: IFTM (Instituto Federal do Triângulo Mineiro)
- **Fim**: Center Shopping
- **Avenida**: João Naves de Ávila, Uberlândia - MG
- **Pontos**: 50 localizações simuladas
- **Intervalo**: 30 segundos entre pontos

## 🛠️ Instalação

### 1. Dependências

```bash
pip install -r requirements.txt
```

### 2. Configuração do MongoDB

Copie o arquivo de exemplo e configure:

```bash
cp env_example.txt .env
```

Edite o arquivo `.env` com suas configurações:

```env
MONGODB_URI=mongodb://localhost:27017/
DATABASE_NAME=rastreamento_entregas
COLLECTION_NAME=localizacoes
```

### 3. MongoDB

Certifique-se de que o MongoDB está rodando:

```bash
# Ubuntu/Debian
sudo systemctl start mongod

# macOS (Homebrew)
brew services start mongodb-community

# Windows
net start MongoDB
```

## 🚀 Execução Rápida

### Script Automático (Recomendado)

```bash
# Executa tudo automaticamente
./run.sh
```

### Comandos Docker Manuais

```bash
# Iniciar todos os serviços
docker compose up

# Executar em background
docker compose up -d

# Parar serviços
docker compose down
```

### Serviços Docker

- **MongoDB**: `localhost:27017` (admin/rastreamento123)
- **Aplicação**: Container interativo com menu

## 🚀 Uso

### Execução Automática

```bash
# Executa tudo automaticamente
./run.sh
```

### Execução Local

```bash
# 1. Criar ambiente virtual
python3 -m venv venv
source venv/bin/activate

# 2. Instalar dependências
pip install -r requirements.txt

# 3. Configurar .env (copie de env_example.txt)
cp env_example.txt .env

# 4. Executar o sistema
python main.py
```


O sistema oferece um menu interativo com as seguintes opções:

1. **🔄 Gerar dados simulados** - Cria e insere dados de rastreamento
2. **🗺️ Criar mapa interativo** - Gera mapa HTML com visualização
3. **📊 Exibir estatísticas** - Mostra estatísticas dos dados
4. **🧹 Limpar dados do banco** - Remove todos os dados
5. **🔍 Consultar localizações** - Lista localizações armazenadas
6. **🚀 Executar sistema completo** - Gera dados e cria mapa automaticamente

### Execução Individual

```bash
# Apenas gerar dados simulados
python simulador_dados.py

# Apenas criar mapa
python mapa_interativo.py
```

## 📁 Estrutura do Projeto

```
├── main.py                 # Script principal com menu interativo
├── config.py              # Configurações do sistema
├── database.py            # Gerenciamento do MongoDB
├── simulador_dados.py     # Geração de dados simulados
├── mapa_interativo.py     # Criação de mapas com Folium
├── requirements.txt       # Dependências Python
├── env_example.txt        # Exemplo de configuração
├── Dockerfile            # Imagem Docker da aplicação
├── docker-compose.yml    # Orquestração dos serviços
├── run.sh                # Script de execução automática
├── mongo-init/           # Scripts de inicialização MongoDB
└── README.md             # Este arquivo
```

## 🗄️ Estrutura da Coleção MongoDB

### Coleção: `localizacoes`

```json
{
  "usuario": "motoboy_001",
  "timestamp": "2024-01-15T10:30:00",
  "localizacao": {
    "type": "Point",
    "coordinates": [-48.2672, -18.9176]
  },
  "velocidade": 35.5,
  "progresso_rota": 25.5,
  "metadados": {
    "ponto_sequencial": 13,
    "total_pontos": 50,
    "rua": "Av. João Naves de Ávila",
    "cidade": "Uberlândia",
    "estado": "MG"
  }
}
```

### Índices

- **Geoespacial**: `localizacao` (2dsphere)
- **Temporal**: `timestamp` (ascendente)
- **Usuário**: `usuario` (texto)

## 🗺️ Recursos do Mapa

- **Marcadores**: Início (verde), fim (vermelho), posição atual (moto)
- **Trajeto**: Linha azul conectando todos os pontos
- **Pontos Sequenciais**: Círculos coloridos por velocidade
- **Heatmap**: Visualização de intensidade por velocidade
- **Timeline**: Controle de tempo para animação
- **Legenda**: Explicação dos elementos visuais

## 📊 Estatísticas Geradas

- Total de pontos de rastreamento
- Velocidade média, máxima e mínima
- Distância aproximada da rota
- Tempo total de deslocamento
- Progresso percentual da rota

## 🔧 Configurações Avançadas

### Personalizar Rota

Edite `config.py` para alterar:

```python
ROUTE_CONFIG = {
    'inicio': {
        'nome': 'IFTM',
        'latitude': -18.9176,
        'longitude': -48.2672
    },
    'fim': {
        'nome': 'Center Shopping',
        'latitude': -18.9201,
        'longitude': -48.2523
    }
}
```

### Ajustar Simulação

```python
SIMULATION_CONFIG = {
    'total_pontos': 50,           # Número de pontos
    'intervalo_segundos': 30,     # Intervalo entre pontos
    'velocidade_media_kmh': 35,   # Velocidade média
    'variacao_posicao': 0.0001    # Variação nas coordenadas
}
```

## 🐛 Solução de Problemas

### Erro de Conexão MongoDB

```bash
# Verificar se MongoDB está rodando
sudo systemctl status mongod

# Verificar porta
netstat -tulpn | grep 27017
```

### Dependências Faltando

```bash
pip install --upgrade pip
pip install -r requirements.txt
```

### Erro de Permissão

```bash
# Linux/macOS
chmod +x main.py
```

## 📝 Notas Importantes

- ⚠️ **Dados Simulados**: Este sistema usa apenas coordenadas simuladas aproximadas
- 🔒 **Segurança**: Não inclui dados reais de GPS privados
- 📍 **Precisão**: Coordenadas são aproximadas para fins educacionais
- 🗺️ **Visualização**: Mapas são salvos como arquivos HTML independentes

## 🤝 Contribuição

1. Fork o projeto
2. Crie uma branch para sua feature
3. Commit suas mudanças
4. Push para a branch
5. Abra um Pull Request

## 📄 Licença

Este projeto é para fins educacionais e de demonstração.

---

**Desenvolvido para o curso de NoSQL - IFTM**
