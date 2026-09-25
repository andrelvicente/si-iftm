var usuarios = [
    { nome: "andre", senha: "123456" },
    { nome: "maria", senha: "abc123" },
    { nome: "joao", senha: "senha01" }
];

localStorage.setItem("usuarios", JSON.stringify(usuarios));

var mensagem = document.getElementById("mensagem");
mensagem.textContent = usuarios.length + " usuários armazenados no localStorage.";
