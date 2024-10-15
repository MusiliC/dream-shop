package dev.cee.dreamshops.repository.cart;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.cee.dreamshops.model.CartItem;

public interface CartItemRepository  extends JpaRepository<CartItem, Long> {

    void deleteAllByCartId(Long cartId);
}
