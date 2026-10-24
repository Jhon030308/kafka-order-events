package com.jhonattan.orders.consumer.controller;

import com.jhonattan.orders.consumer.dto.OrderResponse;
import com.jhonattan.orders.consumer.repository.OrderRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderQueryController {

    private final OrderRepository repository;

    public OrderQueryController(OrderRepository repository) {
        this.repository = repository;
    }

    @Operation(summary = "Lista os pedidos já processados pelo consumidor")
    @GetMapping
    public List<OrderResponse> findAll() {
        return repository.findAll().stream().map(OrderResponse::from).toList();
    }

    @Operation(summary = "Busca um pedido processado pelo id")
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> findById(@PathVariable UUID id) {
        return repository.findById(id)
                .map(OrderResponse::from)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
