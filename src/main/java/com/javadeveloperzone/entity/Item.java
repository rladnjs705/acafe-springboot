package com.javadeveloperzone.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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
public class Item {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;
    private String itemName;
    private Integer itemPrice;
    private String itemImage;

    @OneToMany(mappedBy = "item")
    private List<CategoryItem> categories = new ArrayList<>();

    @CreationTimestamp // INSERT 쿼리 시 현재 시간으로 생성
    private LocalDateTime createDate= LocalDateTime.now();

    @UpdateTimestamp // UPDATE 시 자동으로 값을 채워줌
    private LocalDateTime updatedDate = LocalDateTime.now();
}
