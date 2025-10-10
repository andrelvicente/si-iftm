# Product API - Documentação

## Visão Geral
A Product API é um microsserviço responsável pela gestão de produtos e categorias de um mini e-commerce. Utiliza MongoDB como banco de dados e Spring Boot como framework.

## Estrutura do Projeto
```
src/main/java/com/andrelvicente/prova01/product/product_api/
├── controller/
│   ├── CategoryController.java
│   └── ProductController.java
├── models/
│   ├── dto/
│   │   ├── CategoryDTO.java
│   │   └── ProductDTO.java
│   ├── Category.java
│   └── Product.java
├── repositories/
│   ├── CategoryRepository.java
│   └── ProductRepository.java
├── services/
│   ├── CategoryService.java
│   └── ProductService.java
└── ProductApiApplication.java
```

## Endpoints da API

### Categorias (/category)

#### GET /category
Retorna todas as categorias.
```json
[
  {
    "id": "64f8a1b2c3d4e5f6a7b8c9d0",
    "nome": "Eletrônicos"
  }
]
```

#### GET /category/pageable?page=0&size=10
Retorna categorias paginadas.
```json
{
  "content": [...],
  "pageable": {...},
  "totalElements": 5,
  "totalPages": 1
}
```

#### GET /category/{id}
Retorna uma categoria específica.

#### POST /category
Cria uma nova categoria.
```json
{
  "nome": "Eletrônicos"
}
```

#### PUT /category/{id}
Atualiza uma categoria existente.
```json
{
  "nome": "Eletrônicos e Informática"
}
```

#### DELETE /category/{id}
Exclui uma categoria.

### Produtos (/product)

#### GET /product
Retorna todos os produtos.
```json
[
  {
    "id": "64f8a1b2c3d4e5f6a7b8c9d1",
    "productIdentifier": "PROD001",
    "nome": "Smartphone Samsung",
    "descricao": "Smartphone Samsung Galaxy S23 com 128GB",
    "preco": 2500.00,
    "categoryId": "64f8a1b2c3d4e5f6a7b8c9d0"
  }
]
```

#### GET /product/pageable?page=0&size=10
Retorna produtos paginados.

#### GET /product/{id}
Retorna um produto específico.

#### GET /product/identifier/{productIdentifier}
Busca produto pelo identificador.

#### GET /product/category/{categoryId}
Retorna produtos de uma categoria específica.

#### POST /product
Cria um novo produto.
```json
{
  "productIdentifier": "PROD001",
  "nome": "Smartphone Samsung",
  "descricao": "Smartphone Samsung Galaxy S23 com 128GB",
  "preco": 2500.00,
  "categoryId": "64f8a1b2c3d4e5f6a7b8c9d0"
}
```

#### PUT /product/{id}
Atualiza um produto existente.

#### DELETE /product/{id}
Exclui um produto.

## Validações

### CategoryDTO
- `nome`: obrigatório, entre 2 e 100 caracteres

### ProductDTO
- `productIdentifier`: obrigatório, entre 3 e 50 caracteres
- `nome`: obrigatório, entre 2 e 200 caracteres
- `descricao`: obrigatório, entre 10 e 1000 caracteres
- `preco`: obrigatório, deve ser maior que zero
- `categoryId`: obrigatório

## Relacionamentos
- Cada produto pertence a uma categoria (relacionamento @DBRef)
- Categorias devem ser criadas antes dos produtos
- Não é possível criar produtos com categorias inexistentes

## Códigos de Resposta HTTP
- `200 OK`: Operação bem-sucedida
- `201 Created`: Recurso criado com sucesso
- `204 No Content`: Recurso excluído com sucesso
- `400 Bad Request`: Dados inválidos ou erro de validação
- `404 Not Found`: Recurso não encontrado
- `500 Internal Server Error`: Erro interno do servidor

## Configuração do Banco
- MongoDB URI: `mongodb://root:example@localhost:27017`
- Database: `aula04set25-andre`
- Collections: `categories`, `products`
