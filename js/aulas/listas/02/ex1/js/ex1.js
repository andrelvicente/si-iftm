var primeiroNome = prompt("Informe o seu primeiro nome:");
var sobrenome = prompt("Informe o seu sobrenome:");
var n = Number(prompt("Informe a quantidade de vezes (N):"));
var cor = prompt("Informe a cor desejada para o nome:");
var nomeCompleto = primeiroNome + " " + sobrenome;

for (var i = 1; i <= n; i++) {
    if (i % 2 === 0) {
        document.write("<p style='color: " + cor + ";'>" + i + " - " + nomeCompleto + "</p>");
    } else {
        document.write("<p style='color: black;'>" + i + " - " + nomeCompleto + "</p>");
    }
}
