package com.javadeveloperzone.dto;

import com.javadeveloperzone.entity.Category;
import com.javadeveloperzone.entity.Item;
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
    private Integer itemPrice;
    //@NotBlank(message = "이미지를 선택해 주세요.")
    private String itemImage;
    //@NotNull(message = "카테고리를 선택해 주세요.")
    @Positive // 양수만 허용
    private Long categoryId;
    private Long itemId;



    @Builder
    public ItemDto(Item item) {
        this.itemId = item.getId();
        this.itemName = item.getItemName();
        this.itemPrice = item.getItemPrice();
        this.itemImage = item.getItemImage();
    }
}
