package dev.cee.dreamshops.data;

import java.util.Set;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import dev.cee.dreamshops.model.Role;
import dev.cee.dreamshops.model.User;
import dev.cee.dreamshops.repository.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Transactional
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Set<String> defaultRoles = Set.of("ROLE_ADMIN", "ROLE_USER");
        createDefaultUserIfNotExists();
        createDefaultRolesIfNotExists(defaultRoles);
        createDefaultAdminIfNotExists();
    }

    private void createDefaultUserIfNotExists() {
        Role user_role = roleRepository.findByName("ROLE_USER").get();
        for (int i = 0; i < 5; i++) {
            String defaultEmail = "user" + i + "@gmail.com";

            if (userRepository.existsByEmail(defaultEmail)) {
                continue;
            }

            User user = new User();

            user.setFirstName("User");
            user.setLastName("User" + i);
            user.setEmail(defaultEmail);
            user.setRoles(Set.of(user_role));
            user.setPassword(passwordEncoder.encode("Admin@123"));
            userRepository.save(user);
            System.out.println("Default user " + i + " created");
        }
    }


    private void createDefaultAdminIfNotExists() {
        Role admin_role = roleRepository.findByName("ROLE_ADMIN").get();
        for (int i = 0; i < 2; i++) {
            String defaultEmail = "admin" + i + "@gmail.com";

            if (userRepository.existsByEmail(defaultEmail)) {
                continue;
            }

            User user = new User();

            user.setFirstName("Admin");
            user.setLastName("Admin" + i);
            user.setEmail(defaultEmail);
            user.setRoles(Set.of(admin_role));
            user.setPassword(passwordEncoder.encode("Admin@123"));
            userRepository.save(user);
            System.out.println("Default admin " + i + " created");
        }
    }

    private void createDefaultRolesIfNotExists(Set<String> roles) {
        roles.stream()
                .filter(role -> roleRepository.findByName(role).isEmpty())
                .map(Role::new).forEach(roleRepository::save);
    }
}
