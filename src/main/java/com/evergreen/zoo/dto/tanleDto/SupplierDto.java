package com.evergreen.zoo.dto.tanleDto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class SupplierDto {
    private String supplierID;
    private String name;
    private String phone;
    private String email;
    private String address;
    private String description;

    public SupplierDto(String name, String phone, String email, String address, String description) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.description = description;
    }
}
