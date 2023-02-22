package com.javadeveloperzone.repository;

import com.javadeveloperzone.entity.Category;
import com.javadeveloperzone.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
