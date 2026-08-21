var texto = prompt("Informe um texto:");
var palavra1 = prompt("Informe a primeira palavra:");
var palavra2 = prompt("Informe a segunda palavra:");
var resultado = texto.split(palavra1).join(palavra2);

alert(resultado);
