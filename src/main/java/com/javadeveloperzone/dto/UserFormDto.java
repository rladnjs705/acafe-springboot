package com.javadeveloperzone.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    @Size(min = 8, max = 20, message = "비밀번호는 최소 8자 이상, 최대 20자 이하이어야 합니다.")
    @Pattern(regexp = "^(?=.*[!@#$%^&*(),.?\":{}|<>]).*$", message = "특수문자를 최소 1개 이상 포함해야 합니다.")
    private String pwd;

    @NotBlank(message = "성함을 입력해주세요.")
    @Pattern(regexp = "^[a-zA-Z가-힣]{2,10}$", message = "2~10자의 영문 또는 한글로 이루어진 성함을 입력해주세요.")
    private String userName;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Pattern(regexp = "^[a-zA-Z0-9ㄱ-ㅎㅏ-ㅣ가-힣]{2,10}$", message = "2~10자의 영문, 숫자, 한글 자모음으로 이루어진 닉네임을 입력해주세요.")
    private String nickName;
}
