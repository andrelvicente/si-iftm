var nomeCompleto = prompt("Informe o seu nome completo:", "INSTITUTO FEDERAL DE SÃO PAULO");
var partes = nomeCompleto.split(" ");
var iniciais = "";

for (var i = 0; i < partes.length; i++) {
    console.log(partes[i].length);
    if (partes[i].length <= 2) {
        continue;
    }
    iniciais += partes[i].charAt(0);
}

alert(iniciais);
