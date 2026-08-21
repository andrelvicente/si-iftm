var nomeCompleto = prompt("Informe o seu nome completo:");
var partes = nomeCompleto.split(" ");
var primeiroNome = partes[0].toUpperCase();
var restante = "";

for (var i = 1; i < partes.length; i++) {
    if (i > 1) {
        restante += " ";
    }
    restante += partes[i].toLowerCase();
}

if (restante !== "") {
    alert(primeiroNome + " " + restante);
} else {
    alert(primeiroNome);
}
