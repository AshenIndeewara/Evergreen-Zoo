package com.evergreen.zoo.dto.tanleDto;

import javafx.scene.image.ImageView;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StockDto {
    private String supplier;
    private String item;
    private int qty;
    private ImageView typeImage;
}
