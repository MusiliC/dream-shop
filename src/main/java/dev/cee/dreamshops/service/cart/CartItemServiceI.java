package dev.cee.dreamshops.service.cart;

import java.math.BigDecimal;
import java.util.List;

import dev.cee.dreamshops.model.Cart;
import dev.cee.dreamshops.model.CartItem;

public interface CartItemServiceI {

    void addCartItem(Long cartId, Long productId, int quantity);

    void addCartItem(Long cartId, List<CartItem> cartItems);

    void removeCartItem(Long cartId, Long productId);

    void updateItemQuantity(Long cartId, Long productId, int quantity);


    CartItem getCartItem(Long cartId, Long productId);
}
