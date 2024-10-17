package dev.cee.dreamshops.service.order;


import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;

import dev.cee.dreamshops.exceptions.ResourceNotFoundException;
import dev.cee.dreamshops.model.Order;
import dev.cee.dreamshops.model.OrderItem;
import dev.cee.dreamshops.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService implements OrderServiceI {

    private final OrderRepository orderRepository;

    @Override
    public Order placeOrder(Long userId, Order order) {
        throw new UnsupportedOperationException("placeOrder of OrderService class is not implemented");
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(items -> items.getPrice().multiply(new BigDecimal(items.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


}
