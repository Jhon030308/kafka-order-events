package com.jhonattan.orders.consumer.event;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Cópia própria do contrato do evento. Os dois serviços ficam desacoplados:
 * compartilham apenas o formato JSON, não uma biblioteca.
 */
public record OrderCreatedEvent(
        UUID orderId,
        String customer,
        String product,
        int quantity,
        BigDecimal totalAmount,
        Instant createdAt
) {
}
