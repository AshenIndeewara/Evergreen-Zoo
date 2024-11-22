package com.evergreen.zoo.dto.reportDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AnimalReportDto {
    private int animalID;
    private String name;
    private String species;
    private String diet;
    private int age;
}
