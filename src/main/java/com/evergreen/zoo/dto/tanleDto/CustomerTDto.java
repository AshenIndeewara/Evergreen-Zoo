package com.evergreen.zoo.dto.tanleDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerTDto {
    private int customerID;
    private String name;
    private String email;
    private String phone;
    private String date;
}
