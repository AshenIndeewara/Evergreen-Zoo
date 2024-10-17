package com.evergreen.zoo.dto;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ForgotDto {
    private String id;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private int OTP;

}
