const uppercaseLetters = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'];

function generateRandomLetter() {
    const randomIndex = Math.floor(Math.random() * uppercaseLetters.length);
    return uppercaseLetters[randomIndex];
}

function generateRandomLetters(qtd) {
    let results = '';
    for (let i = 0; i < qtd; i++) {
        results += generateRandomLetter();
    }
    return results;
}

console.log(generateRandomLetters(4));