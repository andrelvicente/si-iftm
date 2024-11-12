package classes;

import java.util.ArrayList;
import java.util.List;

public class Transacao {
    private List<Movimento> movimentos;

    public Transacao() {
        movimentos = new ArrayList<>();
    }

    public boolean realizarTransacao(String data, Conta conta, String historico, float valor, int operacao) {
        Movimento movimento = new Movimento(data, conta, historico, valor, operacao);
        if (movimento.movimentar()) {
            movimentos.add(movimento);
            return true;
        }
        return false;
    }

    public boolean realizarTransacao(String data, Conta conta, Especial especial, String historico, float valor, int operacao) {
        Movimento movimento = new Movimento(data, conta, historico, valor, operacao);
        if (movimento.movimentar(especial)) {
            movimentos.add(movimento);
            return true;
        }
        return false;
    }
    
    public void estornarTransacao() {
        movimentos.clear();
    }

    public List<Movimento> getMovimentos() {
        return movimentos;
    }
}
