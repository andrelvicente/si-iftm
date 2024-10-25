const lowercaseLetters = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'];

function generateRandomLetter() {
    const randomIndex = Math.floor(Math.random() * lowercaseLetters.length);
    return lowercaseLetters[randomIndex];
}

function generateRandomLetters(qtd) {
    let results = '';
    for (let i = 0; i < qtd; i++) {
        results += generateRandomLetter();
    }
    return results;
}

console.log(generateRandomLetters(4));