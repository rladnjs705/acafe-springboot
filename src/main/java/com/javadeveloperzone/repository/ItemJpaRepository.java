package com.javadeveloperzone.repository;

import com.javadeveloperzone.dto.ItemDto;
import com.javadeveloperzone.entity.Item;
import com.javadeveloperzone.repository.support.Querydsl4RepositorySupport;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.javadeveloperzone.entity.QCategory.category;
import static com.javadeveloperzone.entity.QItem.item;
import static com.querydsl.jpa.JPAExpressions.selectFrom;

@Repository
public class ItemJpaRepository extends Querydsl4RepositorySupport {

    public ItemJpaRepository() {
        super(Item.class);
    }

    public Page<Item> searchPageItem(ItemDto condition, Pageable pageable) {
        JPQLQuery<Item> query = selectFrom(item)
                .where(
                        itemNameEq(condition.getItemName()),
                        categoryIdEq(condition.getCategoryId())
                );
        List<Item> content = getQuerydsl().applyPagination(pageable, query).fetch();
        return PageableExecutionUtils.getPage(content, pageable, query::fetchCount);
    }

    public Page<Item> applyPagination(ItemDto condition, Pageable pageable){
        return applyPagination(pageable, query->
                query.selectFrom(item)
                        .where(
                                itemNameEq(condition.getItemName()),
                                categoryIdEq(condition.getCategoryId())
                        )
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize()), query ->
                query.select(item.count())
                        .from(item)
                        .leftJoin(item.category, category)
                        .where(
                            itemNameEq(condition.getItemName()),
                            categoryIdEq(condition.getCategoryId())
                        )
                );
    }

    private BooleanExpression itemNameEq(String itemName) {
        return StringUtils.hasText(itemName) ? item.itemName.contains(itemName) : null;
    }

    private BooleanExpression categoryIdEq(Long categoryId) {
        if(categoryId > 0){
            return categoryId != null ? category.id.eq(categoryId) : null;
        }else{
            return null;
        }
    }

}
