const lowercaseLetters = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'];
const uppercaseLetters = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'];
const numbers = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9'];

function randomCaractere(array) {
    const randomIndex = Math.floor(Math.random() * array.length);
    return array[randomIndex];
}

function generateStrongPassword(size) {
    let password = [];
    
    password.push(randomCaractere(lowercaseLetters));
    password.push(randomCaractere(uppercaseLetters));
    password.push(randomCaractere(numbers));
    
    const allCharacters = [...lowercaseLetters, ...uppercaseLetters, ...numbers];
    for (let i = password.length; i < size; i++) {
        password.push(randomCaractere(allCharacters));
    }
    
    password = password.sort(() => Math.random() - 0.5);
    
    return password.join('');
}

console.log(generateStrongPassword(8));
