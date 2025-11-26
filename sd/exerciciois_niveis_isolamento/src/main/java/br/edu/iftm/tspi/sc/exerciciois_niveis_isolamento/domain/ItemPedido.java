package br.edu.iftm.tspi.sc.exerciciois_niveis_isolamento.domain;

import java.io.Serializable;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="TB_ITEM_PEDIDO")
public class ItemPedido implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_item")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "num_pedido")
    @JsonIgnore
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "cod_produto")
    private Produto produto;

    @Column(name = "qtd_compra")
    private Integer quantidade;

}
