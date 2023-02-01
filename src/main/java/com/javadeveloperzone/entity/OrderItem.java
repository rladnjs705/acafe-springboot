package com.javadeveloperzone.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
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
}
