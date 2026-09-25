var usuarios = [
    { nome: "andre", senha: "123456" },
    { nome: "maria", senha: "abc123" },
    { nome: "joao", senha: "senha01" }
];

localStorage.setItem("usuarios", JSON.stringify(usuarios));

var usuariosSalvos = JSON.parse(localStorage.getItem("usuarios"));
var lista = document.getElementById("listaUsuarios");

for (var i = 0; i < usuariosSalvos.length; i++) {
    var item = document.createElement("li");
    item.textContent = "Usuário: " + usuariosSalvos[i].nome +
        " | Senha: " + usuariosSalvos[i].senha;
    lista.appendChild(item);
}
