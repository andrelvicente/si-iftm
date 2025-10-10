package com.andrelvicente.prova01.product.product_api.services;

import com.andrelvicente.prova01.product.product_api.models.Category;
import com.andrelvicente.prova01.product.product_api.models.Product;
import com.andrelvicente.prova01.product.product_api.models.dto.ProductDTO;
import com.andrelvicente.prova01.product.product_api.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    
    public List<ProductDTO> getAll() {
        return productRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public Page<ProductDTO> getAllPage(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(this::convertToDTO);
    }
    
    public Optional<ProductDTO> findById(String id) {
        return productRepository.findById(id)
                .map(this::convertToDTO);
    }
    
    public Optional<ProductDTO> findByProductIdentifier(String productIdentifier) {
        return productRepository.findByProductIdentifier(productIdentifier)
                .map(this::convertToDTO);
    }
    
    public List<ProductDTO> getProductByCategoryId(String categoryId) {
        return productRepository.findByCategoryId(categoryId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public ProductDTO save(ProductDTO productDTO) {
        // Verificar se a categoria existe
        Optional<Category> category = categoryService.findCategoryById(productDTO.getCategoryId());
        if (category.isEmpty()) {
            throw new IllegalArgumentException("Categoria não encontrada com ID: " + productDTO.getCategoryId());
        }
        
        // Verificar se o productIdentifier já existe
        if (productRepository.findByProductIdentifier(productDTO.getProductIdentifier()).isPresent()) {
            throw new IllegalArgumentException("Produto com identificador '" + productDTO.getProductIdentifier() + "' já existe");
        }
        
        Product product = convertToEntity(productDTO, category.get());
        Product savedProduct = productRepository.save(product);
        return convertToDTO(savedProduct);
    }
    
    public Optional<ProductDTO> update(String id, ProductDTO productDTO) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    // Verificar se a categoria existe
                    Optional<Category> category = categoryService.findCategoryById(productDTO.getCategoryId());
                    if (category.isEmpty()) {
                        throw new IllegalArgumentException("Categoria não encontrada com ID: " + productDTO.getCategoryId());
                    }
                    
                    // Verificar se o productIdentifier já existe em outro produto
                    Optional<Product> productWithSameIdentifier = productRepository.findByProductIdentifier(productDTO.getProductIdentifier());
                    if (productWithSameIdentifier.isPresent() && !productWithSameIdentifier.get().getId().equals(id)) {
                        throw new IllegalArgumentException("Produto com identificador '" + productDTO.getProductIdentifier() + "' já existe");
                    }
                    
                    existingProduct.setProductIdentifier(productDTO.getProductIdentifier());
                    existingProduct.setNome(productDTO.getNome());
                    existingProduct.setDescricao(productDTO.getDescricao());
                    existingProduct.setPreco(productDTO.getPreco());
                    existingProduct.setCategory(category.get());
                    
                    Product updatedProduct = productRepository.save(existingProduct);
                    return convertToDTO(updatedProduct);
                });
    }
    
    public boolean delete(String id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    private ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setProductIdentifier(product.getProductIdentifier());
        dto.setNome(product.getNome());
        dto.setDescricao(product.getDescricao());
        dto.setPreco(product.getPreco());
        dto.setCategoryId(product.getCategory().getId());
        return dto;
    }
    
    private Product convertToEntity(ProductDTO productDTO, Category category) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setProductIdentifier(productDTO.getProductIdentifier());
        product.setNome(productDTO.getNome());
        product.setDescricao(productDTO.getDescricao());
        product.setPreco(productDTO.getPreco());
        product.setCategory(category);
        return product;
    }
}
