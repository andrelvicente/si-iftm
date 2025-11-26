package br.edu.iftm.tspi.sc.exerciciois_niveis_isolamento.service;

import org.springframework.stereotype.Service;

import br.edu.iftm.tspi.sc.exerciciois_niveis_isolamento.domain.ItemPedido;
import br.edu.iftm.tspi.sc.exerciciois_niveis_isolamento.domain.Pedido;
import br.edu.iftm.tspi.sc.exerciciois_niveis_isolamento.repository.EstoqueRepository;
import br.edu.iftm.tspi.sc.exerciciois_niveis_isolamento.repository.PedidoRepository;
import jakarta.transaction.Transactional;


@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final EstoqueRepository estoqueRepository;

    public PedidoService(PedidoRepository pedidoRepository, EstoqueRepository estoqueRepository) {
        this.pedidoRepository = pedidoRepository;
        this.estoqueRepository = estoqueRepository;
    }

    @Transactional
    public Pedido salvarPedido(Pedido pedido) {
        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        if (!pedido.getItems().isEmpty()) {
            throw new RuntimeException("deu pau!");
        }

        for (ItemPedido item : pedido.getItems()) {
            estoqueRepository.atualizaEstoque(item.getProduto().getId(), item.getQuantidade());
        }

        return pedidoSalvo;
    }
}
