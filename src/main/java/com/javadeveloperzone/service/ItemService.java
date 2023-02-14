package com.javadeveloperzone.service;

import com.javadeveloperzone.dto.ItemDto;
import com.javadeveloperzone.entity.Category;
import com.javadeveloperzone.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemService {
    Item createItem(Item item);
//    Page<Item> getItemList(Pageable paging, ItemDto dto);
    Item getItem(Item item);
    void deleteItem(Long itemId);

    Page<Item> getItemList(ItemDto condition, Pageable pageable);
}
