package classes;

public class Movimento {
    public static final int SACAR = 0;
    public static final int DEPOSITAR = 1;

    private String data;
    private Conta conta;
    private String historico;
    private float valor;
    private int operacao;
    private float saldoAnterior;

    public Movimento(String data, Conta conta, String historico, float valor, int operacao) {
        this.data = data;
        this.conta = conta;
        this.historico = historico;
        this.valor = valor;
        this.operacao = operacao;
    }

    public boolean movimentar() {
        saldoAnterior = conta.getSaldo();
        try {
            if (operacao == SACAR) {
                return conta.movimentar(valor, SACAR);
            } else if (operacao == DEPOSITAR) {
                return conta.movimentar(valor, DEPOSITAR);
            }
        } catch (Restricao e) {
            System.out.println("Erro: " + e.getMessage());
        }
        return false;
    }

    public boolean movimentar(Especial contaEspecial) {
        saldoAnterior = conta.getSaldo();
        try {
            if (operacao == SACAR) {
                return conta.movimentar(valor, SACAR, contaEspecial);
            } else if (operacao == DEPOSITAR) {
                return conta.movimentar(valor, DEPOSITAR, contaEspecial);
            }
        } catch (Restricao e) {
            System.out.println("Erro: " + e.getMessage());
        }
        return false;
    }

    public String getData() {
        return data;
    }

    public String getHistorico() {
        return historico;
    }

    public float getValor() {
        return valor;
    }

    public int getOperacao() {
        return operacao;
    }

    public float getSaldoAnterior() {
        return saldoAnterior;
    }

}