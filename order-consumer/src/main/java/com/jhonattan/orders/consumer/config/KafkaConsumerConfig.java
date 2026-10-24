package com.jhonattan.orders.consumer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
public class KafkaConsumerConfig {

    public static final String ORDERS_CREATED_TOPIC = "orders.created";

    /** Garante que o tópico existe mesmo se o consumidor subir antes do produtor. */
    @Bean
    public NewTopic ordersCreatedTopic() {
        return TopicBuilder.name(ORDERS_CREATED_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }

    /** Em caso de erro ao processar: 3 novas tentativas, 1 segundo entre elas. */
    @Bean
    public DefaultErrorHandler kafkaErrorHandler() {
        return new DefaultErrorHandler(new FixedBackOff(1000L, 3L));
    }
}
