const lowercaseLetters = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'];

function randomUppercaseLetter() {
    const randomIndex = Math.floor(Math.random() * lowercaseLetters.length);
    return lowercaseLetters[randomIndex];
}

console.log(randomUppercaseLetter());