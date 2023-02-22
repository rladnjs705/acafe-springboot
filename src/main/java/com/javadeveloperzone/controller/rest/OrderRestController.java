package com.javadeveloperzone.controller.rest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.javadeveloperzone.config.exception.ResultCodeType;
import com.javadeveloperzone.config.utils.ResponseUtils;
import com.javadeveloperzone.dto.OrderDto;
import com.javadeveloperzone.dto.UserDto;
import com.javadeveloperzone.entity.Item;
import com.javadeveloperzone.entity.Order;
import com.javadeveloperzone.entity.OrderItem;
import com.javadeveloperzone.entity.Users;
import com.javadeveloperzone.model.ResponseVo;
import com.javadeveloperzone.service.ItemService;
import com.javadeveloperzone.service.OrderService;
import com.javadeveloperzone.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@RestController
public class OrderRestController {

    private final OrderService orderService;
    private final UserService userService;
    private final ItemService itemService;

    @GetMapping("/admin/orders")
    public ResponseEntity<ResponseVo> orders() {
        Map<String,Object> respMap = new HashMap<String, Object>();
        List<Order> result = orderService.getOrderList();

        respMap.put("list", result);

        return ResponseUtils.response(respMap);
    }

    @PostMapping("/user/order/create")
    public ResponseEntity<ResponseVo> createOrder(@Validated OrderDto orderDto, @RequestBody String body, BindingResult bindingResult) throws ParseException {

        JSONParser jsonParser1 = new JSONParser();
        JSONObject object = (JSONObject) jsonParser1.parse(body);
        JSONArray orderItems = (JSONArray) object.get("orderItems");
        if(orderItems.isEmpty()){
            return ResponseUtils.response(ResultCodeType.ERROR_PARAM, "주문항목은 최소 1개 이상 필요합니다.");
        }

        if(bindingResult.hasErrors()){
            log.info("errors={}", bindingResult);
            return ResponseUtils.response(ResultCodeType.ERROR_PARAM, bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        Map<String,Object> respMap = new HashMap<String, Object>();

        //주문 유저정보
        UserDto userDto = new UserDto();
        userDto.setId(orderDto.getUserId());
        Users user = Users.builder().build();
        if(null != orderDto.getUserId() && orderDto.getUserId() > 0){
            user = userService.getUserId(userDto);
        }

        for (int i = 0; i < orderItems.size() ; i++) {
            JSONObject orderItemDto = (JSONObject) orderItems.get(i);

            //주문메뉴 정보 조회
            Item item = Item.builder().itemId(Long.parseLong(orderItemDto.get("itemId").toString())).build();
            Item getItem = itemService.getItem(item);

            //주문 상품 생성
            OrderItem orderItem = OrderItem.createOrderItem(getItem,
                    Integer.parseInt(orderItemDto.get("itemPrice").toString()),
                    Integer.parseInt(orderItemDto.get("itemPriceSum").toString()),
                    Integer.parseInt(orderItemDto.get("itemCount").toString()));

            //주문 저장
            if(orderDto.getUserId() == null || orderDto.getUserId() == 0){
                user = Users.builder().id(0L).build();
            }
            Order order = Order.createOrder(user, orderItem);
            orderService.createOrder(order);
        }

        respMap.put("order", orderDto);

        return ResponseUtils.response(respMap);
    }

    @PostMapping("/user/order/check")
    public ResponseEntity<ResponseVo> checkOrder(@RequestParam Boolean orderState, @RequestParam Long orderId) {
        Map<String,Object> respMap = new HashMap<String, Object>();

        Order order = orderService.getOrder(orderId);

        order.checkOrder(orderState);

        OrderDto orderDto =  OrderDto.builder()
                .orderId(order.getId())
                .orderState(orderState)
                .build();

        respMap.put("order", orderDto);

        return ResponseUtils.response(respMap);
    }
}
