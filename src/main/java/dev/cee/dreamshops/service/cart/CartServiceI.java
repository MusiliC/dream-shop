package dev.cee.dreamshops.service.cart;

import java.math.BigDecimal;

import dev.cee.dreamshops.model.Cart;

public interface CartServiceI {


    Cart getCart(Long id);

    void clearCart(Long id);

    BigDecimal getTotalPrice(Long id);

    Long initNewCart();
}
