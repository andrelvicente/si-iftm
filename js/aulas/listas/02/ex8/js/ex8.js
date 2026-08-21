var texto = prompt("Digite um texto:");
var palavras = texto.split(" ");
var letras = "abcdefghijklmnopqrstuvwxyz";

for (var i = 0; i < letras.length; i++) {
    var letra = letras.charAt(i);
    var lista = "";

    for (var j = 0; j < palavras.length; j++) {
        if (palavras[j].charAt(0).toLowerCase() === letra) {
            lista += "<li>" + palavras[j] + "</li>";
        }
    }

    if (lista !== "") {
        document.write("Palavras iniciadas com a letra " + letra + ":");
        document.write("<ul>" + lista + "</ul>");
    }
}
