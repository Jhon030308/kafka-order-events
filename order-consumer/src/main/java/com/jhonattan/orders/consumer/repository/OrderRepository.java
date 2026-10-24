package com.jhonattan.orders.consumer.repository;

import com.jhonattan.orders.consumer.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
}
