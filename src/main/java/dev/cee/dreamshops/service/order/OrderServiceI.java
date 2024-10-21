package dev.cee.dreamshops.service.order;

import java.util.List;

import dev.cee.dreamshops.dtos.OrderResponseDto;
import dev.cee.dreamshops.model.Order;

public interface OrderServiceI {

    Order placeOrder(Long userId);

    OrderResponseDto getOrderById(Long orderId);

    List<OrderResponseDto> getUserOrders(Long userId);
}
