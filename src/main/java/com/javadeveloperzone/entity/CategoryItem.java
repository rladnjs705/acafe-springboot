package com.javadeveloperzone.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class CategoryItem {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="category_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
