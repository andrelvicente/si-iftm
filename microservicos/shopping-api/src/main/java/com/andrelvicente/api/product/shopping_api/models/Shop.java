package com.andrelvicente.api.product.shopping_api.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.index.Indexed;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "shop")
public class Shop {
    
    @Id
    private String id;
    
    @NotBlank(message = "User identifier is required")
    @Indexed
    @Field("userIdentifier")
    private String userIdentifier;
    
    @NotNull(message = "Date is required")
    @Indexed
    @Field("date")
    private LocalDateTime date;
    
    @NotNull(message = "Items are required")
    @Valid
    @Field("items")
    private List<ShopItem> items;
    
    @Positive(message = "Total must be positive")
    @Field("total")
    private Double total;
}
