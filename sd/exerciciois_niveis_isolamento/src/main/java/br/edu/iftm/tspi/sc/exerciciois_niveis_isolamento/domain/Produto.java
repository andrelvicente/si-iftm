package br.edu.iftm.tspi.sc.exerciciois_niveis_isolamento.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TB_PRODUTO")
@Data
@NoArgsConstructor
public class Produto implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_produto")
    private Integer id;

    @Column(name = "nom_produto")
    private String nome;

    @Column(name = "vlr_produto")
    private Double valor;

    @Column(name = "qtd_estoque")
    private Integer estoque;
}
