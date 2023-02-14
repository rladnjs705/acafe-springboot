package com.javadeveloperzone.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="category_id")
    private Long id;
    private String categoryName;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    @CreationTimestamp // INSERT 쿼리 시 현재 시간으로 생성
    private LocalDateTime createDate= LocalDateTime.now();

    @UpdateTimestamp // UPDATE 시 자동으로 값을 채워줌
    private LocalDateTime updatedDate = LocalDateTime.now();

    @Builder
    public Category (String categoryName, Long categoryId) {
        this.id = categoryId;
        this.categoryName = categoryName;
    }

    public void updateCategory(Category category) {
        this.categoryName = category.getCategoryName();
    }
}
