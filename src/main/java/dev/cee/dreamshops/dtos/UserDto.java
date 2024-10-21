package dev.cee.dreamshops.dtos;

import java.util.List;

import dev.cee.dreamshops.model.Cart;
import lombok.Data;

@Data
public class UserDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private List<OrderResponseDto> orders;

    private Cart cart;
}
