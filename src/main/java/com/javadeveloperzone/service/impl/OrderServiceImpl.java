package com.javadeveloperzone.service.impl;

import com.javadeveloperzone.dto.OrderDto;
import com.javadeveloperzone.dto.OrderStreamDto;
import com.javadeveloperzone.entity.Order;
import com.javadeveloperzone.repository.OrderRepository;
import com.javadeveloperzone.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
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
    @Transactional(readOnly = true)
    public List<Order> getOrderList() {
        return orderRepository.findAllWithUserAndOrderItems();
    }

    @Override
    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId).get();
    }

    @Override
    public void subscribeOrder(SseEmitter emitter, List<OrderStreamDto> list) {
        if(emitter != null){
            try {
                emitter.send(SseEmitter.event().reconnectTime(500).data(list));
            } catch (IOException e){
                emitter.complete();
                emitter = null;
            }
        }
    }
}
