package com.javadeveloperzone.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue
    @Column(name ="order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users users;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private Integer orderNumber;

    private Integer orderPriceSum;

    private Integer orderCount;

    @CreationTimestamp // INSERT 쿼리 시 현재 시간으로 생성
    private LocalDateTime createDate= LocalDateTime.now();

    @UpdateTimestamp // UPDATE 시 자동으로 값을 채워줌
    private LocalDateTime updatedDate = LocalDateTime.now();

    @CreatedDate
    private LocalDate orderDate = LocalDate.now();


    //==연관관계 메서드==//
    public void setUsers(Users users) {
        this.users = users;
        //users.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    //==생성 메서드==//
    public static Order createOrder(Users users, Integer orderNumber, Integer orderPriceSum, Integer orderCount, List<OrderItem> orderItems){
        Order order = new Order();
        order.setUsers(users);
        for(OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setCreateDate(LocalDateTime.now());
        order.setOrderNumber(orderNumber);
        order.setOrderPriceSum(orderPriceSum);
        order.setOrderCount(orderCount);
        return order;
    }

    //==비즈니스 로직==//
    /**
     * 주문 취소
     */
    public void cancel() {
        this.setStatus(OrderStatus.CANCEL);
    }

    public void checkOrder(boolean orderState){
        if(orderState == true){
            this.setStatus(OrderStatus.CANCEL);
        }else{
            this.setStatus(OrderStatus.ORDER);
        }
    }

}
