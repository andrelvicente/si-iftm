function randomNumber() {
    return Math.floor(Math.random() * 10);
}

function generateRandomNumbers(qtd) {
    let results = '';
    for (let i = 0; i < qtd; i++) {
        results += randomNumber();
    }
    return results;
}

console.log(generateRandomNumbers(4));