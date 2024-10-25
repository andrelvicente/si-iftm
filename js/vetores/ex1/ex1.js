const lowercaseLetters = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'];

function randomLowercaseLetter() {
    const randomIndex = Math.floor(Math.random() * lowercaseLetters.length);
    return lowercaseLetters[randomIndex];
}

console.log(randomLowercaseLetter());