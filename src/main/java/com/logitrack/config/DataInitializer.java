package com.logitrack.config;

import com.logitrack.domain.User;
import com.logitrack.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        User admin = userRepository.findByUsername("admin")
                .orElseGet(() -> new User("admin", "admin@logitrack.com", ""));

        admin.setEmail("admin@logitrack.com");
        admin.setPassword(passwordEncoder.encode("admin"));
        userRepository.save(admin);
    }
}
