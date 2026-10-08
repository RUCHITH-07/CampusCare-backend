package com.campuscare.config;

import com.campuscare.entity.Role;
import com.campuscare.entity.User;
import com.campuscare.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        String adminEmail = "admin@campuscare.com";
        if (!userRepository.existsByEmail(adminEmail)) {
            User admin = User.builder()
                    .fullName("System Administrator")
                    .email(adminEmail)
                    .password(passwordEncoder.encode("Admin@123"))
                    .role(Role.ADMIN)
                    .enabled(true)
                    .phone("9999999999")
                    .rollNumberOrEmployeeId("ADM-001")
                    .department("Administration")
                    .build();

            userRepository.save(admin);
            log.info("Default admin created successfully: {} / Admin@123", adminEmail);
        } else {
            log.info("Default admin already exists: {}", adminEmail);
        }
    }
}
