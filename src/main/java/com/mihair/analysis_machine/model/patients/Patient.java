package com.mihair.analysis_machine.model.patients;

import com.mihair.analysis_machine.model.patients.DTO.PatientDTO;
import jakarta.persistence.*;

import java.time.LocalDate;

// Model of Patient
@Entity
@Table(name="active_patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long UID;
    private String name;
    private String familyName;
    private String roomNumber;
    private String bedNumber;
    @Column(name = "dob")
    private LocalDate dateOfBirth;
    @Enumerated(EnumType.STRING)
    private PatientStates state;
    private String email;
    private String phoneNumber;


    public Patient(String name, String familyName, LocalDate dateOfBirth, String roomNumber, String bedNumber, PatientStates state, String phoneNumber, String email){
        this(new PatientDTO(name,familyName,roomNumber, bedNumber,dateOfBirth.toString(), state.name(), phoneNumber, email));
    }

    public Patient(PatientDTO dto) {
        this.familyName = dto.getFamilyName();
        this.name = dto.getName();

        if(dto.getDateOfBirth() == null || dto.getDateOfBirth().isEmpty())
            this.dateOfBirth = null;
        else
            this.dateOfBirth = LocalDate.parse(dto.getDateOfBirth());

        this.bedNumber = dto.getBedNumber();
        this.roomNumber = dto.getRoomNumber();

        this.state = PatientStates.valueOf(dto.getState());

        this.email = dto.getEmail();
        this.phoneNumber = dto.getPhoneNumber();
    }

    public Patient() {

    }

    public Long getUID() {
        return UID;
    }

    public void setUID(Long id) {
        this.UID = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(String bedNumber) {
        this.bedNumber = bedNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public PatientStates getState() {
        return state;
    }

    public void setState(PatientStates state) {
        this.state = state;
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
