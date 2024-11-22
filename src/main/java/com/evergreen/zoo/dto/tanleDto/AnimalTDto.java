package com.evergreen.zoo.dto.tanleDto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AnimalTDto {
    private String animalID;
    private String name;
    private String gender;
    private String species;
    private String diet;
    private String health;
    private String healthDescription;
    private int age;
}
