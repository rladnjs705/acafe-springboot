package com.javadeveloperzone.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderItemDto {

    private Long itemId;
    private String itemName;
    private Integer itemPrice;
    private Integer itemPriceSum;
    private Integer itemCount;
}
