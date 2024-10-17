package dev.cee.dreamshops.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.cee.dreamshops.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
