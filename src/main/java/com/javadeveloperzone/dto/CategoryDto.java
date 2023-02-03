package com.javadeveloperzone.dto;

import com.javadeveloperzone.entity.Category;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
public class CategoryDto {
    @NotBlank(message = "카테고리를 입력해 주세요.")
    private String categoryName;

    private Long categoryId;

    @Builder
    public CategoryDto(Category category) {
        this.categoryId = category.getId();
        this.categoryName = category.getCategoryName();
    }
}
