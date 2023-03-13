package com.javadeveloperzone.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderStreamDto {
    private Long orderId;
    private Boolean orderState;
    private Integer orderPriceSum;
    private Integer orderCount;
    private Integer orderNumber;
    private Integer shot;
    private String light;

    private String coffeeType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createDate;

    private List<ItemDto> item;

    @Builder
    public OrderStreamDto(Long orderId, Boolean orderState, Integer orderPriceSum, Integer orderCount, LocalDateTime createDate, List<ItemDto> item, Integer orderNumber, Integer shot, String light, String coffeeType) {
        this.orderPriceSum = orderPriceSum;
        this.orderCount = orderCount;
        this.orderId = orderId;
        this.orderState = orderState;
        this.createDate = createDate;
        this.item = item;
        this.orderNumber = orderNumber;
        this.shot = shot;
        this.light = light;
        this.coffeeType = coffeeType;
    }
}
