package dev.cee.dreamshops.service.cart;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import dev.cee.dreamshops.exceptions.ResourceNotFoundException;
import dev.cee.dreamshops.model.Cart;
import dev.cee.dreamshops.model.CartItem;
import dev.cee.dreamshops.model.Product;
import dev.cee.dreamshops.repository.cart.CartItemRepository;
import dev.cee.dreamshops.repository.cart.CartRepository;
import dev.cee.dreamshops.service.products.IproductService;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class CartItemService implements CartItemServiceI {

    private final CartItemRepository cartItemRepository;

    private final IproductService productService;

    private final CartServiceI cartService;

    private final CartRepository cartRepository;

    @Override
    public void addCartItem(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCart(cartId);
        Product product = productService.getProductById(productId);
        CartItem cartItem = cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(new CartItem());

        if (cartItem.getId() == null) {
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }

        cartItem.setTotalPrice();

        cart.addItem(cartItem);

        cartItemRepository.save(cartItem);

        cartRepository.save(cart);
    }

    @Override
    public void addCartItem(Long cartId, List<CartItem> cartItems) {
        Cart cart = cartService.getCart(cartId);

        cartItems.forEach(item -> {
            Product product = productService.getProductById(item.getProduct().getId());

            CartItem cartItem = cart.getItems()
                    .stream()
                    .filter(existingItem -> existingItem.getProduct().getId().equals(item.getProduct().getId()))
                    .findFirst()
                    .orElse(new CartItem());

            if (cartItem.getId() == null) {
                cartItem.setCart(cart);
                cartItem.setProduct(product);
                cartItem.setQuantity(item.getQuantity());
                cartItem.setUnitPrice(product.getPrice());
            } else {
                cartItem.setQuantity(cartItem.getQuantity() + item.getQuantity());
            }

            cartItem.setTotalPrice();

            cart.addItem(cartItem);

            cartItemRepository.save(cartItem);
        });

        cartRepository.save(cart);
    }


    @Override
    public void removeCartItem(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);

        CartItem itemToRemove = this.getCartItem(cartId, productId);

        cart.removeItem(itemToRemove);

        cartRepository.save(cart);
    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCart(cartId);

        cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(item -> {
                    item.setQuantity(quantity);
                    item.setUnitPrice(item.getProduct().getPrice());
                    item.setTotalPrice();
                });

        BigDecimal totalAmount = cart.getTotalAmount();

        cart.setTotalAmount(totalAmount);

        cartRepository.save(cart);
    }

    @Override
    public CartItem getCartItem(Long cartId, Long productId) {

        Cart cart = cartService.getCart(cartId);

        return cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + productId + " not found"));
    }
}
