package dev.cee.dreamshops.service.order;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import dev.cee.dreamshops.dtos.OrderResponseDto;
import dev.cee.dreamshops.enums.OrderStatus;
import dev.cee.dreamshops.exceptions.ResourceNotFoundException;
import dev.cee.dreamshops.model.Cart;
import dev.cee.dreamshops.model.Order;
import dev.cee.dreamshops.model.OrderItem;
import dev.cee.dreamshops.model.Product;
import dev.cee.dreamshops.repository.order.OrderRepository;
import dev.cee.dreamshops.repository.product.ProductRepository;
import dev.cee.dreamshops.service.cart.CartServiceI;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService implements OrderServiceI {

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    private final ModelMapper modelMapper;

    private final CartServiceI cartService;

    @Override
    public Order placeOrder(Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        Order newOrder = createOrder(cart);
        List<OrderItem> orderItemList = createOrderItems(newOrder, cart);
        newOrder.setOrderItems(new HashSet<>(orderItemList));
        newOrder.setOrderTotalAmount(calculateTotalAmount(orderItemList));
        Order savedOrder = orderRepository.save(newOrder);

        cartService.clearCart(cart.getId());

        return savedOrder;

    }

    @Override
    public OrderResponseDto getOrderById(Long orderId) {

        return orderRepository.findById(orderId)
                .map(this::convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    private Order createOrder(Cart cart) {
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    private List<OrderItem> createOrderItems(Order order, Cart cart) {
        return cart.getItems()
                .stream()
                .map((cartItem -> {
                    Product product = cartItem.getProduct();
                    product.setInventory(product.getInventory() - cartItem.getQuantity());
                    productRepository.save(product);
                    return new OrderItem(
                            order,
                            product,
                            cartItem.getQuantity(),
                            cartItem.getUnitPrice()
                    );
                })).toList();
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(items -> items.getPrice().multiply(new BigDecimal(items.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public List<OrderResponseDto> getUserOrders(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);

        return orders.
                stream()
                .map(this::convertToDto)
                .toList();
    }


    private OrderResponseDto convertToDto(Order order) {
        return modelMapper.map(order, OrderResponseDto.class);
    }
}
