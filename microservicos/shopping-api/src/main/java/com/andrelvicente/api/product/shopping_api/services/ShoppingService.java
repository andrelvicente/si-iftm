package com.andrelvicente.api.product.shopping_api.services;

import com.andrelvicente.api.product.shopping_api.models.Shop;
import com.andrelvicente.api.product.shopping_api.models.ShopItem;
import com.andrelvicente.api.product.shopping_api.models.dto.ShopDTO;
import com.andrelvicente.api.product.shopping_api.models.dto.ShopItemDTO;
import com.andrelvicente.api.product.shopping_api.models.dto.ShopReportDTO;
import com.andrelvicente.api.product.shopping_api.repositories.ShopRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShoppingService {
    
    private final ShopRepository shopRepository;
    
    /**
     * Retorna todos os pedidos
     */
    public List<Shop> getAll() {
        log.info("Buscando todos os pedidos");
        return shopRepository.findAll();
    }
    
    /**
     * Busca pedido por ID
     */
    public Optional<Shop> findById(String id) {
        log.info("Buscando pedido por ID: {}", id);
        return shopRepository.findById(id);
    }
    
    /**
     * Salva um novo pedido
     */
    public Shop save(ShopDTO shopDTO) {
        log.info("Salvando novo pedido para usuário: {}", shopDTO.getUserIdentifier());
        
        Shop shop = convertToEntity(shopDTO);
        
        // Calcular total se não fornecido
        if (shop.getTotal() == null) {
            shop.setTotal(calculateTotal(shop.getItems()));
        }
        
        return shopRepository.save(shop);
    }
    
    /**
     * Busca pedidos por usuário
     */
    public List<Shop> getByUser(String userId) {
        log.info("Buscando pedidos por usuário: {}", userId);
        return shopRepository.findByUserIdentifier(userId);
    }
    
    /**
     * Busca pedidos por data
     */
    public List<Shop> getByDate(String dataInicio, String dataFim) {
        log.info("Buscando pedidos por data - Início: {}, Fim: {}", dataInicio, dataFim);
        
        LocalDateTime inicio = parseDateTime(dataInicio);
        LocalDateTime fim = parseDateTime(dataFim);
        
        return shopRepository.findByDateBetween(inicio, fim);
    }
    
    /**
     * Busca pedidos por identificador de produto
     */
    public List<Shop> findByProductIdentifier(String productIdentifier) {
        log.info("Buscando pedidos por produto: {}", productIdentifier);
        return shopRepository.findByProductIdentifier(productIdentifier);
    }
    
    /**
     * Busca pedidos com filtros avançados
     */
    public List<Shop> getShopsByFilter(String dataInicio, String dataFim, Double valorMinimo) {
        log.info("Buscando pedidos com filtros - Início: {}, Fim: {}, Valor Mínimo: {}", 
                dataInicio, dataFim, valorMinimo);
        
        LocalDateTime inicio = parseDateTime(dataInicio);
        LocalDateTime fim = parseDateTime(dataFim);
        
        if (valorMinimo != null && valorMinimo > 0) {
            return shopRepository.findByDateBetweenAndTotalGreaterThanEqual(inicio, fim, valorMinimo);
        } else {
            return shopRepository.findByDateBetweenOnly(inicio, fim);
        }
    }
    
    /**
     * Gera relatório por data
     */
    public List<ShopReportDTO> getReportByDate(String dataInicio, String dataFim) {
        log.info("Gerando relatório por data - Início: {}, Fim: {}", dataInicio, dataFim);
        
        LocalDateTime inicio = parseDateTime(dataInicio);
        LocalDateTime fim = parseDateTime(dataFim);
        
        List<Shop> shops = shopRepository.findByDateBetween(inicio, fim);
        
        return shops.stream()
                .collect(Collectors.groupingBy(
                        shop -> shop.getDate().toLocalDate(),
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                shopList -> {
                                    long totalOrders = shopList.size();
                                    double totalSales = shopList.stream()
                                            .mapToDouble(shop -> shop.getTotal() != null ? shop.getTotal() : 0.0)
                                            .sum();
                                    double averageOrderValue = totalOrders > 0 ? totalSales / totalOrders : 0.0;
                                    
                                    return new ShopReportDTO(
                                            shopList.get(0).getDate().toLocalDate(),
                                            totalOrders,
                                            totalSales,
                                            averageOrderValue
                                    );
                                }
                        )
                ))
                .values()
                .stream()
                .sorted((r1, r2) -> r1.getDate().compareTo(r2.getDate()))
                .collect(Collectors.toList());
    }
    
    /**
     * Converte DTO para entidade
     */
    private Shop convertToEntity(ShopDTO shopDTO) {
        Shop shop = new Shop();
        shop.setUserIdentifier(shopDTO.getUserIdentifier());
        shop.setDate(shopDTO.getDate());
        shop.setTotal(shopDTO.getTotal());
        
        List<ShopItem> items = shopDTO.getItems().stream()
                .map(this::convertItemToEntity)
                .collect(Collectors.toList());
        
        shop.setItems(items);
        return shop;
    }
    
    /**
     * Converte ShopItemDTO para ShopItem
     */
    private ShopItem convertItemToEntity(ShopItemDTO itemDTO) {
        ShopItem item = new ShopItem();
        item.setProductIdentifier(itemDTO.getProductIdentifier());
        item.setPrice(itemDTO.getPrice());
        item.setQuantity(itemDTO.getQuantity());
        return item;
    }
    
    /**
     * Calcula o total do pedido baseado nos itens
     */
    private Double calculateTotal(List<ShopItem> items) {
        return items.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }
    
    /**
     * Converte string para LocalDateTime
     */
    private LocalDateTime parseDateTime(String dateTimeStr) {
        try {
            // Tenta primeiro formato ISO com hora
            if (dateTimeStr.contains("T")) {
                return LocalDateTime.parse(dateTimeStr);
            }
            // Se não, assume que é apenas data e adiciona meia-noite
            LocalDate date = LocalDate.parse(dateTimeStr);
            return date.atStartOfDay();
        } catch (Exception e) {
            log.error("Erro ao converter data: {}", dateTimeStr, e);
            throw new IllegalArgumentException("Formato de data inválido: " + dateTimeStr);
        }
    }
}
