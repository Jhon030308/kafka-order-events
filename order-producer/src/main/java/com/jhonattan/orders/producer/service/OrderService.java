package com.jhonattan.orders.producer.service;

import com.jhonattan.orders.producer.dto.OrderRequest;
import com.jhonattan.orders.producer.event.OrderCreatedEvent;
import com.jhonattan.orders.producer.messaging.OrderEventPublisher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderEventPublisher publisher;

    public OrderService(OrderEventPublisher publisher) {
        this.publisher = publisher;
    }

    public OrderCreatedEvent createOrder(OrderRequest request) {
        BigDecimal total = request.unitPrice().multiply(BigDecimal.valueOf(request.quantity()));

        OrderCreatedEvent event = new OrderCreatedEvent(
                UUID.randomUUID(),
                request.customer(),
                request.product(),
                request.quantity(),
                total,
                Instant.now()
        );

        publisher.publish(event);
        return event;
    }
}
