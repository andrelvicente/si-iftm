window.addEventListener("DOMContentLoaded", function (){
    var txtNome1 = document.getElementById("txtNome1");
    var txtNome2 = document.getElementById("txtNome2");
    var btnEnviar = document.getElementById("btnEnviar");
    var paragrafo = document.getElementById("paragrafo");

    btnEnviar.addEventListener("click", function () {
        setTimeout(copyTxt, 2000)
    });

    function copyTxt(){
        txtNome2.value = txtNome1.value
        paragrafo.innerHTML = txtNome1.value
    }
});