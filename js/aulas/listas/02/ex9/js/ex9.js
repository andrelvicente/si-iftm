var texto = prompt("Digite um texto:");
var normalizado = texto.toLowerCase().split(" ").join("");
var invertido = "";

for (var i = normalizado.length - 1; i >= 0; i--) {
    invertido += normalizado.charAt(i);
}

if (normalizado === invertido) {
    alert("O texto informado é um palíndromo.");
} else {
    alert("O texto informado NÃO é um palíndromo.");
}
