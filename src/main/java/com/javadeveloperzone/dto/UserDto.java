package com.javadeveloperzone.dto;

import com.javadeveloperzone.domain.Role;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//로그인 DTO
@Data
@NoArgsConstructor
public class UserDto {
    private Long id;
    @Email
    private String email;
    private String password;
    private Role role;

    private String authToken;

    @Builder
    public UserDto(Long id, String email, String password, Role role, String authToken) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.authToken = authToken;
    }
}
