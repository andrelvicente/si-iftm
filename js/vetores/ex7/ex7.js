const lowercaseLetters = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'];

function randomLowercaseLetter() {
    const randomIndex = Math.floor(Math.random() * lowercaseLetters.length);
    return lowercaseLetters[randomIndex];
}

function randomNumber() {
    return Math.floor(Math.random() * 10);
}

function generateRandomLettersAndNumbers() {
    let results = '';

    for (let i = 0; i < 4; i++) {
        results += randomLowercaseLetter();
    }

    for (let i = 0; i < 4; i++) {
        results += randomNumber();
    }

    return results;
}

console.log(generateRandomLettersAndNumbers());