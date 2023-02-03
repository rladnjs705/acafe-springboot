package com.javadeveloperzone;

import com.javadeveloperzone.config.ProjectConstants;
import com.javadeveloperzone.domain.Role;
import com.javadeveloperzone.entity.Users;
import com.javadeveloperzone.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@org.springframework.boot.autoconfigure.SpringBootApplication
@RequiredArgsConstructor
public class SpringBootApplication {
    public static void main(String[] args){
        SpringApplication.run(SpringBootApplication.class);
    }

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    private void init() {
        // 초기화 처리
        Users admin = Users.builder()
                .email("admin@admin.com")
                .password(passwordEncoder.encode("1234"))
                .role(Role.ADMIN)
                .build();

        //userRepository.save(admin);
    }
}
