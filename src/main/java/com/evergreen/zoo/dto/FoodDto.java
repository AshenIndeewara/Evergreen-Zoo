package com.evergreen.zoo.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class FoodDto {
    private String foodID;
    private String name;
    private int qtyOnHand;
    private int minQty;
    private String supplier;
    private String description;
    private Double price;
}
