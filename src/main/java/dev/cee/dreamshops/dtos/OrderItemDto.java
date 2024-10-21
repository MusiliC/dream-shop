package dev.cee.dreamshops.dtos;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class OrderItemDto {

    private Long productId;

    private String productName;

    private Integer quantity;

    private BigDecimal price;
}
