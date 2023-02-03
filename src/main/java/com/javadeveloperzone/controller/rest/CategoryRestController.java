package com.javadeveloperzone.controller.rest;

import com.javadeveloperzone.config.exception.ResultCodeType;
import com.javadeveloperzone.config.utils.ResponseUtils;
import com.javadeveloperzone.dto.CategoryDto;
import com.javadeveloperzone.entity.Category;
import com.javadeveloperzone.model.ResponseVo;
import com.javadeveloperzone.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@RestController
public class CategoryRestController {

    private final CategoryService categoryService;

    @GetMapping("/admin/categories")
    public ResponseEntity<ResponseVo> categories(@RequestParam HashMap<String, Object> param) {
        Map<String,Object> respMap = new HashMap<String, Object>();

        List<Category> categoryList = categoryService.getCategoryList();
        List<CategoryDto> result = categoryList.stream()
                .map(c -> new CategoryDto(c))
                .collect(Collectors.toList());

        respMap.put("list", result);

        return ResponseUtils.response(respMap);
    }

    @PostMapping("/admin/category/create")
    public ResponseEntity<ResponseVo> createCategory(@Validated CategoryDto categoryDto, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            log.info("errors={}", bindingResult);
            return ResponseUtils.response(ResultCodeType.ERROR_PARAM, bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        Category category = new Category();
        category = category.builder().
                categoryName(categoryDto.getCategoryName())
                .build();
        categoryService.createCategory(category);

        return ResponseUtils.response(ResultCodeType.SUCCESS, null);
    }

    @PutMapping("/admin/category/update")
    @Transactional
    public ResponseEntity<ResponseVo> updateCategory(@Validated CategoryDto categoryDto, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            log.info("errors={}", bindingResult);
            return ResponseUtils.response(ResultCodeType.ERROR_PARAM, bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        Category category = new Category();
        category = category.builder()
                .categoryId(categoryDto.getCategoryId())
                .categoryName(categoryDto.getCategoryName())
                .build();
        Category data = categoryService.getCategory(category);
        data.updateCategory(category);


        return ResponseUtils.response(ResultCodeType.SUCCESS, null);
    }

    @DeleteMapping("/admin/category/delete/{id}")
    public ResponseEntity<ResponseVo> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);

        return ResponseUtils.response(ResultCodeType.SUCCESS, null);
    }
}
