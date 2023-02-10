package com.javadeveloperzone.service;

import com.javadeveloperzone.dto.CategoryDto;
import com.javadeveloperzone.dto.UserDto;
import com.javadeveloperzone.dto.UserFormDto;
import com.javadeveloperzone.entity.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(Category category);
    List<Category> getCategoryList();
    Category getCategory(Category category);
    Category getDuplicateCategory(Category category);
    void deleteCategory(Long categoryId);
}
