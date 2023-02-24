package com.javadeveloperzone.controller.rest;

import com.javadeveloperzone.config.exception.ResultCodeType;
import com.javadeveloperzone.config.utils.ResponseUtils;
import com.javadeveloperzone.dto.ItemDto;
import com.javadeveloperzone.dto.OrderDto;
import com.javadeveloperzone.dto.OrderStreamDto;
import com.javadeveloperzone.dto.UserDto;
import com.javadeveloperzone.entity.Item;
import com.javadeveloperzone.entity.Order;
import com.javadeveloperzone.entity.OrderItem;
import com.javadeveloperzone.entity.Users;
import com.javadeveloperzone.model.ResponseVo;
import com.javadeveloperzone.repository.OrderRepository;
import com.javadeveloperzone.service.ItemService;
import com.javadeveloperzone.service.OrderService;
import com.javadeveloperzone.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@RestController
public class OrderRestController {
    private final OrderRepository orderRepository;
    private final OrderService orderService;
    private final UserService userService;
    private final ItemService itemService;

    @GetMapping(value = "/admin/orders/subscribe")
    public Mono<List<OrderStreamDto>> orderSubscribe() {
        // 2초 간격으로 새로운 주문 목록을 조회하며 반환
        return Flux.interval(Duration.ofSeconds(2))
                .flatMap(interval -> Flux.fromIterable(orderService.getOrderList()))
                .map(order -> {
                    // Order 정보 추출
                    Long orderId = order.getId();
                    Boolean orderState = false;
                    if (order.getStatus().getValue().equals("Y")) {
                        orderState = false;
                    } else {
                        orderState = true;
                    }
                    Integer orderNumber = order.getOrderNumber();
                    Integer orderCount = order.getOrderCount();
                    Integer orderPriceSum = order.getOrderPriceSum();
                    LocalDateTime createDate = order.getCreateDate();

                    // OrderItem 정보 추출
                    List<OrderItem> orderItems = order.getOrderItems();
                    List<ItemDto> itemDtoList = new ArrayList<>();
                    for (OrderItem orderItem : orderItems) {
                        ItemDto item = new ItemDto();
                        item.setItemName(orderItem.getItem().getItemName());
                        item.setItemPrice(orderItem.getItem().getItemPrice());
                        item.setItemCount(orderItem.getItemCount());
                        item.setItemPriceSum(orderItem.getItemPriceSum());
                        itemDtoList.add(item);
                    }

                    // OrderStreamDto 객체 생성 및 매핑
                    OrderStreamDto orderStreamDto = OrderStreamDto.builder()
                            .orderId(orderId)
                            .orderState(orderState)
                            .orderPriceSum(orderPriceSum)
                            .orderCount(orderCount)
                            .createDate(createDate)
                            .item(itemDtoList)
                            .orderNumber(orderNumber)
                            .build();
                    return orderStreamDto;
                })
                .distinct()
                .bufferTimeout(50, Duration.ofSeconds(1)) // 최대 50개의 데이터를 1초 동안 버퍼링
                .next(); // 버퍼링된 데이터를 Mono로 변환하여 반환
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

        List<OrderItem> orderItemList = new ArrayList<>();

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

            orderItemList.add(orderItem);
        }

        //날짜가 변경되면 주문번호 초기화
        Integer count =  orderRepository.countByOrderDate(LocalDate.now());
        Integer orderNumber = (count > 0) ? count + 1 : 1;

        Order order = Order.createOrder(user, orderNumber, orderDto.getOrderPriceSum(), orderDto.getOrderCount(), orderItemList);
        orderService.createOrder(order);

        orderDto.setOrderNumber(orderNumber);

        respMap.put("order", orderDto);

        return ResponseUtils.response(respMap);
    }

    @PostMapping("/admin/order/check")
    @Transactional
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
