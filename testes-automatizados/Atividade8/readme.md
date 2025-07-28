# 🧪 Atividade A8 – Testes Funcionais com Selenium

Este repositório contém a resolução da Atividade A8, que tem como objetivo a criação de testes funcionais automatizados com **Selenium IDE**, utilizando como base o projeto [projeto-veterinario-springboot-h2](https://github.com/brunoqp78/projeto-veterinario-springboot-h2).

## ✅ Casos de Teste Implementados

### 1. Cadastrar Veterinário

**Objetivo:** Verificar se o sistema permite o cadastro de um novo veterinário.
**Passos:**

* Acessar a página de cadastro
* Preencher o formulário com nome, sobrenome e telefone
* Submeter o formulário
* Verificar se o novo veterinário aparece na lista com os dados corretos (`assert text`)

---

### 2. Pesquisar Veterinário

**Objetivo:** Verificar a funcionalidade de busca por nome do veterinário.
**Passos:**

* Acessar a lista de veterinários
* Usar o campo de busca
* Verificar se os resultados retornam corretamente (`assert text`)

---

### 3. Excluir Veterinário

**Objetivo:** Garantir que o sistema exclui corretamente um veterinário da lista.
**Passos:**

* Acessar a lista de veterinários
* Selecionar um veterinário para exclusão
* Confirmar a exclusão
* Verificar se ele não está mais presente na lista (`assert text` negativo)

---

### 4. Alterar Veterinário

**Objetivo:** Validar se é possível editar os dados de um veterinário existente.
**Passos:**

* Acessar a lista
* Selecionar um veterinário
* Modificar os dados no formulário
* Confirmar a alteração
* Verificar se os dados atualizados aparecem corretamente (`assert value`, `assert text`)

---

### 5. Listar Veterinários

**Objetivo:** Verificar se a listagem de veterinários carrega corretamente com todos os registros.
**Passos:**

* Acessar a página de listagem
* Verificar se os dados dos veterinários cadastrados são exibidos corretamente (`assert text`)