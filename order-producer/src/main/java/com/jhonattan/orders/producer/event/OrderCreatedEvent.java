package com.jhonattan.orders.producer.event;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record OrderCreatedEvent(
        UUID orderId,
        String customer,
        String product,
        int quantity,
        BigDecimal totalAmount,
        Instant createdAt
) {
}
