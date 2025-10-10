package com.andrelvicente.api.product.shopping_api.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopReportDTO {
    
    private LocalDate date;
    private Long totalOrders;
    private Double totalSales;
    private Double averageOrderValue;
}
