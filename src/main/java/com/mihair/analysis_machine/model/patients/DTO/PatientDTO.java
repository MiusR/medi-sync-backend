package com.mihair.analysis_machine.model.patients.DTO;

import com.mihair.analysis_machine.model.patients.PatientStates;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class PatientDTO {
    private String name;
    private String familyName;
    private String roomNumber, bedNumber;
    private String dateOfBirth;
    private String state;


    public PatientDTO(String name, String familyName, String roomNumber, String bedNumber, String dateOfBirth, String state) {
        this.name = name;
        this.familyName = familyName;

        // Incomplete data sanitization
        if (name == null || name.isEmpty()) {
            this.name = "John";
            if (familyName == null || familyName.isEmpty())
                this.familyName = "Doe";
        }

        // Incomplete or missing room/bed number sanitation
        this.roomNumber = roomNumber;
        this.bedNumber = bedNumber;
        if(this.roomNumber == null || this.roomNumber.isEmpty())
            this.roomNumber = "-";
        if(this.bedNumber == null || this.bedNumber.isEmpty())
            this.bedNumber = "-";


        // Date of birth is set to - in case invalid or missing
        this.dateOfBirth = dateOfBirth;
        try {
            if(dateOfBirth == null || dateOfBirth.isEmpty())
                throw new DateTimeParseException("Internal, should not surface", "", 0);
            LocalDate.parse(dateOfBirth);
        }catch (DateTimeParseException e) {
            this.dateOfBirth = "";
        }


        // State is set to NO_STATE in case of invalid or missing
        if ( state != null ) {
            try {
                PatientStates.valueOf(state.toUpperCase());
                this.state = state;
            } catch( IllegalArgumentException e){
                this.state = "NO_STATE";
            }
        }else
            this.state = "NO_STATE";

    }

    public String getName() {
        return name;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getBedNumber() {
        return bedNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getState() {
        return state;
    }
}
