let texto = prompt("Digite um texto qualquer:").toLowerCase();

let letra = prompt("Digite uma letra para remover palavras que começam com ela:").toLowerCase();

if (texto && letra && letra.length === 1) {
    let palavras = texto.split(" ");
    let filtrado = palavras.filter(palavra => !palavra.startsWith(letra)).join(" ");
    document.getElementById("textoInicial").innerText = texto;
    document.getElementById("letra").innerText = letra;
    document.getElementById("resultado").innerText = filtrado;
} else {
    document.getElementById("resultado").innerText = "Entrada inválida.";
}
