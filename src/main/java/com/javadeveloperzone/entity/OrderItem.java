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

    private Integer itemPrice; //주문 가격
    private Integer itemPriceSum; //주문 총 가격
    private Integer itemCount; //주문 수량
    private Integer shot; //샷 추가
    private String light; //연하게 옵션(100:연하게, 200:보통, 300:진하게)

    public static OrderItem createOrderItem(Item item, Integer itemPrice, Integer itemPriceSum, Integer itemCount, Integer shot, String light) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setItemPrice(itemPrice);
        orderItem.setItemPriceSum(itemPriceSum);
        orderItem.setItemCount(itemCount);
        orderItem.setShot(shot);
        orderItem.setLight(light);
        return orderItem;
    }
}
