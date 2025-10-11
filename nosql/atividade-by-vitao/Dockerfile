# Dockerfile para o Sistema de Rastreamento de Entregas
FROM python:3.11-slim

# Define diretório de trabalho
WORKDIR /app

# Instala dependências do sistema
RUN apt-get update && apt-get install -y \
    gcc \
    && rm -rf /var/lib/apt/lists/*

# Copia arquivos de dependências
COPY requirements.txt .

# Instala dependências Python
RUN pip install --no-cache-dir --upgrade pip && \
    pip install --no-cache-dir pymongo==4.6.0 folium==0.15.0 python-dotenv==1.0.0

# Copia código da aplicação
COPY . .

# Cria usuário não-root para segurança
RUN useradd --create-home --shell /bin/bash app && \
    chown -R app:app /app && \
    chown -R app:app /home/andre-luiz/iftm/si-iftm/nosql/atividade-by-vitao
USER app

# Expõe porta (opcional, para futuras extensões web)
EXPOSE 8000

# Comando padrão
CMD ["python", "app_simples.py"]
