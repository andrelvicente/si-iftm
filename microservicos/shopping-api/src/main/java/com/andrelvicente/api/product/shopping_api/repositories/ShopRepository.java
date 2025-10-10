package com.andrelvicente.api.product.shopping_api.repositories;

import com.andrelvicente.api.product.shopping_api.models.Shop;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShopRepository extends MongoRepository<Shop, String> {
    
    /**
     * Busca pedidos por usuário
     */
    List<Shop> findByUserIdentifier(String userIdentifier);
    
    /**
     * Busca pedidos por intervalo de datas
     */
    List<Shop> findByDateBetween(LocalDateTime dataInicio, LocalDateTime dataFim);
    
    /**
     * Busca pedidos que contenham um produto específico
     */
    @Query("{ 'items.productIdentifier': ?0 }")
    List<Shop> findByProductIdentifier(String productIdentifier);
    
    /**
     * Busca pedidos por filtros avançados (data e valor mínimo)
     */
    @Query("{ 'date': { $gte: ?0, $lte: ?1 }, 'total': { $gte: ?2 } }")
    List<Shop> findByDateBetweenAndTotalGreaterThanEqual(
            LocalDateTime dataInicio, 
            LocalDateTime dataFim, 
            Double valorMinimo
    );
    
    /**
     * Busca pedidos por filtros avançados (apenas data, sem valor mínimo)
     */
    @Query("{ 'date': { $gte: ?0, $lte: ?1 } }")
    List<Shop> findByDateBetweenOnly(
            LocalDateTime dataInicio, 
            LocalDateTime dataFim
    );
}
