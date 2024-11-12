package classes;

public class Conta {
    private int numero;
    private Cliente correntista;
    private float saldo;

    public Conta() {}

    public Conta(int numero, Cliente correntista, float saldo) {
        this.numero = numero;
        this.correntista = correntista;
        this.saldo = saldo;
    }

    public void depositar(float valor) {
        saldo += valor;
    }

    public void depositar(float valor, Especial contaEspecial) {
        if (saldo > 0) {
            float taxa = valor * 0.0005f;
            saldo += (valor - taxa);
        } else {
            saldo += valor;
        }
    }

    public boolean sacar(float valor) throws Restricao {
        float taxa = valor * 0.005f; 
        if (saldo >= (valor + taxa)) {
            saldo -= (valor + taxa);
            return true;
        } else {
            throw new Restricao(saldo, valor);
        }
    }

    public boolean sacar(float valor, Especial contaEspecial) throws Restricao {
        float taxa = valor * contaEspecial.defineTaxacao(saldo);
        if (saldo >= (valor + taxa)) {
            saldo -= (valor + taxa);
            return true;
        } else {
            float valorRestante = valor - saldo;
            saldo -= (valor + taxa);
            
            if (valorRestante <= contaEspecial.getLimite()) {
                float novoLimite = contaEspecial.getLimite() - valorRestante;
                contaEspecial.setLimite(novoLimite);
                System.out.println("Saque realizado usando limite especial. Limite especial restante: " + contaEspecial.getLimite());
                return true;
            } else {
                throw new Restricao(saldo, valor);
            }
        }
    }

    public boolean movimentar(float valor, int operacao) throws Restricao {
        if (operacao == Movimento.DEPOSITAR) {
            depositar(valor);
            return true;
        } else if (operacao == Movimento.SACAR) {
            return sacar(valor);
        }
        return false;
    }

    public boolean movimentar(float valor, int operacao, Especial contaEspecial) throws Restricao {
        if (operacao == Movimento.DEPOSITAR) {
            depositar(valor, contaEspecial);
            return true;
        } else if (operacao == Movimento.SACAR) {
            return sacar(valor, contaEspecial);
        }
        return false;
    }

    public int getNumero() {
        return numero;
    }

    public Cliente getCorrentista() {
        return correntista;
    }

    public float getSaldo() {
        return saldo;
    }

}
