let contadorMario = 0;
let contadorLuigi = 0;
let personagemAtual = 'img/mario.gif';

function trocarImagem(novaImagem) {
    document.getElementById('personagem').src = novaImagem;
    personagemAtual = novaImagem;
}

function incrementarContador() {
    if (personagemAtual === 'img/mario.gif') {
        contadorMario++;
        document.getElementById('contadorMario').textContent = "Contador do Mario: " + contadorMario;
    } else if (personagemAtual === 'img/luigi.gif') {
        contadorLuigi++;
        document.getElementById('contadorLuigi').textContent = "Contador do Luigi: " + contadorLuigi;
    }
}
