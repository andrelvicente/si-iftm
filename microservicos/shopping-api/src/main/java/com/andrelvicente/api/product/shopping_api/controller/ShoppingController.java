package com.andrelvicente.api.product.shopping_api.controller;

import com.andrelvicente.api.product.shopping_api.models.Shop;
import com.andrelvicente.api.product.shopping_api.models.dto.ShopDTO;
import com.andrelvicente.api.product.shopping_api.models.dto.ShopReportDTO;
import com.andrelvicente.api.product.shopping_api.services.ShoppingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/shopping")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Shopping API", description = "API para gerenciamento de pedidos/compras")
public class ShoppingController {
    
    private final ShoppingService shoppingService;
    
    @GetMapping
    @Operation(summary = "getAll", description = "Retorna todos os pedidos")
    public ResponseEntity<List<Shop>> getAll() {
        log.info("Endpoint getAll chamado");
        List<Shop> shops = shoppingService.getAll();
        return ResponseEntity.ok(shops);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "findById", description = "Busca pedido por ID")
    public ResponseEntity<Shop> findById(
            @Parameter(description = "ID do pedido") 
            @PathVariable String id) {
        log.info("Endpoint findById chamado com ID: {}", id);
        Optional<Shop> shop = shoppingService.findById(id);
        return shop.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    @Operation(summary = "save", description = "Cria um novo pedido")
    public ResponseEntity<Shop> save(@Valid @RequestBody ShopDTO shopDTO) {
        log.info("Endpoint save chamado para usuário: {}", shopDTO.getUserIdentifier());
        Shop savedShop = shoppingService.save(shopDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedShop);
    }
    
    @GetMapping("/shopByUser")
    @Operation(summary = "getByUser", description = "Busca pedidos por usuário")
    public ResponseEntity<List<Shop>> getByUser(
            @Parameter(description = "ID do usuário") 
            @RequestParam String userId) {
        log.info("Endpoint getByUser chamado com userId: {}", userId);
        List<Shop> shops = shoppingService.getByUser(userId);
        return ResponseEntity.ok(shops);
    }
    
    @GetMapping("/shopByDate")
    @Operation(summary = "getByDate", description = "Busca pedidos por intervalo de datas")
    public ResponseEntity<List<Shop>> getByDate(
            @Parameter(description = "Data de início (formato: yyyy-MM-dd ou yyyy-MM-ddTHH:mm:ss)") 
            @RequestParam String dataInicio,
            @Parameter(description = "Data de fim (formato: yyyy-MM-dd ou yyyy-MM-ddTHH:mm:ss)") 
            @RequestParam String dataFim) {
        log.info("Endpoint getByDate chamado - Início: {}, Fim: {}", dataInicio, dataFim);
        List<Shop> shops = shoppingService.getByDate(dataInicio, dataFim);
        return ResponseEntity.ok(shops);
    }
    
    @GetMapping("/{productIdentifier}")
    @Operation(summary = "findByProductIdentifier", description = "Busca pedidos que contenham um produto específico")
    public ResponseEntity<List<Shop>> findByProductIdentifier(
            @Parameter(description = "Identificador do produto") 
            @PathVariable String productIdentifier) {
        log.info("Endpoint findByProductIdentifier chamado com productIdentifier: {}", productIdentifier);
        List<Shop> shops = shoppingService.findByProductIdentifier(productIdentifier);
        return ResponseEntity.ok(shops);
    }
    
    @GetMapping("/search")
    @Operation(summary = "getShopsByFilter", description = "Busca pedidos com filtros avançados (data e valor mínimo)")
    public ResponseEntity<List<Shop>> getShopsByFilter(
            @Parameter(description = "Data de início (formato: yyyy-MM-dd ou yyyy-MM-ddTHH:mm:ss)") 
            @RequestParam String dataInicio,
            @Parameter(description = "Data de fim (formato: yyyy-MM-dd ou yyyy-MM-ddTHH:mm:ss)") 
            @RequestParam String dataFim,
            @Parameter(description = "Valor mínimo do pedido (opcional)") 
            @RequestParam(required = false) Double valorMinimo) {
        log.info("Endpoint getShopsByFilter chamado - Início: {}, Fim: {}, Valor Mínimo: {}", 
                dataInicio, dataFim, valorMinimo);
        List<Shop> shops = shoppingService.getShopsByFilter(dataInicio, dataFim, valorMinimo);
        return ResponseEntity.ok(shops);
    }
    
    @GetMapping("/report")
    @Operation(summary = "getReportByDate", description = "Gera relatório de vendas por intervalo de datas")
    public ResponseEntity<List<ShopReportDTO>> getReportByDate(
            @Parameter(description = "Data de início (formato: yyyy-MM-dd ou yyyy-MM-ddTHH:mm:ss)") 
            @RequestParam String dataInicio,
            @Parameter(description = "Data de fim (formato: yyyy-MM-dd ou yyyy-MM-ddTHH:mm:ss)") 
            @RequestParam String dataFim) {
        log.info("Endpoint getReportByDate chamado - Início: {}, Fim: {}", dataInicio, dataFim);
        List<ShopReportDTO> report = shoppingService.getReportByDate(dataInicio, dataFim);
        return ResponseEntity.ok(report);
    }
}
