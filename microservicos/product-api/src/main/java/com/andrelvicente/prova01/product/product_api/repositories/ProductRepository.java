package com.andrelvicente.prova01.product.product_api.repositories;

import com.andrelvicente.prova01.product.product_api.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    
    Optional<Product> findByProductIdentifier(String productIdentifier);
    
    List<Product> findByCategoryId(String categoryId);
    
    Page<Product> findAll(Pageable pageable);
    
    Page<Product> findByCategoryId(String categoryId, Pageable pageable);
}
