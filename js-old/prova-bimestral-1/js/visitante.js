const inputUsuario = document.getElementById("usuario");
const output = document.getElementById("output");
const btnCliqueMe = document.getElementById("btnCliqueMe");
const btnApenasColoqueMouse = document.getElementById("btnApenasColoqueMouse");
const btnColoqueETireMouse = document.getElementById("btnColoqueETireMouse");
const btnImagemAleatoria = document.getElementById("btnImagemAleatoria");
const imagem = document.getElementById("imagem");

const imagens = [
    "./img/emoji1.jpg",
    "./img/emoji2.jpg",
    "./img/emoji3.jpg",
    "./img/emoji4.jpg"
];

function exibirNomeUsuario() {
    const nome = inputUsuario.value.trim();
    if (nome) {
        output.textContent = `Olá, ${nome}!`;
    }
}

btnCliqueMe.addEventListener("click", function() {
    exibirNomeUsuario();
    imagem.src = "./img/emoji1.jpg";
    imagem.style.display = "block";
});

btnApenasColoqueMouse.addEventListener("mouseover", function() {
    exibirNomeUsuario();
    imagem.src = "./img/emoji2.jpg";
    imagem.style.display = "block";
});


btnColoqueETireMouse.addEventListener("mouseleave", function() {
    imagem.src = "./img/emoji3.jpg";
    imagem.style.display = "block";
    exibirNomeUsuario();
});

btnImagemAleatoria.addEventListener("click", function() {
    const imagemAleatoria = imagens[Math.floor(Math.random() * imagens.length)];
    imagem.src = imagemAleatoria;
    imagem.style.display = "block";
});