package com.jhonattan.orders.consumer.dto;

import com.jhonattan.orders.consumer.entity.OrderEntity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record OrderResponse(
        UUID id,
        String customer,
        String product,
        int quantity,
        BigDecimal totalAmount,
        Instant createdAt,
        Instant processedAt
) {
    public static OrderResponse from(OrderEntity e) {
        return new OrderResponse(e.getId(), e.getCustomer(), e.getProduct(),
                e.getQuantity(), e.getTotalAmount(), e.getCreatedAt(), e.getProcessedAt());
    }
}
