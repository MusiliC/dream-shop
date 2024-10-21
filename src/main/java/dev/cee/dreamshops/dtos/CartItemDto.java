package dev.cee.dreamshops.dtos;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CartItemDto {

    private Long itemId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private ProductResponseDto product;
}
