package com.logitrack;

import com.logitrack.domain.User;
import com.logitrack.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class LogitrackProApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogitrackProApplication.class, args);
    }

    @Bean
    CommandLineRunner ensureDefaultAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            User admin = userRepository.findByUsername("admin")
                    .orElseGet(() -> new User("admin", "admin@logitrack.com", ""));

            // Keep a deterministic test credential in local/dev startup.
            admin.setEmail("admin@logitrack.com");
            admin.setPassword(passwordEncoder.encode("admin"));
            userRepository.save(admin);
        };
    }
}
