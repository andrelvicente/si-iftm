package classes;

import java.util.List;

public class Validacao {

    public Validacao() {
    }

    public boolean validarUsuario(String senha, List<String> senhasAutorizadas) {
        return senhasAutorizadas.contains(senha);
    }

    public boolean redefinirSenha(String novaSenha) {
        if (novaSenha.matches("^(?=.*[0-9])(?=.*[!@#$]).{6,}$")) {
            return true;
        }
        return false;
    }
}