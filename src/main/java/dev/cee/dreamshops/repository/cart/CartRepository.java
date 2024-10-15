package dev.cee.dreamshops.repository.cart;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.cee.dreamshops.model.Cart;


public interface CartRepository  extends JpaRepository<Cart, Long> {
}
