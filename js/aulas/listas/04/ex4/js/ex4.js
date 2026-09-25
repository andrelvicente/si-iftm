var campoNome = document.getElementById("nome");
var campoSenha = document.getElementById("senha");
var btnSalvar = document.getElementById("btnSalvar");
var mensagem = document.getElementById("mensagem");

function salvarUsuario() {
    var nome = campoNome.value.trim();
    var senha = campoSenha.value.trim();

    if (nome == "" || senha == "") {
        mensagem.textContent = "Informe o usuário e a senha.";
        mensagem.style.color = "red";
        return;
    }

    var usuario = {
        nome: nome,
        senha: senha
    };

    localStorage.setItem("usuarioObjeto", JSON.stringify(usuario));

    mensagem.textContent = "Usuário " + usuario.nome + " armazenado com sucesso!";
    mensagem.style.color = "green";
    campoNome.value = "";
    campoSenha.value = "";
}

btnSalvar.addEventListener("click", salvarUsuario);
