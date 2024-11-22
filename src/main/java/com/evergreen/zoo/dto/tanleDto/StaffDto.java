package com.evergreen.zoo.dto.tanleDto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class StaffDto {
    private int staffID;
    private String staffName;
    private String staffRole;
    private String staffContact;
    private String staffEmail;
    private String staffAddress;

    public StaffDto(String staffName, String staffRole, String staffContact, String staffEmail) {
        this.staffName = staffName;
        this.staffRole = staffRole;
        this.staffContact = staffContact;
        this.staffEmail = staffEmail;
    }

    public StaffDto(int staffID,String staffName, String staffRole, String staffContact, String staffEmail) {
        this.staffID = staffID;
        this.staffName = staffName;
        this.staffRole = staffRole;
        this.staffContact = staffContact;
        this.staffEmail = staffEmail;
    }
}
