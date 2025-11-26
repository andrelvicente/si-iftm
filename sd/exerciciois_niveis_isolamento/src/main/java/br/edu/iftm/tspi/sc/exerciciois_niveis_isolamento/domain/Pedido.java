package br.edu.iftm.tspi.sc.exerciciois_niveis_isolamento.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name="TB_PEDIDO")
@Getter
@Setter
@NoArgsConstructor
public class Pedido implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="num_pedido")
    private Integer numero;

    @Column(name="dat_pedido")
    private LocalDate data;

    @OneToMany(fetch=FetchType.EAGER, mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonProperty("itens")
    private List<ItemPedido> items = new ArrayList<>();
    
}
