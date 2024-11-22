package com.evergreen.zoo.dto.tanleDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class TicketTDto {
    private int ticketID;
    private int visitorID;
    private String ticketType;
    private int qty;

}
