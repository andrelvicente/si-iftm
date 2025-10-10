package com.andrelvicente.api.product.shopping_api.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopDTO {
    
    @NotBlank(message = "User identifier is required")
    private String userIdentifier;
    
    @NotNull(message = "Date is required")
    private LocalDateTime date;
    
    @NotNull(message = "Items are required")
    @Valid
    private List<ShopItemDTO> items;
    
    @Positive(message = "Total must be positive")
    private Double total;
}
