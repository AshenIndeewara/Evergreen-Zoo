package com.evergreen.zoo.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TicketDto {
    private double total;
    private String paymentMethod;
    private int adult;
    private int child;
    private int foreigner;
    private int foreignerChild;
    private int student;
}
