var r = Number(prompt("Informe o valor de R (intervalo [0, 255]):"));
var g = Number(prompt("Informe o valor de G (intervalo [0, 255]):"));
var b = Number(prompt("Informe o valor de B (intervalo [0, 255]):"));

document.write(
    "<p style='color: rgb(" + r + ", " + g + ", " + b + ");'>" +
    "Fundamentos de Web Design II" +
    "</p>"
);
