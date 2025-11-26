package br.edu.iftm.tspi.sc.exerciciois_niveis_isolamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.iftm.tspi.sc.exerciciois_niveis_isolamento.domain.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido,Integer> {

}
