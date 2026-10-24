package com.jhonattan.orders.consumer.entity;

import com.jhonattan.orders.consumer.event.OrderCreatedEvent;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String customer;

    @Column(nullable = false)
    private String product;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal totalAmount;

    @Column(nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant processedAt;

    protected OrderEntity() {
    }

    public static OrderEntity from(OrderCreatedEvent event) {
        OrderEntity entity = new OrderEntity();
        entity.id = event.orderId();
        entity.customer = event.customer();
        entity.product = event.product();
        entity.quantity = event.quantity();
        entity.totalAmount = event.totalAmount();
        entity.createdAt = event.createdAt();
        entity.processedAt = Instant.now();
        return entity;
    }

    public UUID getId() { return id; }
    public String getCustomer() { return customer; }
    public String getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getProcessedAt() { return processedAt; }
}
