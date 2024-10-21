package dev.cee.dreamshops.service.order;

import java.util.List;

import dev.cee.dreamshops.model.Order;

public interface OrderServiceI {

    Order placeOrder(Long userId, Order order);

    Order getOrderById(Long orderId);

    List<Order> getUserOrders(Long userId);
}
