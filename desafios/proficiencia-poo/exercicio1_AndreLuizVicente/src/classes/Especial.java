package classes;

public class Especial {
    private float limite;
    private int tempo;

    public Especial() {}

    public Especial(float limite, int tempo) {
        this.limite = limite;
        this.tempo = tempo;
    }

    public float defineTaxacao(float saldo) {
        if (tempo < 12) {
            return 0.002f; 
        } else if (tempo <= 23) {
            return saldo <= 0 ? 0.002f : 0.0015f;
        } else {
            return saldo <= 0 ? 0.0015f : 0.001f;
        }
    }

    public float getLimite() {
        return limite;
    }

    public void setLimite(float limite) {
        this.limite = limite;
    }

    public int getTempo() {
        return tempo;
    }
}
