localStorage.setItem("usuario", "andre");
localStorage.setItem("senha", "123456");

var mensagem = document.getElementById("mensagem");
mensagem.textContent = "Usuário: " + localStorage.getItem("usuario") +
    " | Senha: " + localStorage.getItem("senha");
