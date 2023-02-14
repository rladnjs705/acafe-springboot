package com.javadeveloperzone.repository;

import com.javadeveloperzone.entity.Item;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Page<Item> findAll(Pageable pageable);
    @Query("SELECT i FROM Item i WHERE " +
            "(:itemName IS NULL OR i.itemName LIKE %:itemName%)")
    Page<Item> findAllByItemName(Pageable pageable, @Param("itemName") String itemName);
}
