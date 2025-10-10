package com.andrelvicente.prova01.product.product_api.controller;

import com.andrelvicente.prova01.product.product_api.models.dto.ProductDTO;
import com.andrelvicente.prova01.product.product_api.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    
    private final ProductService productService;
    
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAll() {
        List<ProductDTO> products = productService.getAll();
        return ResponseEntity.ok(products);
    }
    
    @GetMapping("/pageable")
    public ResponseEntity<Page<ProductDTO>> getAllPage(Pageable pageable) {
        Page<ProductDTO> products = productService.getAllPage(pageable);
        return ResponseEntity.ok(products);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable String id) {
        Optional<ProductDTO> product = productService.findById(id);
        return product.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/identifier/{productIdentifier}")
    public ResponseEntity<ProductDTO> findByProductIdentifier(@PathVariable String productIdentifier) {
        Optional<ProductDTO> product = productService.findByProductIdentifier(productIdentifier);
        return product.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductDTO>> getProductByCategoryId(@PathVariable String categoryId) {
        List<ProductDTO> products = productService.getProductByCategoryId(categoryId);
        return ResponseEntity.ok(products);
    }
    
    @PostMapping
    public ResponseEntity<ProductDTO> save(@Valid @RequestBody ProductDTO productDTO) {
        try {
            ProductDTO savedProduct = productService.save(productDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable String id, @Valid @RequestBody ProductDTO productDTO) {
        try {
            Optional<ProductDTO> updatedProduct = productService.update(id, productDTO);
            return updatedProduct.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        boolean deleted = productService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
