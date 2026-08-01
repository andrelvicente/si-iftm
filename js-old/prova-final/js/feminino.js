document.addEventListener("DOMContentLoaded", function () {
    const nomeCompleto = localStorage.getItem("nomeCompleto") || undefined;

    if(!nomeCompleto){
        alert("Nome completo não encontrado")
        return
    }
    
    document.getElementById("nome").value = nomeCompleto;
    document.getElementById("nomeExibido").textContent = nomeCompleto;

    const palavras = nomeCompleto.trim().split(/\s+/);
    
    document.getElementById("contagemCaracteres").textContent = nomeCompleto.length;
    document.getElementById("primeiraPalavra").textContent = palavras[0] || "N/A";
    document.getElementById("ultimaPalavra").textContent = palavras[palavras.length - 1] || "N/A";
    document.getElementById("contagemPalavras").textContent = palavras.length;
});
