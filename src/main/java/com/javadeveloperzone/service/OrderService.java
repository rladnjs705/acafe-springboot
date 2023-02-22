package com.javadeveloperzone.service;

import com.javadeveloperzone.dto.OrderDto;
import com.javadeveloperzone.dto.UserDto;
import com.javadeveloperzone.dto.UserFormDto;
import com.javadeveloperzone.entity.Order;
import com.javadeveloperzone.entity.Users;

import java.util.List;

public interface OrderService {
    Order createOrder(Order order);
    List<Order> getOrderList();
    Order getOrder(Long orderId);
}
