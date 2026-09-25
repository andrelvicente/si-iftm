var usuario = {
    nome: "andre",
    senha: "123456"
};

// localStorage só guarda texto, por isso o objeto é convertido para JSON
localStorage.setItem("usuarioObjeto", JSON.stringify(usuario));

var usuarioSalvo = JSON.parse(localStorage.getItem("usuarioObjeto"));

var mensagem = document.getElementById("mensagem");
mensagem.textContent = "Objeto armazenado -> Usuário: " + usuarioSalvo.nome +
    " | Senha: " + usuarioSalvo.senha;
