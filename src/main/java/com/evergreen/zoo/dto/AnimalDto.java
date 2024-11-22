package com.evergreen.zoo.dto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AnimalDto {
    private String name;
    private String species;
    private String health;
    private String healthDescription = "None";
    private String gender;
    private int age;

    public void clearAll(){
        this.name = "";
        this.species = "";
        this.health = "";
        this.healthDescription = "None";
        this.gender = "";
        this.age = 0;
    }
}
