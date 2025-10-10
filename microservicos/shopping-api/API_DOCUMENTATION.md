# Shopping API - Documentação

## Visão Geral

A **Shopping API** é um microsserviço responsável pelo gerenciamento de pedidos/compras do mini e-commerce. Utiliza **MongoDB** com documentos embutidos para armazenar informações de preço por pedido.

## Tecnologias

- **Spring Boot 3.5.6**
- **MongoDB** com Spring Data MongoDB
- **Java 17**
- **Lombok** para redução de boilerplate
- **SpringDoc OpenAPI** para documentação da API
- **Bean Validation** para validação de dados

## Estrutura do Projeto

```
src/main/java/com/andrelvicente/api/product/shopping_api/
├── controller/
│   └── ShoppingController.java
├── models/
│   ├── dto/
│   │   ├── ShopDTO.java
│   │   ├── ShopItemDTO.java
│   │   └── ShopReportDTO.java
│   ├── Shop.java
│   └── ShopItem.java
├── repositories/
│   └── ShopRepository.java
├── services/
│   └── ShoppingService.java
└── ShoppingApiApplication.java
```

## Modelagem de Dados

### Collection: `shop`

A collection `shop` armazena documentos com a seguinte estrutura:

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

### Características da Modelagem

- **Documentos Embutidos**: As informações de preço dos produtos são armazenadas dentro do documento de pedido
- **Racional**: O preço pode variar por pedido, então é necessário capturar o valor exato no momento da compra
- **Índices**: `userIdentifier` e `date` são indexados para melhor performance

## Endpoints da API

**Base URL**: `http://localhost:8083/shopping`

### 1. GET `/shopping` - getAll
Retorna todos os pedidos.

**Resposta**: `200 OK`
```json
[
  {
    "id": "507f1f77bcf86cd799439011",
    "userIdentifier": "user123",
    "date": "2024-01-15T10:30:00",
    "items": [
      {
        "productIdentifier": "prod001",
        "price": 29.99,
        "quantity": 2
      }
    ],
    "total": 59.98
  }
]
```

### 2. GET `/shopping/{id}` - findById
Busca pedido por ID.

**Parâmetros**:
- `id` (path): ID do pedido

**Resposta**: `200 OK` ou `404 Not Found`

### 3. POST `/shopping` - save
Cria um novo pedido.

**Body**:
```json
{
  "userIdentifier": "user123",
  "date": "2024-01-15T10:30:00",
  "items": [
    {
      "productIdentifier": "prod001",
      "price": 29.99,
      "quantity": 2
    }
  ],
  "total": 59.98
}
```

**Resposta**: `201 Created`

### 4. GET `/shopping/shopByUser` - getByUser
Busca pedidos por usuário.

**Parâmetros**:
- `userId` (query): ID do usuário

**Exemplo**: `/shopping/shopByUser?userId=user123`

### 5. GET `/shopping/shopByDate` - getByDate
Busca pedidos por intervalo de datas.

**Parâmetros**:
- `dataInicio` (query): Data de início (formato: `yyyy-MM-dd` ou `yyyy-MM-ddTHH:mm:ss`)
- `dataFim` (query): Data de fim (formato: `yyyy-MM-dd` ou `yyyy-MM-ddTHH:mm:ss`)

**Exemplo**: `/shopping/shopByDate?dataInicio=2024-01-01&dataFim=2024-01-31`

### 6. GET `/shopping/{productIdentifier}` - findByProductIdentifier
Busca pedidos que contenham um produto específico.

**Parâmetros**:
- `productIdentifier` (path): Identificador do produto

**Exemplo**: `/shopping/prod001`

### 7. GET `/shopping/search` - getShopsByFilter
Busca pedidos com filtros avançados.

**Parâmetros**:
- `dataInicio` (query): Data de início
- `dataFim` (query): Data de fim
- `valorMinimo` (query, opcional): Valor mínimo do pedido

**Exemplo**: `/shopping/search?dataInicio=2024-01-01&dataFim=2024-01-31&valorMinimo=50.0`

### 8. GET `/shopping/report` - getReportByDate
Gera relatório de vendas por intervalo de datas.

**Parâmetros**:
- `dataInicio` (query): Data de início
- `dataFim` (query): Data de fim

**Resposta**:
```json
[
  {
    "date": "2024-01-15",
    "totalOrders": 5,
    "totalSales": 299.50,
    "averageOrderValue": 59.90
  }
]
```

## Configuração

### application.properties

```properties
spring.application.name=shopping-api
spring.data.mongodb.uri=mongodb://root:example@localhost:27017
spring.data.mongodb.database=aula04set25-andre
server.port=8083
```

### MongoDB

Certifique-se de que o MongoDB esteja rodando na porta 27017 com as credenciais configuradas.

## Documentação Interativa

Após iniciar a aplicação, acesse:
- **Swagger UI**: `http://localhost:8083/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8083/api-docs`

## Validações

A API inclui validações para:
- Campos obrigatórios (`@NotBlank`, `@NotNull`)
- Valores positivos (`@Positive`)
- Estrutura de objetos (`@Valid`)

## Exemplos de Uso

### Criar um pedido
```bash
curl -X POST http://localhost:8083/shopping \
  -H "Content-Type: application/json" \
  -d '{
    "userIdentifier": "user123",
    "date": "2024-01-15T10:30:00",
    "items": [
      {
        "productIdentifier": "prod001",
        "price": 29.99,
        "quantity": 2
      }
    ],
    "total": 59.98
  }'
```

### Buscar pedidos por usuário
```bash
curl "http://localhost:8083/shopping/shopByUser?userId=user123"
```

### Buscar pedidos por data
```bash
curl "http://localhost:8083/shopping/shopByDate?dataInicio=2024-01-01&dataFim=2024-01-31"
```

### Gerar relatório
```bash
curl "http://localhost:8083/shopping/report?dataInicio=2024-01-01&dataFim=2024-01-31"
```

## Executando a Aplicação

1. Certifique-se de que o MongoDB esteja rodando
2. Execute: `mvn spring-boot:run`
3. A aplicação estará disponível em `http://localhost:8083`
