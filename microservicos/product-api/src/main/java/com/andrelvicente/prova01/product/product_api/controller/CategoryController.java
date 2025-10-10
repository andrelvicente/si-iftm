package com.andrelvicente.prova01.product.product_api.controller;

import com.andrelvicente.prova01.product.product_api.models.dto.CategoryDTO;
import com.andrelvicente.prova01.product.product_api.services.CategoryService;
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
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    
    private final CategoryService categoryService;
    
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAll() {
        List<CategoryDTO> categories = categoryService.getAll();
        return ResponseEntity.ok(categories);
    }
    
    @GetMapping("/pageable")
    public ResponseEntity<Page<CategoryDTO>> getAllPage(Pageable pageable) {
        Page<CategoryDTO> categories = categoryService.getAllPage(pageable);
        return ResponseEntity.ok(categories);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable String id) {
        Optional<CategoryDTO> category = categoryService.findById(id);
        return category.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<CategoryDTO> save(@Valid @RequestBody CategoryDTO categoryDTO) {
        try {
            CategoryDTO savedCategory = categoryService.save(categoryDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> update(@PathVariable String id, @Valid @RequestBody CategoryDTO categoryDTO) {
        Optional<CategoryDTO> updatedCategory = categoryService.update(id, categoryDTO);
        return updatedCategory.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        boolean deleted = categoryService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
