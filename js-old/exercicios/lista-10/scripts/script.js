document.addEventListener("DOMContentLoaded", () => {
    const elements = {
        nivel: document.getElementById("nivel"),
        tempoJogo: document.getElementById("tempoJogo"),
        btnIniciar: document.getElementById("btnIniciar"),
        btnPausar: document.getElementById("btnPausar"),
        btnParar: document.getElementById("btnParar"),
        numeroSorteado: document.getElementById("numeroSorteado"),
        acertos: document.getElementById("acertos"),
        percentualAcertos: document.getElementById("percentualAcertos"),
        erros: document.getElementById("erros"),
        pares: document.getElementById("pares")
    };

    const niveis = {
        facil: { tempo: 105, intervalo: 1000 },
        medio: { tempo: 75, intervalo: 500 },
        dificil: { tempo: 45, intervalo: 300 }
    };

    let intervaloNumero, intervaloTempo;
    let tempoRestante = 0, tempoInicial = 0, tempoNivel = 0;
    let jogoPausado = false, paresSorteados = 0, contagemAcertos = 0, contagemErros = 0;

    elements.nivel.addEventListener("change", () => {
        const nivelSelecionado = niveis[elements.nivel.value];
        if (nivelSelecionado) {
            tempoInicial = nivelSelecionado.tempo;
            tempoNivel = nivelSelecionado.intervalo;
            elements.btnIniciar.disabled = false;
            atualizarTempo(tempoInicial);
        }
    });

    elements.btnIniciar.addEventListener("click", iniciarJogo);
    elements.numeroSorteado.addEventListener("click", verificarNumero);
    elements.btnPausar.addEventListener("click", pausarJogo);
    elements.btnParar.addEventListener("click", pararJogo);

    function iniciarJogo() {
        jogoPausado = false;
        tempoRestante = tempoInicial;
        alternarBotoes(true);
        elements.nivel.disabled = true;
        iniciarIntervalos();
    }

    function iniciarIntervalos() {
        intervaloNumero = setInterval(() => {
            const numero = gerarNumero();
            elements.numeroSorteado.textContent = numero;
            elements.numeroSorteado.dataset.clicado = "false";
            elements.numeroSorteado.style.color = "#e0f7fa";

            if (numero % 2 === 0) {
                paresSorteados++;
                atualizarContador(elements.pares, paresSorteados);
                atualizarPercentualAcertos();
            }
        }, tempoNivel);

        intervaloTempo = setInterval(() => {
            if (--tempoRestante <= 0) finalizarJogo();
            atualizarTempo(tempoRestante);
        }, 1000);
    }

    function verificarNumero() {
        const numero = parseInt(elements.numeroSorteado.textContent);
        if (isNaN(numero) || elements.numeroSorteado.dataset.clicado === "true") return;
        
        elements.numeroSorteado.dataset.clicado = "true";
        if (numero % 2 === 0) {
            contagemAcertos++;
            elements.numeroSorteado.style.color = "green";
            atualizarContador(elements.acertos, contagemAcertos);
        } else {
            contagemErros++;
            elements.numeroSorteado.style.color = "red";
            atualizarContador(elements.erros, contagemErros);
        }
        atualizarPercentualAcertos();
    }

    function pausarJogo() {
        jogoPausado = true;
        clearInterval(intervaloNumero);
        clearInterval(intervaloTempo);
        alternarBotoes(false, true);
    }

    function pararJogo() {
        resetarEstado();
        elements.nivel.disabled = false;
        elements.nivel.value = "selecione";
    }

    function finalizarJogo() {
        clearInterval(intervaloNumero);
        clearInterval(intervaloTempo);
        elements.numeroSorteado.textContent = "-";
        alternarBotoes(false);
        elements.nivel.disabled = false;
    }

    function atualizarTempo(tempo) {
        const minutos = Math.floor(tempo / 60);
        const segundos = tempo % 60;
        elements.tempoJogo.textContent = `${String(minutos).padStart(2, "0")}:${String(segundos).padStart(2, "0")}`;
    }

    function atualizarContador(elemento, valor) {
        elemento.textContent = valor;
    }

    function atualizarPercentualAcertos() {
        const percentual = paresSorteados > 0 ? ((contagemAcertos / paresSorteados) * 100).toFixed(1) : "0.0";
        elements.percentualAcertos.textContent = percentual + "%";
    }

    function alternarBotoes(iniciando, pausado = false) {
        elements.btnIniciar.disabled = iniciando;
        elements.btnPausar.disabled = !iniciando || pausado;
        elements.btnParar.disabled = !iniciando;
    }

    function resetarEstado() {
        clearInterval(intervaloNumero);
        clearInterval(intervaloTempo);
        elements.numeroSorteado.textContent = "-";
        tempoInicial = tempoRestante = 0;
        paresSorteados = contagemAcertos = contagemErros = 0;
        atualizarTempo(0);
        [elements.pares, elements.acertos, elements.erros, elements.percentualAcertos].forEach(el => el.textContent = "0");
        alternarBotoes(false);
    }

    function gerarNumero() {
        return Math.floor(Math.random() * 100) + 1;
    }
});
