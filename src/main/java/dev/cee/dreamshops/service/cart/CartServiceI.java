package dev.cee.dreamshops.service.cart;

import java.math.BigDecimal;

import dev.cee.dreamshops.model.Cart;
import dev.cee.dreamshops.model.User;

public interface CartServiceI {


    Cart getCart(Long id);

    void clearCart(Long id);

    BigDecimal getTotalPrice(Long id);

    Cart initNewCart(User user);

    Cart getCartByUserId(Long userId);
}
