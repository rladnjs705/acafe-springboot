package com.javadeveloperzone.entity;

import com.javadeveloperzone.domain.Role;
import com.javadeveloperzone.dto.ItemDto;
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
@Table(name = "Item")
public class Item {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long itemId;
    private String itemName;
    private Integer itemPrice;
    private String itemImage;

    @Column
    private Integer itemOrder = 0;

    @Column(columnDefinition = "CHAR(1) DEFAULT 'Y' CHECK (display_yn IN ('Y', 'N'))")
    private String displayYn;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @CreationTimestamp // INSERT 쿼리 시 현재 시간으로 생성
    private LocalDateTime createDate= LocalDateTime.now();

    @UpdateTimestamp // UPDATE 시 자동으로 값을 채워줌
    private LocalDateTime updatedDate = LocalDateTime.now();

    @Builder
    public Item(String itemName, Long itemId, Integer itemPrice, String itemImage, String displayYn, Integer itemOrder, Category category) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemImage = itemImage;
        this.displayYn = displayYn;
        this.itemOrder = itemOrder;
        this.category = category;
    }

    public void updateItem(Item item) {
        this.itemName = item.getItemName();
        this.itemPrice = item.getItemPrice();
        this.itemImage = item.getItemImage();
        this.displayYn = item.getDisplayYn();
        this.itemOrder = item.getItemOrder();
        this.category = item.getCategory();
    }
}
