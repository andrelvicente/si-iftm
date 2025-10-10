package com.andrelvicente.prova01.product.product_api.repositories;

import com.andrelvicente.prova01.product.product_api.models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {
    
    Optional<Category> findByNome(String nome);
    
    Page<Category> findAll(Pageable pageable);
}
