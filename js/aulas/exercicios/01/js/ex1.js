var nomeCompleto = prompt("Informe o seu nome completo:");
var partes = nomeCompleto.split(" ");
var iniciais = "";

for (var i = 0; i < partes.length; i++) {
    var parte = partes[i];
    var parteMinuscula = parte.toLowerCase();

    if (parte.length < 2) {
        continue;
    }

    iniciais += parte.charAt(0);
}

alert(iniciais);
