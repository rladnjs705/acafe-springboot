package com.javadeveloperzone.repository;

import com.javadeveloperzone.dto.ItemDto;
import com.javadeveloperzone.dto.QItemDto;
import com.javadeveloperzone.entity.Category;
import com.javadeveloperzone.entity.Item;
import com.javadeveloperzone.entity.QItem;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.javadeveloperzone.entity.QItem.item;
import static com.javadeveloperzone.entity.QCategory.category;
import static com.querydsl.jpa.JPAExpressions.select;
import static com.querydsl.jpa.JPAExpressions.selectFrom;

@Repository
@RequiredArgsConstructor
public class MemberJpaRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }

    public Page<ItemDto> searchByBuilder(ItemDto condition, Pageable pageable) {

//        BooleanBuilder builder = new BooleanBuilder();
//        if(StringUtils.hasText(condition.getItemName())){
//            builder.and(item.itemName.eq(condition.getItemName()));
//        }
//        if(null != condition.getCategoryId()){
//            builder.and(category.id.eq(condition.getCategoryId()));
//        }
        List<ItemDto> content = queryFactory
                .select(new QItemDto(
                        item.id.as("itemId"),
                        item.itemName,
                        item.itemPrice,
                        item.itemImage,
                        category.id.as("categoryId")))
                .from(item)
                .leftJoin(item.category, category)
                .where(
                        itemNameEq(condition.getItemName()),
                        categoryIdEq(condition.getCategoryId())
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
                        categoryIdEq(condition.getCategoryId())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression itemNameEq(String itemName) {
        return StringUtils.hasText(itemName) ? item.itemName.contains(itemName) : null;
    }

    private BooleanExpression categoryIdEq(Long categoryId) {
        return categoryId != null ? category.id.eq(categoryId) : null;
    }

}
