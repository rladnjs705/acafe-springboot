package com.javadeveloperzone.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name ="order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id")
    private Order order;

    private int itemPrice; //주문 가격
    private int itemPriceSum; //주문 총 가격
    private int itemCount; //주문 수량

    public static OrderItem createOrderItem(Item item, int itemPrice, int itemPriceSum, int itemCount) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setItemPrice(itemPrice);
        orderItem.setItemPriceSum(itemPriceSum);
        orderItem.setItemCount(itemCount);
        return orderItem;
    }
}
