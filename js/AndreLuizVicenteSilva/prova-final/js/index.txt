function validarFormulario() {
    const nome = document.getElementById("nome").value.trim();
    const sobrenome = document.getElementById("sobrenome").value.trim();
    const senha = document.getElementById("senha").value;
    const sexo = document.getElementById("sexo").value;
    const nomeCompleto = nome + " " + sobrenome;
    if (nome === "" || sobrenome === "") {
        alert("Favor informar o NOME e SOBRENOME");
        return false;
    }

    const senhaRegex = /^(REC|RECUPERAÇÃO)-Jsbásico:\(1\)23[2-5]{2}$/;
    if (!senhaRegex.test(senha)) {
        alert("Senha inválida. Siga o padrão.");
        return false;
    }

    if (sexo === "masculino") {
        window.location.href = "masculino.html";
    } else if (sexo === "feminino") {
        window.location.href = "feminino.html";
    }

    localStorage.setItem("nomeCompleto", nomeCompleto);

    return false;
}
