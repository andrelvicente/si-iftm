var texto = prompt("Digite um texto:");
var palavras = texto.split(" ");
var primeira = palavras[0];
var ultima = palavras[palavras.length - 1];

document.write(primeira + " " + ultima);
