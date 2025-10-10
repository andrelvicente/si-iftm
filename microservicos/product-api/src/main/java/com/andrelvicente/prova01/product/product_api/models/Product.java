package com.andrelvicente.prova01.product.product_api.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "products")
public class Product {
    
    @Id
    private String id;
    
    private String productIdentifier;
    
    private String nome;
    
    private String descricao;
    
    private Double preco;
    
    @DBRef
    private Category category;
}
