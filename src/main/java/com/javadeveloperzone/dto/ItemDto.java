package com.javadeveloperzone.dto;

import com.javadeveloperzone.entity.Item;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemDto {

    @NotBlank(message = "메뉴명을 입력해 주세요.")
    private String itemName;

    @PositiveOrZero(message = "메뉴가격을 제대로 입력해 주세요.") // 양수와0만 허용
    private Integer itemPrice = 0;
    //@NotBlank(message = "이미지를 선택해 주세요.")
    private String itemImage;
    //@NotNull(message = "카테고리를 선택해 주세요.")
    @Positive // 양수만 허용
    private Long categoryId;
    private Long itemId;

    private String displayYn = "Y";
    private Integer itemPriceSum;
    private Integer itemCount;
    private String adminYn = "N";

    private Integer itemOrder;

    @Builder
    public ItemDto(Item item) {
        this.itemId = item.getItemId();
        this.itemName = item.getItemName();
        this.itemPrice = item.getItemPrice();
        this.itemImage = item.getItemImage();
        this.displayYn = item.getDisplayYn();
        this.itemOrder = item.getItemOrder();
        this.categoryId = item.getCategory().getId();
    }
    @QueryProjection
    public ItemDto(Long itemId, String itemName, Integer itemPrice, String itemImage, String displayYn, Integer itemOrder, Long categoryId){
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemImage = itemImage;
        this.displayYn = displayYn;
        this.categoryId = categoryId;
        this.itemOrder = itemOrder;
    }
}
