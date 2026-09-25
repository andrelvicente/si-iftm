var campoNome = document.getElementById("nome");
var campoSenha = document.getElementById("senha");
var btnEntrar = document.getElementById("btnEntrar");
var mensagem = document.getElementById("mensagem");

function carregarUsuarios() {
    var dados = localStorage.getItem("usuarios");
    if (dados == null) {
        return [];
    }
    return JSON.parse(dados);
}

function verificarLogin() {
    var nome = campoNome.value.trim();
    var senha = campoSenha.value.trim();

    if (nome == "" || senha == "") {
        mensagem.textContent = "Informe o usuário e a senha.";
        mensagem.style.color = "red";
        return;
    }

    var usuarios = carregarUsuarios();
    var encontrado = false;

    for (var i = 0; i < usuarios.length; i++) {
        if (usuarios[i].nome == nome && usuarios[i].senha == senha) {
            encontrado = true;
        }
    }

    if (encontrado) {
        mensagem.textContent = "USUÁRIO JÁ EXISTENTE";
        mensagem.style.color = "green";
    } else {
        mensagem.textContent = "USUÁRIO INEXISTENTE";
        mensagem.style.color = "red";
    }
}

btnEntrar.addEventListener("click", verificarLogin);
