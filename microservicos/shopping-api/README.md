# Shopping API - Microsserviço de Pedidos

## 📋 Resumo da Implementação

O microsserviço **Shopping API** foi implementado seguindo exatamente as especificações fornecidas, utilizando **Spring Boot 3.5.6** e **MongoDB** com documentos embutidos para gerenciar pedidos/compras do mini e-commerce.

## 🏗️ Arquitetura Implementada

### Estrutura de Pastas (Padrão Maven)
```
shopping-api/
├── src/main/java/com/andrelvicente/api/product/shopping_api/
│   ├── controller/
│   │   └── ShoppingController.java
│   ├── models/
│   │   ├── dto/
│   │   │   ├── ShopDTO.java
│   │   │   ├── ShopItemDTO.java
│   │   │   └── ShopReportDTO.java
│   │   ├── Shop.java
│   │   └── ShopItem.java
│   ├── repositories/
│   │   └── ShopRepository.java
│   ├── services/
│   │   └── ShoppingService.java
│   └── ShoppingApiApplication.java
├── src/main/resources/
│   └── application.properties
├── pom.xml
├── API_DOCUMENTATION.md
├── test_requests.http
└── README.md
```

## 🗄️ Modelagem de Dados

### Collection: `shop`
- **Abordagem**: Documentos embutidos no MongoDB
- **Racional**: Preços podem variar por pedido, então são armazenados dentro do documento
- **Índices**: `userIdentifier` e `date` para otimização de consultas

### Estrutura do Documento
```json
{
  "_id": "ObjectId",
  "userIdentifier": "string",
  "date": "ISODate", 
  "items": [
    {
      "productIdentifier": "string",
      "price": "number",
      "quantity": "number"
    }
  ],
  "total": "number"
}
```

## 🌐 Endpoints Implementados

| Método | Rota | Método Java | Descrição |
|--------|------|-------------|-----------|
| `GET` | `/shopping` | `getAll` | Retorna todos os pedidos |
| `GET` | `/shopping/{id}` | `findById` | Busca pedido por ID |
| `POST` | `/shopping` | `save` | Cria novo pedido |
| `GET` | `/shopping/shopByUser` | `getByUser` | Busca por usuário (query: `userId`) |
| `GET` | `/shopping/shopByDate` | `getByDate` | Busca por data (query: `dataInicio`, `dataFim`) |
| `GET` | `/shopping/{productIdentifier}` | `findByProductIdentifier` | Busca por produto |
| `GET` | `/shopping/search` | `getShopsByFilter` | Filtros avançados (query: `dataInicio`, `dataFim`, `valorMinimo`) |
| `GET` | `/shopping/report` | `getReportByDate` | Relatório por data (query: `dataInicio`, `dataFim`) |

## 🔧 Tecnologias e Dependências

- **Spring Boot 3.5.6** - Framework principal
- **Spring Data MongoDB** - Persistência com MongoDB
- **Spring Web** - API REST
- **Spring Validation** - Validação de dados
- **Lombok** - Redução de boilerplate
- **SpringDoc OpenAPI** - Documentação da API
- **Java 17** - Linguagem de programação

## ⚙️ Configuração

### application.properties
```properties
spring.application.name=shopping-api
spring.data.mongodb.uri=mongodb://root:example@localhost:27017
spring.data.mongodb.database=aula04set25-andre
server.port=8083
```

### MongoDB
- **Host**: localhost:27017
- **Database**: aula04set25-andre
- **Collection**: shop

## 🚀 Como Executar

1. **Certifique-se de que o MongoDB esteja rodando**
2. **Compile o projeto**:
   ```bash
   ./mvnw clean compile
   ```
3. **Execute a aplicação**:
   ```bash
   ./mvnw spring-boot:run
   ```
4. **Acesse a aplicação**:
   - API: `http://localhost:8083`
   - Swagger UI: `http://localhost:8083/swagger-ui.html`
   - OpenAPI JSON: `http://localhost:8083/api-docs`

## 📊 Funcionalidades Implementadas

### ✅ Operações CRUD Básicas
- Criar pedidos com validação completa
- Buscar todos os pedidos
- Buscar pedido por ID
- Validação de dados com Bean Validation

### ✅ Consultas Avançadas
- Busca por usuário
- Busca por intervalo de datas
- Busca por produto (em documentos embutidos)
- Filtros combinados (data + valor mínimo)

### ✅ Relatórios
- Agregação por data
- Total de vendas
- Número de pedidos
- Valor médio por pedido

### ✅ Validações
- Campos obrigatórios
- Valores positivos
- Estrutura de objetos
- Formato de datas

## 📝 Documentação

- **API_DOCUMENTATION.md**: Documentação completa da API
- **test_requests.http**: Arquivo com exemplos de requisições HTTP
- **Swagger UI**: Documentação interativa disponível em `/swagger-ui.html`

## 🧪 Testes

Use o arquivo `test_requests.http` para testar todos os endpoints ou acesse o Swagger UI para testes interativos.

## ✨ Características Especiais

1. **Documentos Embutidos**: Preços são armazenados no pedido para capturar variações
2. **Índices Otimizados**: Campos frequentes são indexados
3. **Validação Robusta**: Validação completa de entrada
4. **Documentação Automática**: Swagger/OpenAPI integrado
5. **Logging Detalhado**: Logs para debugging e monitoramento
6. **Tratamento de Erros**: Respostas HTTP apropriadas
7. **Flexibilidade de Datas**: Suporte a múltiplos formatos de data

## 🎯 Conformidade com Especificações

✅ **Nomes de endpoints exatos** conforme documento  
✅ **Parâmetros com nomes específicos** (`dataInicio`, `dataFim`, `valorMinimo`)  
✅ **Estrutura de pastas padrão Maven**  
✅ **Modelagem com documentos embutidos**  
✅ **Collection `shop` no MongoDB**  
✅ **Validações e boas práticas**  
✅ **Documentação completa**  

O microsserviço está pronto para uso e totalmente funcional!
