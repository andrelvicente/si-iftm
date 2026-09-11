var campoPrimeiroNome = document.getElementById("primeiroNome");
var campoRestanteNome = document.getElementById("restanteNome");
var campoCargo = document.getElementById("cargo");
var modeloCracha = document.getElementById("modeloCracha");
var conteudoCracha = document.getElementById("conteudoCracha");

document.getElementById("btnHtml").addEventListener("click", function () {
    gerarCracha("./img/logoHTML.webp");
});

document.getElementById("btnCss").addEventListener("click", function () {
    gerarCracha("./img/logoCSS.webp");
});

document.getElementById("btnJs").addEventListener("click", function () {
    gerarCracha("./img/logoJS.webp");
});

function gerarCracha(caminhoLogo) {
    var primeiroNome = campoPrimeiroNome.value.trim();
    var restanteNome = campoRestanteNome.value.trim();
    var cargo = campoCargo.value.trim();
    var nomeCompleto = (primeiroNome + " " + restanteNome).trim();
    var sala = Math.floor(Math.random() * 10) + 1;
    var cargoMinusculo = cargo.toLowerCase();
    var html = "";

    html += "<p id='nomeCracha'>" + nomeCompleto.toUpperCase() + "</p>";
    html += "<p id='iniciaisCracha'>" + obterIniciais(nomeCompleto) + "</p>";
    html += "<img id='logoCracha' src='" + caminhoLogo + "' alt='Logo do minicurso'>";

    if (cargoMinusculo === "professor") {
        html += "<p id='cargoCracha' style='color: green;'>Professor</p>";
    } else if (cargoMinusculo === "desenvolvedor") {
        html += "<p id='cargoCracha' style='color: red;'>Desenvolvedor</p>";
    }

    html += "<p id='salaCracha'>Sala " + sala + "</p>";
    modeloCracha.style.display = "none";
    conteudoCracha.innerHTML = html;
}

function obterIniciais(nomeCompleto) {
    var partes = nomeCompleto.split(" ");
    var iniciais = "";

    for (var i = 0; i < partes.length; i++) {
        if (partes[i] !== "") {
            iniciais += partes[i].charAt(0).toUpperCase();
        }
    }

    return iniciais;
}
