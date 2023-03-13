package com.javadeveloperzone.repository;

import com.javadeveloperzone.dto.ItemDto;
import com.javadeveloperzone.dto.QItemDto;
import com.javadeveloperzone.entity.Item;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.javadeveloperzone.entity.QCategory.category;
import static com.javadeveloperzone.entity.QItem.item;

@Repository
@RequiredArgsConstructor
public class MemberJpaRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }

    public Page<ItemDto> searchByBuilder(ItemDto condition, Pageable pageable) {
        List<ItemDto> content = queryFactory
                .select(new QItemDto(
                        item.itemId.as("itemId"),
                        item.itemName,
                        item.itemPrice,
                        item.itemImage,
                        item.displayYn,
                        category.id.as("categoryId")))
                .from(item)
                .leftJoin(item.category, category)
                .where(
                        itemNameEq(condition.getItemName()),
                        categoryIdEq(condition.getCategoryId()),
                        itemDisplayYnEq(condition.getAdminYn())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        JPAQuery<Long> countQuery = queryFactory
                .select(item.count())
                .from(item)
                .leftJoin(item.category, category)
                .where(
                        itemNameEq(condition.getItemName()),
                        categoryIdEq(condition.getCategoryId()),
                        itemDisplayYnEq(condition.getAdminYn())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression itemNameEq(String itemName) {
        return StringUtils.hasText(itemName) ? item.itemName.contains(itemName) : null;
    }

    private BooleanExpression itemDisplayYnEq(String adminYn) {
        if(adminYn.equals("N")){
            return item.displayYn.eq("Y");
        }else{
            return null;
        }
    }

    private BooleanExpression categoryIdEq(Long categoryId) {
        return categoryId != null ? category.id.eq(categoryId) : null;
    }

}
