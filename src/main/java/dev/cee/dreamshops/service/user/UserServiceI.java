package dev.cee.dreamshops.service.user;

import dev.cee.dreamshops.dtos.CreateUserRequest;
import dev.cee.dreamshops.dtos.UserDto;
import dev.cee.dreamshops.dtos.UserUpdateRequest;
import dev.cee.dreamshops.model.User;

public interface UserServiceI {

    User getUserById(Long id);

    User createUser(CreateUserRequest request);

    User updateUser(UserUpdateRequest request, Long userId);

    void deleteUser(Long userId);

    UserDto convertUserToUserDto(User user);
}
