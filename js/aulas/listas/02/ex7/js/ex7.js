var texto = prompt("Digite um texto:");
var letra = prompt("Digite uma letra:");
var palavras = texto.split(" ");
var resultado = "";

for (var i = 0; i < palavras.length; i++) {
    if (palavras[i].charAt(0).toLowerCase() === letra.toLowerCase()) {
        if (resultado !== "") {
            resultado += " ";
        }
        resultado += palavras[i];
    }
}

document.write(resultado);
