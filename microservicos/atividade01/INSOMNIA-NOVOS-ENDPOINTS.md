# Novos Endpoints - Relacionamentos Muitos-para-Muitos

## 📋 Resumo das Adições

Foram adicionados **6 novos endpoints** ao arquivo `MongoDB-Relacionamentos-Insomnia.json` para suportar a relação muitos-para-muitos entre cursos e estudantes.

## 🎓 Novos Endpoints de Cursos

### 1. Criar Curso com Estudantes
- **Método**: `POST`
- **URL**: `{{ _.base_url }}/cursos/com-estudantes`
- **Descrição**: Cria um curso com múltiplos estudantes
- **Body**:
```json
{
  "nome": "Engenharia de Software",
  "descricao": "Curso completo de engenharia de software",
  "estudanteIds": ["estudante_id_1", "estudante_id_2"]
}
```

### 2. Adicionar Estudante ao Curso
- **Método**: `POST`
- **URL**: `{{ _.base_url }}/cursos/{{ _.curso_id }}/estudantes/{{ _.estudante_id }}`
- **Descrição**: Adiciona um estudante a um curso existente
- **Body**: Vazio

### 3. Remover Estudante do Curso
- **Método**: `DELETE`
- **URL**: `{{ _.base_url }}/cursos/{{ _.curso_id }}/estudantes/{{ _.estudante_id }}`
- **Descrição**: Remove um estudante de um curso
- **Body**: Vazio

## 🎒 Novos Endpoints de Estudantes

### 4. Criar Estudante com Cursos
- **Método**: `POST`
- **URL**: `{{ _.base_url }}/estudantes/com-cursos`
- **Descrição**: Cria um estudante com múltiplos cursos
- **Body**:
```json
{
  "nome": "Ana Costa",
  "matricula": "2024004",
  "cursoIds": ["curso_id_1", "curso_id_2"]
}
```

### 5. Adicionar Curso ao Estudante
- **Método**: `POST`
- **URL**: `{{ _.base_url }}/estudantes/{{ _.estudante_id }}/cursos/{{ _.curso_id }}`
- **Descrição**: Adiciona um curso a um estudante existente
- **Body**: Vazio

### 6. Remover Curso do Estudante
- **Método**: `DELETE`
- **URL**: `{{ _.base_url }}/estudantes/{{ _.estudante_id }}/cursos/{{ _.curso_id }}`
- **Descrição**: Remove um curso de um estudante
- **Body**: Vazio

## 🔧 Variáveis de Ambiente Adicionadas

O environment foi atualizado com as seguintes variáveis:

- `curso_id`: ID de exemplo para testes (substitua pelos IDs reais)
- `estudante_id`: ID de exemplo para testes (substitua pelos IDs reais)

## 📝 Como Usar no Insomnia

### 1. Importar a Collection
1. Abra o Insomnia
2. Clique em "Import" 
3. Selecione o arquivo `MongoDB-Relacionamentos-Insomnia.json`
4. A collection será importada com todos os endpoints

### 2. Configurar Environment
1. Vá em "Manage Environments"
2. Edite o "Base Environment"
3. Atualize as variáveis:
   - `base_url`: URL da sua aplicação (ex: `http://localhost:8080`)
   - `curso_id`: ID de um curso existente
   - `estudante_id`: ID de um estudante existente

### 3. Testar os Endpoints

#### Fluxo Recomendado:
1. **Criar estudantes** usando o endpoint "Criar Estudante"
2. **Criar cursos** usando o endpoint "Criar Curso"
3. **Testar relacionamentos**:
   - Use "Criar Curso com Estudantes" para criar um curso já com estudantes
   - Use "Criar Estudante com Cursos" para criar um estudante já com cursos
   - Use os endpoints de adicionar/remover para gerenciar relacionamentos dinamicamente

#### Exemplo de Teste:
1. Crie alguns estudantes e anote os IDs retornados
2. Crie alguns cursos e anote os IDs retornados
3. Atualize as variáveis `curso_id` e `estudante_id` no environment
4. Teste os endpoints de relacionamento

## 🎯 Benefícios dos Novos Endpoints

- **Flexibilidade**: Permite criar entidades com relacionamentos ou sem
- **Gerenciamento Dinâmico**: Adicionar/remover relacionamentos após criação
- **Consistência**: Mantém a relação bidirecional automaticamente
- **Facilidade de Teste**: Endpoints específicos para cada operação

## 📊 Estrutura da Collection

A collection agora contém:
- **👤 Usuários**: 5 endpoints (CRUD básico)
- **🎓 Cursos**: 8 endpoints (CRUD + relacionamentos)
- **🎒 Estudantes**: 8 endpoints (CRUD + relacionamentos)
- **👤 Perfis**: 5 endpoints (CRUD básico)
- **📝 Postagens**: 5 endpoints (CRUD básico)

**Total**: 31 endpoints organizados em 5 grupos

## 🔄 Relação Muitos-para-Muitos

Os novos endpoints implementam uma relação muitos-para-muitos onde:
- Um curso pode ter vários estudantes
- Um estudante pode estar em vários cursos
- A relação é mantida em ambas as entidades
- Operações de adicionar/remover são bidirecionais

Agora você pode testar completamente a funcionalidade de relacionamentos muitos-para-muitos usando o Insomnia!
