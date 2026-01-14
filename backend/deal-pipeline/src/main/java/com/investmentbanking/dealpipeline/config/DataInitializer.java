package com.investmentbanking.dealpipeline.config;

import com.investmentbanking.dealpipeline.user.model.Role;
import com.investmentbanking.dealpipeline.user.model.User;
import com.investmentbanking.dealpipeline.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (userRepository.count() == 0) {

            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .role(Role.ADMIN)
                    .active(true)
                    .build();

            User user = User.builder()
                    .username("user")
                    .password(passwordEncoder.encode("user123"))
                    .role(Role.USER)
                    .active(true)
                    .build();

            userRepository.save(admin);
            userRepository.save(user);

            System.out.println("Default users created");
        }
    }
}
