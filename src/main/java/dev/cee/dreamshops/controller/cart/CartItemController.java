package dev.cee.dreamshops.controller.cart;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import dev.cee.dreamshops.controller.response.ApiResponse;
import dev.cee.dreamshops.exceptions.ResourceNotFoundException;
import dev.cee.dreamshops.model.Cart;
import dev.cee.dreamshops.model.User;
import dev.cee.dreamshops.service.cart.CartItemServiceI;
import dev.cee.dreamshops.service.cart.CartServiceI;
import dev.cee.dreamshops.service.user.UserServiceI;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {

    private final CartItemServiceI cartItemService;

    private final CartServiceI cartService;

    private final UserServiceI userService;

    @PostMapping("")
    public ResponseEntity<ApiResponse> addCartItem(@RequestParam Long productId, @RequestParam Integer quantity) {

        try {

            User user = userService.getUserById(1L);
            Cart cart = cartService.initNewCart(user);

            cartItemService.addCartItem(cart.getId(), productId, quantity);

            return ResponseEntity.ok(new ApiResponse("Add Item Success", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{cartId}/{productId}")
    public ResponseEntity<ApiResponse> removeCartItem(@PathVariable Long cartId, @PathVariable Long productId) {

        try {
            cartItemService.removeCartItem(cartId, productId);

            return ResponseEntity.ok(new ApiResponse("Remove Item Success", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/{cartId}/{productId}")
    public ResponseEntity<ApiResponse> updateCartItemQuantity(@PathVariable Long cartId, @PathVariable Long productId, @RequestParam Integer quantity) {

        try {
            cartItemService.updateItemQuantity(cartId, productId, quantity);

            return ResponseEntity.ok(new ApiResponse("Update Item Success", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
