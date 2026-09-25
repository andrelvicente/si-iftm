localStorage.setItem("usuario", "andre");

var mensagem = document.getElementById("mensagem");
mensagem.textContent = "Usuário armazenado: " + localStorage.getItem("usuario");
