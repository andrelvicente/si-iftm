package br.edu.iftm.tspi.sc.exerciciois_niveis_isolamento.service;

import org.springframework.stereotype.Service;

import br.edu.iftm.tspi.sc.exerciciois_niveis_isolamento.domain.ContaPessimista;
import br.edu.iftm.tspi.sc.exerciciois_niveis_isolamento.repository.ContaPessimistaRepository;
import jakarta.transaction.Transactional;

@Service
public class ContaPessimistaService {

    private final ContaPessimistaRepository repository;

    public ContaPessimistaService(ContaPessimistaRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void deposita(String numero, Double valor) {
        ContaPessimista conta = repository.findByNumeroWithLock(numero);

        if (conta == null) {
            throw new IllegalArgumentException(String.format("Conta não encontrada: %s", numero)); // Adicionei %s para
                                                                                                   // clareza
        }

        try {
            Thread.sleep(10000);
        } catch (Exception e) {
            // TODO: handle exception
        }

        conta.setSaldo(conta.getSaldo() + valor);

        repository.save(conta);
    }

    @Transactional
    public void saque(String numero, Double valor) {
        ContaPessimista conta = repository.findByNumeroWithLock(numero);

        if (conta == null) {
            // Lança exceção se a conta não for encontrada
            throw new IllegalArgumentException("Conta não encontrada");
        }

        if (conta.getSaldo() < valor) {
            // Lança exceção se o saldo for insuficiente
            throw new IllegalArgumentException("Saldo insuficiente");
        }
        try {
            Thread.sleep(10000);
        } catch (Exception e) {
            // TODO: handle exception
        }

        // Realiza a operação de saque
        conta.setSaldo(conta.getSaldo() - valor);

        // Salva as alterações no banco de dados
        repository.save(conta);
    }
}
