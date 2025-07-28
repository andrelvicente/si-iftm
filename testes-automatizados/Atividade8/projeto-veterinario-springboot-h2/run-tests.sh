#!/bin/bash

# Script para executar testes automatizados do Sistema de Veterinários

echo "=========================================="
echo "  TESTES AUTOMATIZADOS - VETERINÁRIOS   "
echo "=========================================="

# Função para verificar se a aplicação está rodando
check_app_running() {
    echo "Verificando se a aplicação está rodando..."
    if curl -s http://localhost:8080/home > /dev/null 2>&1; then
        echo "✅ Aplicação está rodando em http://localhost:8080"
        return 0
    else
        echo "❌ Aplicação não está rodando"
        return 1
    fi
}

# Função para executar todos os testes
run_all_tests() {
    echo ""
    echo "🔄 Executando todos os testes Selenium WebDriver..."
    cd gerenciador-Veterinarios
    ./mvnw test -Dtest="*SeleniumTest" -q
    echo "✅ Testes concluídos!"
}

# Função para executar teste específico
run_specific_test() {
    echo ""
    echo "Testes disponíveis:"
    echo "1. CadastrarVeterinarioSeleniumTest"
    echo "2. PesquisarVeterinarioSeleniumTest"
    echo "3. ExcluirVeterinarioSeleniumTest"
    echo "4. AlterarVeterinarioSeleniumTest"
    echo "5. ListarVeterinarioSeleniumTest"
    
    read -p "Digite o número do teste (1-5): " choice
    
    case $choice in
        1) test_class="CadastrarVeterinarioSeleniumTest" ;;
        2) test_class="PesquisarVeterinarioSeleniumTest" ;;
        3) test_class="ExcluirVeterinarioSeleniumTest" ;;
        4) test_class="AlterarVeterinarioSeleniumTest" ;;
        5) test_class="ListarVeterinarioSeleniumTest" ;;
        *) echo "❌ Opção inválida"; return ;;
    esac
    
    echo "🔄 Executando $test_class..."
    cd gerenciador-Veterinarios
    ./mvnw test -Dtest="$test_class" -q
    echo "✅ Teste concluído!"
}

# Função para iniciar aplicação
start_app() {
    echo "🚀 Iniciando aplicação..."
    cd gerenciador-Veterinarios
    echo "A aplicação será iniciada em http://localhost:8080"
    echo "Pressione Ctrl+C para parar a aplicação"
    ./mvnw spring-boot:run
}

# Menu principal
main_menu() {
    echo ""
    echo "Escolha uma opção:"
    echo "1. Verificar se aplicação está rodando"
    echo "2. Executar todos os testes"
    echo "3. Executar teste específico"
    echo "4. Iniciar aplicação"
    echo "5. Sair"
    
    read -p "Digite sua escolha (1-5): " choice
    
    case $choice in
        1) check_app_running ;;
        2) 
            if check_app_running; then
                run_all_tests
            else
                echo "⚠️  Inicie a aplicação primeiro (opção 4)"
            fi
            ;;
        3) 
            if check_app_running; then
                run_specific_test
            else
                echo "⚠️  Inicie a aplicação primeiro (opção 4)"
            fi
            ;;
        4) start_app ;;
        5) echo "👋 Até logo!"; exit 0 ;;
        *) echo "❌ Opção inválida" ;;
    esac
}

# Loop principal
while true; do
    main_menu
    echo ""
    read -p "Pressione Enter para continuar..."
done
