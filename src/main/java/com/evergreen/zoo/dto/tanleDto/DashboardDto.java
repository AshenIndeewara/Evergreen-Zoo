package com.evergreen.zoo.dto.tanleDto;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DashboardDto {
    private String species;
    private int count;
    private String status;
}
