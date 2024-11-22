package com.evergreen.zoo.dto.tanleDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SpeciesDto {
    private String speciesID;
    private String speciesName;
    private String speciesDiet;
    private String speciesStatus;
    private int speciesCount;
}
