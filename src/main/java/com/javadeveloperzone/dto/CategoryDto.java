package com.javadeveloperzone.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryDto {
    @NotBlank(message = "카테고리를 입력해 주세요.")
    private String categoryName;
}
