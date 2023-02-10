package com.javadeveloperzone.controller.rest;

import com.javadeveloperzone.config.exception.ResultCodeType;
import com.javadeveloperzone.config.utils.ResponseUtils;
import com.javadeveloperzone.dto.ItemDto;
import com.javadeveloperzone.entity.Category;
import com.javadeveloperzone.entity.Item;
import com.javadeveloperzone.model.ResponseVo;
import com.javadeveloperzone.service.CategoryService;
import com.javadeveloperzone.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@RestController
public class ItemRestController {

    private final ItemService itemService;
    private final CategoryService categoryService;

    @GetMapping("/user/items")
    public ResponseEntity<ResponseVo> items(@PageableDefault(sort = "id", direction = Sort.Direction.DESC)Pageable pageable) {
        Map<String,Object> respMap = new HashMap<String, Object>();

        Page<Item> pageList = itemService.getItemList(pageable);

        List<ItemDto> itemDtoList = pageList.stream().map(ItemDto::new).collect(Collectors.toList());
        Page<ItemDto> itemDtoPage = new PageImpl<>(itemDtoList, pageable, pageList.getTotalElements());

        respMap.put("list", itemDtoPage.toList());
        respMap.put("pageable", itemDtoPage.getPageable());
        respMap.put("itemCount", itemDtoPage.getTotalElements());
        respMap.put("totalPage", itemDtoPage.getTotalPages());

        return ResponseUtils.response(respMap);
    }

    @PostMapping("/admin/item/create")
    public ResponseEntity<ResponseVo> createItem(@Validated ItemDto itemDto, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            log.info("errors={}", bindingResult);
            return ResponseUtils.response(ResultCodeType.ERROR_PARAM, bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        Map<String,Object> respMap = new HashMap<String, Object>();
        Category category = new Category();
        category = category.builder().
                categoryId(itemDto.getCategoryId())
                .build();
        category = categoryService.getCategory(category);

        Item item = new Item();
        item = item.builder().
                itemName(itemDto.getItemName())
                .itemPrice(itemDto.getItemPrice())
                .itemImage(itemDto.getItemImage())
                .category(category)
                .build();

        itemService.createItem(item);

        ItemDto dto = new ItemDto(item);

        respMap.put("result", dto);

        return ResponseUtils.response(ResultCodeType.SUCCESS, null);
    }
}
