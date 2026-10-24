package com.jhonattan.orders.producer.service;

import com.jhonattan.orders.producer.dto.OrderRequest;
import com.jhonattan.orders.producer.event.OrderCreatedEvent;
import com.jhonattan.orders.producer.messaging.OrderEventPublisher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderEventPublisher publisher;

    @InjectMocks
    private OrderService service;

    @Test
    void shouldCalculateTotalAndPublishEvent() {
        OrderRequest request = new OrderRequest("Maria", "Teclado", 3, new BigDecimal("10.50"));

        OrderCreatedEvent result = service.createOrder(request);

        ArgumentCaptor<OrderCreatedEvent> captor = ArgumentCaptor.forClass(OrderCreatedEvent.class);
        verify(publisher).publish(captor.capture());

        OrderCreatedEvent published = captor.getValue();
        assertThat(published).isEqualTo(result);
        assertThat(published.orderId()).isNotNull();
        assertThat(published.customer()).isEqualTo("Maria");
        assertThat(published.totalAmount()).isEqualByComparingTo("31.50");
    }
}
