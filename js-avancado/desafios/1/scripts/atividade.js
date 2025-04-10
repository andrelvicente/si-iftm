window.addEventListener("DOMContentLoaded", function () {
    const tableData = [
        { name: "Ana", age: 18, gender: 'F', salary: 1000 },
        { name: "Joao", age: 20, gender: 'M', salary: 3000 },
        { name: "Cesar", age: 33, gender: 'M', salary: 1900 },
        { name: "Maria", age: 12, gender: 'M', salary: 7000 },
        { name: "Ze", age: 17, gender: 'F', salary: 2400 },
    ];

    console.log("nome e idade de todas as pessoas maiores de idade (18+):");
    tableData
        .filter(person => person.age >= 18)
        .forEach(person => console.log(`Nome: ${person.name}, Idade: ${person.age}`));

    console.log("\nnomes de todas as pessoas do sexo masculino");
    tableData
        .filter(person => person.gender === 'M')
        .forEach(person => console.log(`Nome: ${person.name}`));

    const highestSalaryPerson = tableData.reduce((max, person) => person.salary > max.salary ? person : max);
    console.log("\ndados da pessoa com o maior salário");
    console.log(highestSalaryPerson);

    const womanAbove5000 = tableData.some(person => person.gender === 'F' && person.salary > 5000);
    console.log("\nHá alguma mulher que ganha acima de 5000,00?");
    console.log(womanAbove5000 ? "Sim" : "Não");

    const men = tableData.filter(p => p.gender === 'M');
    const women = tableData.filter(p => p.gender === 'F');

    console.log("\nMédia salarial:");
    console.log(`Homens: R$ ${avgSalary(men).toFixed(2)}`);
    console.log(`Mulheres: R$ ${avgSalary(women).toFixed(2)}`);
});


const avgSalary = arr => arr.reduce((sum, p) => sum + p.salary, 0) / arr.length;
