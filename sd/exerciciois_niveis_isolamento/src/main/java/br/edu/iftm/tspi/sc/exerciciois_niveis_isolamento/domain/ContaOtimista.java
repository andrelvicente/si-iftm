package br.edu.iftm.tspi.sc.exerciciois_niveis_isolamento.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_conta_otimista")
@Data
@NoArgsConstructor
public class ContaOtimista implements Serializable {
    @Id
    @Column(name = "num_conta")
    private String numero;

    @Column(name = "vlr_saldo")
    private Double saldo;

    @Version
    @Column(name = "cod_versao")
    private Integer versao;
}
