package dev.cee.dreamshops.repository.order;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import dev.cee.dreamshops.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserId(Long userId);
}
