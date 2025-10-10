package com.andrelvicente.prova01.product.product_api.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    
    private String id;
    
    @NotBlank(message = "Identificador do produto é obrigatório")
    @Size(min = 3, max = 50, message = "Identificador deve ter entre 3 e 50 caracteres")
    private String productIdentifier;
    
    @NotBlank(message = "Nome do produto é obrigatório")
    @Size(min = 2, max = 200, message = "Nome deve ter entre 2 e 200 caracteres")
    private String nome;
    
    @NotBlank(message = "Descrição do produto é obrigatória")
    @Size(min = 10, max = 1000, message = "Descrição deve ter entre 10 e 1000 caracteres")
    private String descricao;
    
    @NotNull(message = "Preço é obrigatório")
    @Positive(message = "Preço deve ser maior que zero")
    private Double preco;
    
    @NotBlank(message = "ID da categoria é obrigatório")
    private String categoryId;
}
