package com.javadeveloperzone.service.impl;

import com.javadeveloperzone.entity.Category;
import com.javadeveloperzone.entity.Item;
import com.javadeveloperzone.repository.CategoryRepository;
import com.javadeveloperzone.repository.ItemRepository;
import com.javadeveloperzone.service.CategoryService;
import com.javadeveloperzone.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Override

    public Item createItem(Item item) {

        Item result = itemRepository.save(item);

        return result;
    }

    @Override
//    @Transactional(readOnly = true)
    public Page<Item> getItemList(Pageable paging) {
        return itemRepository.findAll(paging);
    }

    @Override
    @Transactional(readOnly = true)
    public Item getItem(Item item) {
        Item result = itemRepository.findById(item.getId()).get();
        return result;
    }

    @Override
    public void deleteItem(Long itemId) {
        itemRepository.deleteById(itemId);
    }

}
