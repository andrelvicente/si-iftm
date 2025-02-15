const fullName = document.getElementById("name");
const userName = document.getElementById("user");
const password = document.getElementById("password");
const stock = document.getElementById("stock");


function checkUser(){
    const user = localStorage.getItem("userB3");
    if(!user){
        window.location.href = "userNotFound.html";
        return null;
    }
    return JSON.parse(user);
}

function stockByAge(user){
    console.log(user);
    fullName.textContent = user.fullName;
    const nameSplited = user.fullName.split(" ");
    let code = "";
    nameSplited.forEach(element => {
        code = code + element[0];
        return;   
    });
    userName.textContent = "Usuário: " + nameSplited[0];
    password.textContent = "Senha: " + code;
    if(user.ageSelector == "3"){
        stock.textContent = "Renda Fixa"
        return;
    }
    if(user.ageSelector == "4"){
        stock.textContent = "Ações"
        return;
    }
    if(user.ageSelector == "5"){
        stock.textContent = "Fundo de Investimento Imobiliário"
        return;
    }
}

const user = checkUser();
stockByAge(user);
