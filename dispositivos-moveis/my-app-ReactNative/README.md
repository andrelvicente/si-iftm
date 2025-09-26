# My App - React Native com Expo e Firebase

Um aplicativo mobile desenvolvido com React Native, Expo e Firebase para autenticação de usuários.

## 📱 Sobre o Projeto

Este é um aplicativo de exemplo que demonstra:

- Sistema de autenticação com Firebase
- Navegação entre telas
- Interface moderna e responsiva
- Integração com serviços de backend

## 🛠️ Tecnologias Utilizadas

- **React Native** - Framework para desenvolvimento mobile
- **Expo** - Plataforma para desenvolvimento React Native
- **Firebase Auth** - Autenticação de usuários
- **React Navigation** - Navegação entre telas
- **JavaScript/ES6+** - Linguagem de programação

## 📂 Estrutura do Projeto

```
my-app/
├── src/
│   ├── navigators/
│   │   └── MainStack.js          # Configuração de navegação
│   ├── screens/
│   │   ├── HomeScreen.js         # Tela inicial
│   │   ├── LoginScreen.js        # Tela de login
│   │   └── RegisterScreen.js     # Tela de cadastro
│   └── services/
│       └── firebase.js           # Configuração do Firebase
├── assets/                       # Imagens e ícones
├── App.js                        # Componente principal
├── app.json                      # Configurações do Expo
└── package.json                  # Dependências do projeto
```

## 🚀 Como Executar

### Pré-requisitos

- Node.js instalado
- Expo Go instalado no celular (Android/iOS)
- Conta no Firebase configurada

### Instalação

1. **Clone o repositório:**

```bash
git clone [url-do-repositorio]
cd my-app
```

2. **Instale as dependências:**

```bash
npm install
```

3. **Configure o Firebase:**

   - Crie um projeto no [Firebase Console](https://console.firebase.google.com/)
   - Ative a autenticação por email/senha
   - Atualize as credenciais em `src/services/firebase.js`

4. **Execute o projeto:**

```bash
npx expo start
```

### Opções de Execução

- **Celular (Recomendado):** Escaneie o QR code com o Expo Go
- **Emulador Android:** Pressione `a` no terminal
- **Navegador:** Pressione `w` no terminal
- **Com túnel:** `npx expo start --tunnel` (para redes corporativas)

## 📱 Funcionalidades

### ✅ Implementadas

- [x] Tela de Login com validação
- [x] Tela de Cadastro de usuários
- [x] Autenticação Firebase
- [x] Navegação entre telas
- [x] Tratamento de erros em português

## 🔧 Configurações

### Firebase

```javascript
const firebaseConfig = {
  apiKey: "sua-api-key",
  authDomain: "seu-projeto.firebaseapp.com",
  projectId: "seu-projeto-id",
  // ...outras configurações
};
```

### Android Package

```json
{
  "expo": {
    "android": {
      "package": "com.anonymous.myapp"
    }
  }
}
```

## 🐛 Troubleshooting

### Problemas Comuns

**1. Erro de Android SDK:**

- Use `npx expo start --tunnel` em vez de configurar o SDK
- Baixe o Expo Go no celular

**2. Request Timeout:**

```bash
npx expo start --tunnel
```

**3. Cache corrompido:**

```bash
npx expo start --clear
```

**4. Dependências desatualizadas:**

```bash
npm install
```

## 📚 Scripts Disponíveis

```bash
# Iniciar desenvolvimento
npm start
# ou
npx expo start

# Limpar cache
npx expo start --clear

# Modo túnel
npx expo start --tunnel

# Abrir no navegador
npx expo start --web
```

---

⭐ **Desenvolvido como atividade acadêmica para aprendizado de React Native** ⭐
