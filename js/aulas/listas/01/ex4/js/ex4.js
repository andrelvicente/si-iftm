var nota1 = Number(prompt("Informe a nota do 1º bimestre:"));
var nota2 = Number(prompt("Informe a nota do 2º bimestre:"));
var soma = nota1 + nota2;

if (soma >= 60) {
    alert("Aluno APROVADO. Soma das notas: " + soma);
} else {
    var pontosFaltantes = 60 - soma;
    alert(
        "Aluno REPROVADO. Soma das notas: " +
        soma +
        ". Faltaram " +
        pontosFaltantes +
        " ponto(s) para aprovação."
    );
}
