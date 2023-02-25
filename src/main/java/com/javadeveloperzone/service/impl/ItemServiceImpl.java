package com.javadeveloperzone.service.impl;

import com.javadeveloperzone.dto.ItemDto;
import com.javadeveloperzone.entity.Item;
import com.javadeveloperzone.repository.ItemJpaRepository;
import com.javadeveloperzone.repository.ItemRepository;
import com.javadeveloperzone.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    private final ItemJpaRepository itemJpaRepository;

    @Override
    @Transactional
    public Item createItem(Item item) {

        Item result = itemRepository.save(item);

        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Item getItem(Item item) {
        Item result = itemRepository.findById(item.getItemId()).get();
        return result;
    }

    @Override
    @Transactional
    public void deleteItem(Long itemId) {
        itemRepository.deleteByItemIdEquals(itemId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Item> getItemList(ItemDto condition, Pageable pageable){
        return itemJpaRepository.applyPagination(condition, pageable);
    }

}
