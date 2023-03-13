package com.javadeveloperzone.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.javadeveloperzone.dto.QItemDto is a Querydsl Projection type for ItemDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QItemDto extends ConstructorExpression<ItemDto> {

    private static final long serialVersionUID = 1840456086L;

    public QItemDto(com.querydsl.core.types.Expression<Long> itemId, com.querydsl.core.types.Expression<String> itemName, com.querydsl.core.types.Expression<Integer> itemPrice, com.querydsl.core.types.Expression<String> itemImage, com.querydsl.core.types.Expression<String> displayYn, com.querydsl.core.types.Expression<Integer> itemOrder, com.querydsl.core.types.Expression<Long> categoryId) {
        super(ItemDto.class, new Class<?>[]{long.class, String.class, int.class, String.class, String.class, int.class, long.class}, itemId, itemName, itemPrice, itemImage, displayYn, itemOrder, categoryId);
    }

}

