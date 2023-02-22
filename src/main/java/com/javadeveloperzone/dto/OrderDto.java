package com.javadeveloperzone.dto;

import com.javadeveloperzone.domain.Role;
import com.javadeveloperzone.entity.Item;
import com.javadeveloperzone.entity.OrderItem;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderDto {

    @Positive
    private Long orderId;

    private Boolean orderState;

    @Positive
    private Long userId;

    @PositiveOrZero(message = "메뉴가격을 제대로 입력해 주세요.") // 양수와0만 허용
    private Integer orderPriceSum = 0;

    @NotNull(message = "잘못된 주문 수입니다")
    @Positive // 양수만 허용
    private Long orderCount;

    @Builder
    public OrderDto(Long orderId, boolean orderState, Integer orderPriceSum, Long orderCount) {
        this.orderPriceSum = orderPriceSum;
        this.orderCount = orderCount;
        this.orderId = orderId;
        this.orderState = orderState;
    }
}
