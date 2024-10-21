package dev.cee.dreamshops.dtos;

import java.util.Set;

import lombok.Data;

@Data
public class CartDto {

    private Long cartId;
    private Set<CartItemDto> cartItems;
}
