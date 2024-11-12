package classes;

public class Restricao extends Exception {
    private float saldo;
    private float saque;

    public Restricao(float saldo, float saque) {
        super("O saque do valor "+saque+" não é possível. O saldo "+saldo+" é insuficiente");
        this.saldo = saldo;
        this.saque = saque;
    }

    @Override
    public String toString() {
        return "O saque do valor "+saque+" não é possível. O saldo "+saldo+" é insuficiente";
    }
    
}
