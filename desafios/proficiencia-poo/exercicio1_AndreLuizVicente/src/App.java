import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import classes.Cliente;
import classes.Conta;
import classes.Especial;
import classes.Movimento;
import classes.Transacao;
import classes.Validacao;

public class App {
    public static void main(String[] args) {
        rodarProjeto();
    }

    private static void rodarProjeto(){
        Scanner scanner = new Scanner(System.in);

        Cliente cliente = new Cliente("Fulano", "123.456.789-00");

        System.out.println("Escolha o tipo de conta (1 - Comum, 2 - Especial):");
        int tipo = scanner.nextInt();
        scanner.nextLine(); 

        Conta conta;
        Transacao transacao = new Transacao();
        if (tipo == 1) {
            conta = new Conta(102030, cliente, 450.0f);

            transacao.realizarTransacao("18/07/2014", conta, "Depósito Inicial", 100.0f, Movimento.DEPOSITAR);
            transacao.realizarTransacao("18/07/2014", conta, "Saque", 50.0f, Movimento.SACAR);
            transacao.realizarTransacao("18/07/2014", conta, "Saque", 120.0f, Movimento.SACAR);
            transacao.realizarTransacao("18/07/2014", conta, "Saque", 850.0f, Movimento.DEPOSITAR);
        } else if (tipo == 2) {
            Especial especial = new Especial(500.0f, 10);
            conta = new Conta(102030, cliente, 450.0f);
            System.out.println("Conta Especial com limite de: " + especial.getLimite());
            transacao.realizarTransacao("18/07/2014", conta, especial, "Depósito Inicial", 100.0f, Movimento.DEPOSITAR);
            transacao.realizarTransacao("18/07/2014", conta, especial, "Saque", 50.0f, Movimento.SACAR);
            transacao.realizarTransacao("18/07/2014", conta, especial, "Saque", 120.0f, Movimento.SACAR);
            transacao.realizarTransacao("18/07/2014", conta, especial, "Saque", 850.0f, Movimento.DEPOSITAR);
        } else {
            conta = new Conta();
            System.out.println("Valor inválido. Finalizando sessão.");
            scanner.close();
        }

        int tentativas = 0;
        boolean relatorioExibido = false;
        Validacao validacao = new Validacao();
        List<String> senhasAutorizadas = new ArrayList<>();
        senhasAutorizadas.add("admin$123");

        while (tentativas < 3 && !relatorioExibido) {
            System.out.println("Digite sua senha para acessar o relatório:");
            String senha = scanner.nextLine();
            if (validacao.validarUsuario(senha, senhasAutorizadas)) {
                System.out.printf("Emitindo Extrato da Conta Comum Número: %d \n", conta.getNumero());
                System.out.printf("Correntista: %s \n", cliente.getNome());
                System.out.printf("Saldo anterior: %.2f \n", transacao.getMovimentos().get(0).getSaldoAnterior());
                exibirRelatorio(transacao.getMovimentos());
                System.out.printf("Saldo atual: %.2f \n", conta.getSaldo());
                relatorioExibido = true;
            } else {
                System.out.println("Senha incorreta!");
                tentativas++;
            }
        }

        if (!relatorioExibido) {
            System.out.println("Deseja redefinir sua senha? (s/n)");
            String resposta = scanner.nextLine();
            if (resposta.equalsIgnoreCase("s")) {
                System.out.println("Digite a nova senha:");
                String novaSenha = scanner.nextLine();
                if (validacao.redefinirSenha(novaSenha)) {
                    senhasAutorizadas.clear();
                    senhasAutorizadas.add(novaSenha);
                    System.out.println("Senha redefinida com sucesso!");
                } else {
                    System.out.println("Senha não atende aos critérios.");
                }
            } else {
                System.out.println("Aplicação finalizada.");
            }
        }

        scanner.close();
    }

    
    private static void exibirRelatorio(List<Movimento> movimentos) {
        for (Movimento movimento : movimentos) {
            System.out.println("------------------------");
            System.out.printf("Data: %s \nHistórico: %s \nValor: %.2f \nOperação: %s%n",
                    movimento.getData(),
                    movimento.getHistorico(),
                    movimento.getValor(),
                    movimento.getOperacao() == Movimento.DEPOSITAR ? "Depósito" : "Saque");
        }
    }
}
