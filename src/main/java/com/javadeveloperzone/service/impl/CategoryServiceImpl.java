package com.javadeveloperzone.service.impl;

import com.javadeveloperzone.entity.Category;
import com.javadeveloperzone.repository.CategoryRepository;
import com.javadeveloperzone.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category createCategory(Category category) {

        // 카테고리 중복 확인
//        if(categoryRepository.findByCategoryName(category.getCategoryName()) != null){
//            return null;
//        }

        // 카테고리 중복 확인
        validateDuplicateCategory(category);

        Category result = categoryRepository.save(Category.builder()
                .categoryName(category.getCategoryName())
                .build());

        return result;
    }

    @Override
    public List<Category> getCategoryList() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategory(Category category) {
        validateDuplicateCategory(category);
        Category result = categoryRepository.findById(category.getId()).get();
        return result;
    }

    @Override
    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    private void validateDuplicateCategory(Category category){
        categoryRepository.findByCategoryName(category.getCategoryName()).ifPresent((m -> {
            throw new IllegalArgumentException("이미 존재하는 카테고리입니다.");
        }));
    }
}
