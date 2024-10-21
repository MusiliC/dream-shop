package dev.cee.dreamshops.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.cee.dreamshops.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByEmail(String email);
}
