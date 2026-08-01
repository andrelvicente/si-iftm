const btnEntrar = document.getElementById("entrar");
const btnLimpar = document.getElementById("limpar");
const inputUsuario = document.getElementById("usuario");
const inputSenha = document.getElementById("senha");

function inicializarEventos() {
    btnEntrar.addEventListener("click", handleEntrar);
    btnLimpar.addEventListener("click", limparCampos);
}

function handleEntrar() {
    if (validarCampos()) {
        redirecionarUsuario(inputUsuario.value.trim());
    }
}

function validarCampos() {
    const usuario = inputUsuario.value.trim();
    const senha = inputSenha.value.trim();

    if (!usuario || !senha) {
        exibirAlerta("Por favor, preencha os campos de usuário e senha!");
        return false;
    }
    return true;
}

function exibirAlerta(mensagem) {
    alert(mensagem);
}

function redirecionarUsuario(usuario) {
    const destino = usuario.toUpperCase() === "VISITANTE" ? "./visitante.html" : "./construcao.html";
    window.location.href = destino;
}

function limparCampos() {
    inputUsuario.value = "";
    inputSenha.value = "";
}

inicializarEventos();
