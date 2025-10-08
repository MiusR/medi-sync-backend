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
    private String email;
    private String phoneNumber;


    public PatientDTO(String name, String familyName, String roomNumber, String bedNumber, String dateOfBirth, String state, String phoneNumber, String email) {
        this.name = name;
        this.familyName = familyName;
        this.phoneNumber = phoneNumber;
        this.email = email;


        // Required fields
        if (name == null || name.isEmpty() || familyName == null || familyName.isEmpty()) {
            throw new ModelValidationException("Name and family name can NOT be empty fields.");
        }

        if( email == null || email.isEmpty() || phoneNumber == null || phoneNumber.isEmpty()) {
            throw new ModelValidationException("Email and phone number can NOT be empty fields.");
        }

        if(!email.contains("@") || !email.contains("."))
            throw new ModelValidationException("Email does not follow usual rules!");

        if(!phoneNumber.matches("[0-9]"))
            throw new ModelValidationException("Phone number can only contain numbers.");

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
