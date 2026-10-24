package com.jhonattan.orders.consumer.service;

import com.jhonattan.orders.consumer.entity.OrderEntity;
import com.jhonattan.orders.consumer.event.OrderCreatedEvent;
import com.jhonattan.orders.consumer.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderProcessingService {

    private static final Logger log = LoggerFactory.getLogger(OrderProcessingService.class);

    private final OrderRepository repository;

    public OrderProcessingService(OrderRepository repository) {
        this.repository = repository;
    }

    /**
     * Idempotente: o Kafka garante entrega "pelo menos uma vez", então o mesmo
     * evento pode chegar duas vezes. Se o pedido já existe, ignoramos.
     *
     * @return true se o pedido foi salvo, false se era duplicado
     */
    @Transactional
    public boolean process(OrderCreatedEvent event) {
        if (repository.existsById(event.orderId())) {
            log.warn("Pedido {} já processado, ignorando evento duplicado", event.orderId());
            return false;
        }
        repository.save(OrderEntity.from(event));
        log.info("Pedido {} salvo: {} x {} para {}",
                event.orderId(), event.quantity(), event.product(), event.customer());
        return true;
    }
}
