package com.javadeveloperzone.service.impl;

import com.javadeveloperzone.dto.ItemDto;
import com.javadeveloperzone.dto.OrderDto;
import com.javadeveloperzone.entity.Item;
import com.javadeveloperzone.entity.Order;
import com.javadeveloperzone.repository.ItemJpaRepository;
import com.javadeveloperzone.repository.ItemRepository;
import com.javadeveloperzone.repository.OrderRepository;
import com.javadeveloperzone.service.ItemService;
import com.javadeveloperzone.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getOrderList() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId).get();
    }
}
