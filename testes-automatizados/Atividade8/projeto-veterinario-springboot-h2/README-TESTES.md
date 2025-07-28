# Testes Automatizados - Sistema de Gerenciamento de Veterinários

Este projeto contém testes automatizados completos para o sistema de gerenciamento de veterinários, implementando tanto **Selenium IDE** quanto **Selenium WebDriver** para todas as funcionalidades solicitadas.

## 📋 Funcionalidades Testadas

### Parte 1 - Selenium IDE

- ✅ Cadastrar Veterinário
- ✅ Pesquisar Veterinário
- ✅ Excluir Veterinário
- ✅ Alterar Veterinário
- ✅ Listar Veterinário

### Parte 2 - Selenium WebDriver

- ✅ Cadastrar Veterinário
- ✅ Pesquisar Veterinário
- ✅ Excluir Veterinário
- ✅ Alterar Veterinário
- ✅ Listar Veterinário

## 🏗️ Estrutura do Projeto

```
projeto-veterinario-springboot-h2/
├── gerenciador-Veterinarios/
│   ├── src/
│   │   ├── test/
│   │   │   └── java/
│   │   │       └── org/iftm/gerenciadorveterinarios/selenium/
│   │   │           ├── BaseSeleniumTest.java
│   │   │           ├── CadastrarVeterinarioSeleniumTest.java
│   │   │           ├── PesquisarVeterinarioSeleniumTest.java
│   │   │           ├── ExcluirVeterinarioSeleniumTest.java
│   │   │           ├── AlterarVeterinarioSeleniumTest.java
│   │   │           └── ListarVeterinarioSeleniumTest.java
│   │   └── main/...
│   └── pom.xml
└── selenium-ide-scripts/
    ├── 1-CadastrarVeterinario.side
    ├── 2-PesquisarVeterinario.side
    ├── 3-ExcluirVeterinario.side
    ├── 4-AlterarVeterinario.side
    └── 5-ListarVeterinario.side
```

## 🛠️ Tecnologias Utilizadas

- **Spring Boot 2.6.1** - Framework principal da aplicação
- **H2 Database** - Banco de dados em memória para testes
- **Selenium WebDriver 4.15.0** - Automação de testes web
- **WebDriverManager 5.3.2** - Gerenciamento automático de drivers
- **JUnit 5** - Framework de testes unitários
- **Maven** - Gerenciamento de dependências

## 🚀 Como Executar

### Pré-requisitos

- Java 8 ou superior
- Maven 3.6 ou superior
- Google Chrome instalado
- Selenium IDE (extensão do Chrome/Firefox) para executar scripts .side

### 1. Executar a Aplicação

```bash
cd gerenciador-Veterinarios
./mvnw spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080/home`

### 2. Executar Testes Selenium WebDriver

```bash
cd gerenciador-Veterinarios
./mvnw test -Dtest="*SeleniumTest"
```

Ou executar testes específicos:

```bash
# Teste de Cadastro
./mvnw test -Dtest="CadastrarVeterinarioSeleniumTest"

# Teste de Pesquisa
./mvnw test -Dtest="PesquisarVeterinarioSeleniumTest"

# Teste de Exclusão
./mvnw test -Dtest="ExcluirVeterinarioSeleniumTest"

# Teste de Alteração
./mvnw test -Dtest="AlterarVeterinarioSeleniumTest"

# Teste de Listagem
./mvnw test -Dtest="ListarVeterinarioSeleniumTest"
```

### 3. Executar Scripts Selenium IDE

1. Instale a extensão Selenium IDE no seu navegador
2. Abra a extensão
3. Importe os arquivos `.side` da pasta `selenium-ide-scripts/`
4. Execute os scripts individualmente ou em suíte

## 📝 Detalhes dos Testes

### Selenium WebDriver

Cada classe de teste implementa múltiplos cenários:

#### CadastrarVeterinarioSeleniumTest

- **Cenário 1**: Cadastrar veterinário com dados válidos
- **Cenário 2**: Verificar validação de campos obrigatórios
- **Cenário 3**: Verificar validação de formato de email
- **Cenário 4**: Verificar preenchimento automático e valores dos campos

#### PesquisarVeterinarioSeleniumTest

- **Cenário 1**: Pesquisar veterinário por nome existente
- **Cenário 2**: Pesquisar veterinário por nome inexistente
- **Cenário 3**: Realizar pesquisa com campo vazio
- **Cenário 4**: Verificar elementos da página de pesquisa
- **Cenário 5**: Verificar funcionalidade de pesquisa parcial

#### ExcluirVeterinarioSeleniumTest

- **Cenário 1**: Excluir veterinário existente
- **Cenário 2**: Verificar presença de botões de exclusão na tabela
- **Cenário 3**: Verificar estrutura dos botões de ação na tabela
- **Cenário 4**: Verificar links dos botões de exclusão
- **Cenário 5**: Verificar comportamento após exclusão de todos os veterinários

#### AlterarVeterinarioSeleniumTest

- **Cenário 1**: Alterar dados de veterinário existente
- **Cenário 2**: Verificar preenchimento automático do formulário de edição
- **Cenário 3**: Verificar validação de campos na edição
- **Cenário 4**: Verificar botão de editar na tabela
- **Cenário 5**: Verificar links dos botões de edição

#### ListarVeterinarioSeleniumTest

- **Cenário 1**: Verificar estrutura da tabela de listagem
- **Cenário 2**: Verificar conteúdo dos cabeçalhos da tabela
- **Cenário 3**: Verificar listagem de veterinários cadastrados
- **Cenário 4**: Verificar comportamento com lista vazia
- **Cenário 5**: Verificar formatação de dados na tabela
- **Cenário 6**: Verificar título da página
- **Cenário 7**: Verificar botões de navegação

### Selenium IDE

Os scripts `.side` implementam:

- **Assert Value**: Verificação de valores em formulários
- **Assert Text**: Verificação de conteúdo em tabelas e labels
- **Assert Element Present**: Verificação de existência de elementos
- **Assert Attribute**: Verificação de atributos HTML
- **Assert Location**: Verificação de URL/navegação

## 🔍 Tipos de Verificações Implementadas

### Assert Value (Formulários)

- Verificação de valores preenchidos em campos de entrada
- Validação de preenchimento automático em formulários de edição
- Conferência de placeholders e valores padrão

### Assert Text (Tabelas e Labels)

- Verificação de dados exibidos em tabelas
- Conferência de cabeçalhos e estrutura
- Validação de formatação (ex: "R$" nos salários)
- Verificação de títulos e textos da interface

### Verificações Adicionais

- Presença de botões e elementos de navegação
- Funcionalidade de links e redirecionamentos
- Validação HTML5 de formulários
- Comportamento com dados inexistentes

## 🎯 Casos de Teste Específicos

Cada caso de teste implementa:

1. **Definição do Cenário**: Contexto e dados de teste
2. **Chamada da Ação**: Passos de execução do teste
3. **Verificação da Resposta**: Assertions e validações

## 📊 Cobertura de Testes

- ✅ CRUD completo (Create, Read, Update, Delete)
- ✅ Validações de formulário
- ✅ Navegação entre páginas
- ✅ Formatação e exibição de dados
- ✅ Comportamento com dados vazios/inexistentes
- ✅ Interface de usuário e UX

## 🏆 Características dos Testes

- **Testes completos**: Cada funcionalidade possui múltiplos cenários
- **Assertions específicas**: Uso correto de assertValue e assertText
- **Cenários realistas**: Simulação de uso real da aplicação
- **Cobertura abrangente**: Testa funcionalidades e edge cases
- **Organização clara**: Código bem documentado e estruturado

## 🔧 Configuração do Ambiente

O projeto está configurado para:

- Executar testes em Chrome (configurável para headless)
- Gerenciar automaticamente o ChromeDriver
- Usar banco H2 em memória para isolamento de testes
- Executar em porta aleatória para evitar conflitos

## 📈 Execução e Relatórios

Os testes podem ser executados:

- Individualmente por classe
- Em conjunto por funcionalidade
- Como suíte completa
- Com relatórios detalhados do JUnit
