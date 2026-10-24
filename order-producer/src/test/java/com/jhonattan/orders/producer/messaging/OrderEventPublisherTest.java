package com.jhonattan.orders.producer.messaging;

import com.jhonattan.orders.producer.event.OrderCreatedEvent;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.KafkaTemplate;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderEventPublisherTest {

    @Test
    @SuppressWarnings("unchecked")
    void shouldUseOrderIdAsMessageKey() {
        KafkaTemplate<String, OrderCreatedEvent> template = mock(KafkaTemplate.class);
        when(template.send(anyString(), anyString(), any(OrderCreatedEvent.class)))
                .thenReturn(CompletableFuture.completedFuture(null));

        OrderEventPublisher publisher = new OrderEventPublisher(template);
        UUID id = UUID.randomUUID();
        OrderCreatedEvent event = new OrderCreatedEvent(
                id, "Maria", "Teclado", 1, new BigDecimal("150.00"), Instant.now());

        publisher.publish(event);

        verify(template).send("orders.created", id.toString(), event);
    }
}
