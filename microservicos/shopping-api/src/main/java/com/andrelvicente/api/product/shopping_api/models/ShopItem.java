package com.andrelvicente.api.product.shopping_api.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopItem {
    
    @NotBlank(message = "Product identifier is required")
    @Field("productIdentifier")
    private String productIdentifier;
    
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    @Field("price")
    private Double price;
    
    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    @Field("quantity")
    private Integer quantity;
}
