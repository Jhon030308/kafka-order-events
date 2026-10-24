package com.jhonattan.orders.consumer.service;

import com.jhonattan.orders.consumer.entity.OrderEntity;
import com.jhonattan.orders.consumer.event.OrderCreatedEvent;
import com.jhonattan.orders.consumer.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class OrderProcessingServiceTest {

    @Mock
    private OrderRepository repository;

    @InjectMocks
    private OrderProcessingService service;

    private OrderCreatedEvent newEvent() {
        return new OrderCreatedEvent(UUID.randomUUID(), "Maria", "Teclado", 2,
                new BigDecimal("300.00"), Instant.now());
    }

    @Test
    void shouldSaveNewOrder() {
        OrderCreatedEvent event = newEvent();
        when(repository.existsById(event.orderId())).thenReturn(false);

        boolean saved = service.process(event);

        assertThat(saved).isTrue();
        ArgumentCaptor<OrderEntity> captor = ArgumentCaptor.forClass(OrderEntity.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getId()).isEqualTo(event.orderId());
        assertThat(captor.getValue().getCustomer()).isEqualTo("Maria");
        assertThat(captor.getValue().getTotalAmount()).isEqualByComparingTo("300.00");
    }

    @Test
    void shouldIgnoreDuplicatedEvent() {
        OrderCreatedEvent event = newEvent();
        when(repository.existsById(event.orderId())).thenReturn(true);

        boolean saved = service.process(event);

        assertThat(saved).isFalse();
        verify(repository, never()).save(any());
    }
}
