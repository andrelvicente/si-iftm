package com.andrelvicente.prova01.product.product_api.services;

import com.andrelvicente.prova01.product.product_api.models.Category;
import com.andrelvicente.prova01.product.product_api.models.dto.CategoryDTO;
import com.andrelvicente.prova01.product.product_api.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    
    private final CategoryRepository categoryRepository;
    
    public List<CategoryDTO> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public Page<CategoryDTO> getAllPage(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .map(this::convertToDTO);
    }
    
    public Optional<CategoryDTO> findById(String id) {
        return categoryRepository.findById(id)
                .map(this::convertToDTO);
    }
    
    public CategoryDTO save(CategoryDTO categoryDTO) {
        Category category = convertToEntity(categoryDTO);
        Category savedCategory = categoryRepository.save(category);
        return convertToDTO(savedCategory);
    }
    
    public Optional<CategoryDTO> update(String id, CategoryDTO categoryDTO) {
        return categoryRepository.findById(id)
                .map(existingCategory -> {
                    existingCategory.setNome(categoryDTO.getNome());
                    Category updatedCategory = categoryRepository.save(existingCategory);
                    return convertToDTO(updatedCategory);
                });
    }
    
    public boolean delete(String id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    public Optional<Category> findCategoryById(String id) {
        return categoryRepository.findById(id);
    }
    
    private CategoryDTO convertToDTO(Category category) {
        return new CategoryDTO(category.getId(), category.getNome());
    }
    
    private Category convertToEntity(CategoryDTO categoryDTO) {
        return new Category(categoryDTO.getId(), categoryDTO.getNome());
    }
}
