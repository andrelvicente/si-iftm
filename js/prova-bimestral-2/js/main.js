const firstName = document.getElementById("name");
const lastName = document.getElementById("lastName");
const ageSelector = document.getElementById("ageSltr");
const register = document.getElementById("registerBtn");
const clear = document.getElementById("clearBtn");
const message = document.getElementById("message");
const mainImage = document.getElementById("mainImg");

function checkInputData(){
    const isValidFullName = firstName.value.trim() && lastName.value.trim();
    const fullName = (firstName.value.trim() + " " + lastName.value.trim()).toUpperCase();
    if(!isValidFullName){
        message.textContent = "Nome e sobrenome devem ser informados.";
        message.style.color = "red";
        mainImage.src = "img/erro.png";
        return;
    }
    
    if(ageSelector.value == "0"){
        message.textContent = "Selecione sua faixa etária.";
        message.style.color = "red";
        mainImage.src = "img/erro.png";
        return;
    }    
    
    if(ageSelector.value == "1" || ageSelector.value == "2"){
        message.textContent = fullName + ", você não tem idade suficiente.";
        message.style.color = "red";
        mainImage.src = "img/erro.png";
        return;
    }

    localStorage.setItem("userB3", 
        JSON.stringify({fullName, ageSelector: ageSelector.value})
    );
    
    message.textContent = "Processando. Aguarde (5 segundos)";
    message.style.color = "blue";
    mainImage.src = "img/processando.gif";
    setTimeout(goToOtherPage, 5000)
}

function goToOtherPage(){
    window.location.href = "processa.html";
}
function clearData() {
    firstName.value = "";
    lastName.value = "";
    ageSelector.value="0"
    message.textContent = "* Favor preencher todos os campos acima";
    message.style.color = "blue";
    mainImage.src = "img/b3.png";
}

register.addEventListener("click", function() {
    checkInputData();
});

clear.addEventListener("click", function() {
    clearData();
});
