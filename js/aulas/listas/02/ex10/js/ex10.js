var numero = Number(prompt("Informe um valor inteiro positivo maior ou igual a 2:"));

document.write("<table border='1'>");
document.write("<tr>");
document.write("<th>Base decimal</th>");
document.write("<th>Base binária</th>");
document.write("<th>Base hexadecimal</th>");
document.write("</tr>");

for (var i = 0; i <= numero; i++) {
    document.write("<tr>");
    document.write("<td>" + i + "</td>");
    document.write("<td>" + i.toString(2) + "</td>");
    document.write("<td>" + i.toString(16) + "</td>");
    document.write("</tr>");
}

document.write("</table>");
