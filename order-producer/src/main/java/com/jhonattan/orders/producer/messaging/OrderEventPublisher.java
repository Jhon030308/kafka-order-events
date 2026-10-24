package com.jhonattan.orders.producer.messaging;

import com.jhonattan.orders.producer.config.KafkaTopicConfig;
import com.jhonattan.orders.producer.event.OrderCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(OrderEventPublisher.class);

    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    public OrderEventPublisher(KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * A chave da mensagem é o orderId: eventos do mesmo pedido
     * sempre caem na mesma partição, preservando a ordem.
     */
    public void publish(OrderCreatedEvent event) {
        String key = event.orderId().toString();
        kafkaTemplate.send(KafkaTopicConfig.ORDERS_CREATED_TOPIC, key, event)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Falha ao publicar pedido {}", key, ex);
                    } else {
                        log.info("Pedido {} publicado no tópico {}", key, KafkaTopicConfig.ORDERS_CREATED_TOPIC);
                    }
                });
    }
}
