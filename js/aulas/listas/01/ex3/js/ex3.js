var nomeCompleto = prompt("Informe o seu nome completo:");
var idade = Number(prompt("Informe a sua idade:"));
var idadeMinima = 18;
var nomeMaiusculo = nomeCompleto.toUpperCase();

if (idade >= idadeMinima) {
    alert(nomeMaiusculo + ", você já POSSUI idade para tirar carteira");
} else {
    var anosFaltantes = idadeMinima - idade;
    alert(
        nomeMaiusculo +
        ", você ainda NÃO POSSUI idade para tirar carteira, ainda falta(m) " +
        anosFaltantes +
        " anos."
    );
}
