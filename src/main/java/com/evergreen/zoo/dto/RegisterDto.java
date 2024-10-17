package com.evergreen.zoo.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {
    String username;
    String password;
    String name;
    String email;
    String phone;
    String address;
    int Role;
}
