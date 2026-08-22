var frase = prompt("Informe uma frase:", "caminhao quebrou na companhia. Caminhao quebrado da prejuizo porque fica caro.");
var letraAntiga = prompt("Informe a letra a ser substituída:");
var letraNova = prompt("Informe a nova letra:");
var palavras = frase.split(" ");

for (var i = 0; i < palavras.length; i++) {
    if (palavras[i].charAt(0).toLowerCase() === letraAntiga.toLowerCase()) {
        palavras[i] = letraNova + palavras[i].substring(1);
    }
}

alert(palavras.join(" "));
