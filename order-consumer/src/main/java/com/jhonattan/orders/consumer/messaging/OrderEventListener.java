package com.jhonattan.orders.consumer.messaging;

import com.jhonattan.orders.consumer.config.KafkaConsumerConfig;
import com.jhonattan.orders.consumer.event.OrderCreatedEvent;
import com.jhonattan.orders.consumer.service.OrderProcessingService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventListener {

    private final OrderProcessingService service;

    public OrderEventListener(OrderProcessingService service) {
        this.service = service;
    }

    @KafkaListener(topics = KafkaConsumerConfig.ORDERS_CREATED_TOPIC)
    public void onOrderCreated(OrderCreatedEvent event) {
        service.process(event);
    }
}
