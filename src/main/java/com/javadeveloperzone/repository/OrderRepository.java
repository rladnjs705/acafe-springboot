package com.javadeveloperzone.repository;

import com.javadeveloperzone.entity.Category;
import com.javadeveloperzone.entity.Item;
import com.javadeveloperzone.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Integer countByOrderDate(LocalDate orderDate);

    @EntityGraph(value = "Order.withUserAndOrderItems",attributePaths = {"users", "orderItems", "orderItems.order", "orderItems.item"})
    @Query("SELECT o FROM Order o WHERE o.status = 'ORDER'")
    List<Order> findAllWithUserAndOrderItems();
}
