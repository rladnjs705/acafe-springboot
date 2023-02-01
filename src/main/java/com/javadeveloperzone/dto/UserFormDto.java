package com.javadeveloperzone.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

//회원가입 DTO
@Data
@NoArgsConstructor
public class UserFormDto {
    @NotBlank(message = "이메일을 입력해 주세요.")
    @Email(message = "이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해 주세요.")
    private String password;
}
