package com.evergreen.zoo.dto.reportDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class TicketReportDto {
    private int ticketID;
    private String ticketType;
    private int qty;
    private double price;
    private double totalPrice;
}
