#!/bin/bash

# Script para testar os endpoints com armazenamento apenas de IDs
# Execute este script após iniciar a aplicação Spring Boot

BASE_URL="http://localhost:8080"

echo "=== Testando Relacionamentos com Armazenamento de IDs Apenas ==="
echo

# 1. Criar alguns estudantes primeiro
echo "1. Criando estudantes..."
echo

ESTUDANTE1=$(curl -s -X POST "$BASE_URL/estudantes" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Silva",
    "matricula": "2024001"
  }' | jq -r '.id')

ESTUDANTE2=$(curl -s -X POST "$BASE_URL/estudantes" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Maria Santos",
    "matricula": "2024002"
  }' | jq -r '.id')

ESTUDANTE3=$(curl -s -X POST "$BASE_URL/estudantes" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Pedro Oliveira",
    "matricula": "2024003"
  }' | jq -r '.id')

echo "Estudantes criados:"
echo "- João Silva (ID: $ESTUDANTE1)"
echo "- Maria Santos (ID: $ESTUDANTE2)"
echo "- Pedro Oliveira (ID: $ESTUDANTE3)"
echo

# 2. Criar alguns cursos primeiro
echo "2. Criando cursos..."
echo

CURSO1=$(curl -s -X POST "$BASE_URL/cursos" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Programação Java",
    "descricao": "Curso de programação em Java"
  }' | jq -r '.id')

CURSO2=$(curl -s -X POST "$BASE_URL/cursos" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Banco de Dados",
    "descricao": "Curso de banco de dados"
  }' | jq -r '.id')

CURSO3=$(curl -s -X POST "$BASE_URL/cursos" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Desenvolvimento Web",
    "descricao": "Curso de desenvolvimento web"
  }' | jq -r '.id')

echo "Cursos criados:"
echo "- Programação Java (ID: $CURSO1)"
echo "- Banco de Dados (ID: $CURSO2)"
echo "- Desenvolvimento Web (ID: $CURSO3)"
echo

# 3. Criar um curso com múltiplos estudantes
echo "3. Criando curso com múltiplos estudantes..."
echo

CURSO_COM_ESTUDANTES=$(curl -s -X POST "$BASE_URL/cursos/com-estudantes" \
  -H "Content-Type: application/json" \
  -d "{
    \"nome\": \"Engenharia de Software\",
    \"descricao\": \"Curso completo de engenharia de software\",
    \"estudanteIds\": [\"$ESTUDANTE1\", \"$ESTUDANTE2\"]
  }" | jq -r '.id')

echo "Curso 'Engenharia de Software' criado com estudantes João e Maria (ID: $CURSO_COM_ESTUDANTES)"
echo

# 4. Criar um estudante com múltiplos cursos
echo "4. Criando estudante com múltiplos cursos..."
echo

ESTUDANTE_COM_CURSOS=$(curl -s -X POST "$BASE_URL/estudantes/com-cursos" \
  -H "Content-Type: application/json" \
  -d "{
    \"nome\": \"Ana Costa\",
    \"matricula\": \"2024004\",
    \"cursoIds\": [\"$CURSO1\", \"$CURSO2\"]
  }" | jq -r '.id')

echo "Estudante 'Ana Costa' criado com cursos Programação Java e Banco de Dados (ID: $ESTUDANTE_COM_CURSOS)"
echo

# 5. Testar endpoints que retornam apenas IDs
echo "5. Testando endpoints que retornam apenas IDs..."
echo

echo "Listando cursos (apenas IDs):"
curl -s -X GET "$BASE_URL/cursos" | jq '.'

echo
echo "Listando estudantes (apenas IDs):"
curl -s -X GET "$BASE_URL/estudantes" | jq '.'

echo

# 6. Testar endpoints que retornam dados completos
echo "6. Testando endpoints que retornam dados completos..."
echo

echo "Listando cursos completos (com estudantes):"
curl -s -X GET "$BASE_URL/cursos/completos" | jq '.'

echo
echo "Listando estudantes completos (com cursos):"
curl -s -X GET "$BASE_URL/estudantes/completos" | jq '.'

echo

# 7. Buscar curso específico (apenas IDs)
echo "7. Buscando curso específico (apenas IDs):"
echo

curl -s -X GET "$BASE_URL/cursos/$CURSO_COM_ESTUDANTES" | jq '.'

echo

# 8. Buscar curso específico (completo)
echo "8. Buscando curso específico (completo):"
echo

curl -s -X GET "$BASE_URL/cursos/$CURSO_COM_ESTUDANTES/completo" | jq '.'

echo

# 9. Buscar estudante específico (apenas IDs)
echo "9. Buscando estudante específico (apenas IDs):"
echo

curl -s -X GET "$BASE_URL/estudantes/$ESTUDANTE_COM_CURSOS" | jq '.'

echo

# 10. Buscar estudante específico (completo)
echo "10. Buscando estudante específico (completo):"
echo

curl -s -X GET "$BASE_URL/estudantes/$ESTUDANTE_COM_CURSOS/completo" | jq '.'

echo

# 11. Adicionar estudante a um curso existente
echo "11. Adicionando Pedro ao curso de Desenvolvimento Web..."
echo

curl -s -X POST "$BASE_URL/cursos/$CURSO3/estudantes/$ESTUDANTE3" \
  -H "Content-Type: application/json" | jq '.'

echo

# 12. Adicionar curso a um estudante existente
echo "12. Adicionando curso de Desenvolvimento Web ao João..."
echo

curl -s -X POST "$BASE_URL/estudantes/$ESTUDANTE1/cursos/$CURSO3" \
  -H "Content-Type: application/json" | jq '.'

echo

# 13. Verificar estado final - apenas IDs
echo "13. Estado final - Cursos (apenas IDs):"
echo

curl -s -X GET "$BASE_URL/cursos" | jq '.'

echo

echo "14. Estado final - Estudantes (apenas IDs):"
echo

curl -s -X GET "$BASE_URL/estudantes" | jq '.'

echo

# 14. Verificar estado final - dados completos
echo "15. Estado final - Cursos completos:"
echo

curl -s -X GET "$BASE_URL/cursos/completos" | jq '.'

echo

echo "16. Estado final - Estudantes completos:"
echo

curl -s -X GET "$BASE_URL/estudantes/completos" | jq '.'

echo

# 15. Remover estudante de um curso
echo "17. Removendo Maria do curso de Engenharia de Software..."
echo

curl -s -X DELETE "$BASE_URL/cursos/$CURSO_COM_ESTUDANTES/estudantes/$ESTUDANTE2" \
  -H "Content-Type: application/json" | jq '.'

echo

# 16. Remover curso de um estudante
echo "18. Removendo curso de Banco de Dados da Ana..."
echo

curl -s -X DELETE "$BASE_URL/estudantes/$ESTUDANTE_COM_CURSOS/cursos/$CURSO2" \
  -H "Content-Type: application/json" | jq '.'

echo

# 17. Verificar estado final após remoções
echo "19. Estado final após remoções - Cursos completos:"
echo

curl -s -X GET "$BASE_URL/cursos/completos" | jq '.'

echo

echo "20. Estado final após remoções - Estudantes completos:"
echo

curl -s -X GET "$BASE_URL/estudantes/completos" | jq '.'

echo
echo "=== Teste concluído! ==="
echo
echo "Resumo das melhorias:"
echo "- Armazenamento apenas de IDs (mais eficiente)"
echo "- Endpoints separados para dados simples e completos"
echo "- Relação muitos-para-muitos mantida com IDs"
echo "- Performance melhorada (menos dados transferidos)"
