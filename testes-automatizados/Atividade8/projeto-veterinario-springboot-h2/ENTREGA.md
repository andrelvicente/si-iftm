# 📋 ENTREGA - ATIVIDADE 8 - TESTES AUTOMATIZADOS

## ✅ Entregas Realizadas

### Parte 1 - Selenium IDE (Scripts .side)

- ✅ **1-CadastrarVeterinario.side** - Teste completo de cadastro
- ✅ **2-PesquisarVeterinario.side** - Testes de pesquisa (nome existente e campo vazio)
- ✅ **3-ExcluirVeterinario.side** - Testes de exclusão e verificação de botões
- ✅ **4-AlterarVeterinario.side** - Teste completo de alteração de dados
- ✅ **5-ListarVeterinario.side** - Testes de listagem e navegação

### Parte 2 - Selenium WebDriver (Classes Java)

- ✅ **CadastrarVeterinarioSeleniumTest.java** - 4 cenários de teste
- ✅ **PesquisarVeterinarioSeleniumTest.java** - 5 cenários de teste
- ✅ **ExcluirVeterinarioSeleniumTest.java** - 5 cenários de teste
- ✅ **AlterarVeterinarioSeleniumTest.java** - 5 cenários de teste
- ✅ **ListarVeterinarioSeleniumTest.java** - 7 cenários de teste

## 🎯 Características Implementadas

### ✅ Todos os casos de teste são completos:

1. **Definição do cenário** - Contexto e dados de entrada claramente definidos
2. **Chamada da ação** - Passos de execução detalhados e comentados
3. **Verificação da resposta** - Assertions específicas usando assertValue e assertText

### ✅ Uso correto das verificações:

- **assertValue**: Verificação de valores em campos de formulários
- **assertText**: Verificação de conteúdo em tabelas, labels e elementos de texto
- **assertElementPresent**: Verificação de existência de elementos
- **assertAttribute**: Verificação de atributos HTML

### ✅ Cobertura completa das funcionalidades:

- **CRUD Completo**: Create, Read, Update, Delete
- **Validações**: Campos obrigatórios, formato de email, dados inválidos
- **Interface**: Navegação, botões, estrutura de páginas
- **Edge Cases**: Listas vazias, dados inexistentes, pesquisas parciais

## 📁 Estrutura de Arquivos Entregues

```
projeto-veterinario-springboot-h2/
├── README-TESTES.md                    # Documentação completa
├── run-tests.bat                       # Script Windows para execução
├── run-tests.sh                        # Script Linux/Mac para execução
├── selenium-ide-scripts/
│   ├── 1-CadastrarVeterinario.side
│   ├── 2-PesquisarVeterinario.side
│   ├── 3-ExcluirVeterinario.side
│   ├── 4-AlterarVeterinario.side
│   └── 5-ListarVeterinario.side
└── gerenciador-Veterinarios/
    ├── pom.xml                         # Dependências Selenium adicionadas
    ├── src/test/java/org/iftm/gerenciadorveterinarios/selenium/
    │   ├── BaseSeleniumTest.java       # Classe base para configuração
    │   ├── CadastrarVeterinarioSeleniumTest.java
    │   ├── PesquisarVeterinarioSeleniumTest.java
    │   ├── ExcluirVeterinarioSeleniumTest.java
    │   ├── AlterarVeterinarioSeleniumTest.java
    │   └── ListarVeterinarioSeleniumTest.java
    └── src/test/resources/
        └── application-test.properties  # Configuração para testes
```

## 🚀 Como Executar

### Opção 1: Script Automático (Recomendado)

```bash
# Windows
run-tests.bat

# Linux/Mac
chmod +x run-tests.sh
./run-tests.sh
```

### Opção 2: Manual

```bash
# 1. Iniciar aplicação (Terminal 1)
cd gerenciador-Veterinarios
./mvnw spring-boot:run

# 2. Executar testes (Terminal 2)
cd gerenciador-Veterinarios
./mvnw test -Dtest="*SeleniumTest"
```

### Opção 3: Selenium IDE

1. Instalar extensão Selenium IDE no Chrome/Firefox
2. Importar arquivos .side da pasta selenium-ide-scripts/
3. Executar com aplicação rodando em http://localhost:8080

## 📊 Resumo dos Testes

### Selenium IDE - 5 Scripts

- **Total de comandos**: ~150+ comandos
- **Assertions implementadas**: assertValue, assertText, assertElementPresent, assertAttribute
- **Cenários cobertos**: Fluxos principais + validações

### Selenium WebDriver - 5 Classes

- **Total de métodos de teste**: 26 métodos
- **Cenários implementados**: 26+ cenários únicos
- **Cobertura**: CRUD completo + validações + edge cases

## ⚠️ Observações Importantes

1. **Dependências**: Projeto configurado com Selenium 4.15.0 e WebDriverManager
2. **Navegador**: Testes configurados para Chrome (pode ser alterado)
3. **Banco de Dados**: H2 em memória para isolamento de testes
4. **Porta**: Aplicação roda em porta aleatória para evitar conflitos
