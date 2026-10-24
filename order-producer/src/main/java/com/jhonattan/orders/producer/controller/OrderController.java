package com.jhonattan.orders.producer.controller;

import com.jhonattan.orders.producer.dto.OrderRequest;
import com.jhonattan.orders.producer.event.OrderCreatedEvent;
import com.jhonattan.orders.producer.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "Cria um pedido e publica o evento orders.created no Kafka")
    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public OrderCreatedEvent create(@Valid @RequestBody OrderRequest request) {
        return orderService.createOrder(request);
    }
}
