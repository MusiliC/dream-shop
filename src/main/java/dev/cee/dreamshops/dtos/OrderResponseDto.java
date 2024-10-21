package dev.cee.dreamshops.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class OrderResponseDto {

    private Long orderId;

    private Long userId;

    private LocalDateTime orderDate;

    private BigDecimal orderTotalAmount;

    private String orderStatus;

    private List<OrderItemDto> orderItems;
}
