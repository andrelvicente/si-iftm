var campoNome = document.getElementById("nome");
var campoSenha = document.getElementById("senha");
var btnCadastrar = document.getElementById("btnCadastrar");
var mensagem = document.getElementById("mensagem");
var lista = document.getElementById("listaUsuarios");

function carregarUsuarios() {
    var dados = localStorage.getItem("usuarios");
    if (dados == null) {
        return [];
    }
    return JSON.parse(dados);
}

function listarUsuarios() {
    var usuarios = carregarUsuarios();
    lista.innerHTML = "";

    for (var i = 0; i < usuarios.length; i++) {
        var item = document.createElement("li");
        item.textContent = "Usuário: " + usuarios[i].nome +
            " | Senha: " + usuarios[i].senha;
        lista.appendChild(item);
    }
}

function cadastrarUsuario() {
    var nome = campoNome.value.trim();
    var senha = campoSenha.value.trim();

    if (nome == "" || senha == "") {
        mensagem.textContent = "Informe o usuário e a senha.";
        mensagem.style.color = "red";
        return;
    }

    var usuarios = carregarUsuarios();
    usuarios.push({ nome: nome, senha: senha });
    localStorage.setItem("usuarios", JSON.stringify(usuarios));

    mensagem.textContent = "Usuário " + nome + " cadastrado com sucesso!";
    mensagem.style.color = "green";
    campoNome.value = "";
    campoSenha.value = "";
    listarUsuarios();
}

btnCadastrar.addEventListener("click", cadastrarUsuario);

listarUsuarios();
