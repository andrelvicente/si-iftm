#!/bin/bash

# 🚀 Script de Execução - Sistema de Rastreamento
# Cria ambiente e executa a aplicação

set -e

# Cores
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
RED='\033[0;31m'
NC='\033[0m'

print_message() {
    echo -e "${2}${1}${NC}"
}

print_header() {
    echo -e "\n${BLUE}🚀 SISTEMA DE RASTREAMENTO DE ENTREGAS${NC}"
    echo -e "${BLUE}=====================================${NC}\n"
}

# Verifica se Docker está instalado
check_docker() {
    if ! command -v docker &> /dev/null; then
        print_message "❌ Docker não encontrado!" $RED
        print_message "💡 Instale o Docker antes de continuar" $YELLOW
        exit 1
    fi

    if ! command -v docker compose &> /dev/null; then
        print_message "❌ Docker Compose não encontrado!" $RED
        print_message "💡 Instale o Docker Compose antes de continuar" $YELLOW
        exit 1
    fi
}

# Cria diretórios necessários
create_directories() {
    print_message "📁 Criando diretórios necessários..." $BLUE
    mkdir -p mapas logs
    print_message "✅ Diretórios criados" $GREEN
}

# Inicia os serviços
start_services() {
    print_message "🐳 Iniciando serviços Docker..." $BLUE
    
    # Para serviços existentes se houver
    docker compose down 2>/dev/null || true
    
    # Inicia serviços
    docker compose up -d
    
    print_message "✅ Serviços iniciados!" $GREEN
    print_message "📊 MongoDB: localhost:27017" $YELLOW
    print_message "🗄️  Usuário: admin / rastreamento123" $YELLOW
}

# Aguarda MongoDB ficar pronto
wait_for_mongodb() {
    print_message "⏳ Aguardando MongoDB ficar pronto..." $BLUE
    
    # Aguarda até 60 segundos
    for i in {1..60}; do
        if docker compose exec -T mongodb mongosh --eval "db.adminCommand('ping')" >/dev/null 2>&1; then
            print_message "✅ MongoDB está pronto!" $GREEN
            return 0
        fi
        sleep 1
        echo -n "."
    done
    
    print_message "❌ Timeout aguardando MongoDB" $RED
    exit 1
}

# Executa a aplicação
run_application() {
    print_message "🚀 Iniciando aplicação..." $BLUE
    
    # Executa a aplicação no container
    docker compose exec rastreamento python app_simples.py
}

# Função principal
main() {
    print_header
    
    print_message "Este script irá:" $YELLOW
    print_message "1. Verificar Docker" $YELLOW
    print_message "2. Criar diretórios necessários" $YELLOW
    print_message "3. Iniciar serviços (MongoDB + Aplicação)" $YELLOW
    print_message "4. Executar o sistema de rastreamento" $YELLOW
    
    echo
    read -p "Deseja continuar? (S/n): " -n 1 -r
    echo
    
    if [[ $REPLY =~ ^[Nn]$ ]]; then
        print_message "Operação cancelada." $YELLOW
        exit 0
    fi
    
    # Executa etapas
    check_docker
    create_directories
    start_services
    wait_for_mongodb
    run_application
}

# Executa função principal
main "$@"
