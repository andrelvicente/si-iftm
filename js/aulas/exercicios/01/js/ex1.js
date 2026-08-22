var nomeCompleto = prompt("Informe o seu nome completo:");
var partes = nomeCompleto.split(" ");
var iniciais = "";

for (var i = 0; i < partes.length; i++) {
    if (partes[i].length < 2) {
        continue;
    }
    iniciais += partes[i].charAt(0);
}

alert(iniciais);
