package org.example.chatapp.component;

import lombok.RequiredArgsConstructor;
import org.example.chatapp.entity.User;
import org.example.chatapp.repo.UserRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor

public class Runner implements CommandLineRunner {
    @Value("${spring.jpa.hibernate.ddl-auto}")
    String ddl;

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) throws Exception {
        if (ddl.equals("create")) {

            for (int i = 1; i <= 5; i++) {
                userRepo.save(User.builder()
                        .id(i)
                        .password(passwordEncoder.encode("123"))
                        .username("user" + i)
                        .build());
            }
        }
    }
}
