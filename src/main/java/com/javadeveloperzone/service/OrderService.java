package com.javadeveloperzone.service;

import com.javadeveloperzone.dto.OrderDto;
import com.javadeveloperzone.dto.OrderStreamDto;
import com.javadeveloperzone.dto.UserDto;
import com.javadeveloperzone.dto.UserFormDto;
import com.javadeveloperzone.entity.Order;
import com.javadeveloperzone.entity.Users;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

import java.util.List;

public interface OrderService {
    Order createOrder(Order order);
    List<Order> getOrderList();
    Order getOrder(Long orderId);

    void subscribeOrder(SseEmitter emitter, List<OrderStreamDto> list);
}
