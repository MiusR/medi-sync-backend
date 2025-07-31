package com.mihair.analysis_machine.model;

import com.mihair.analysis_machine.model.DTO.PatientDTO;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

// Model of Patient
@Entity
@Table(name="active_patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String familyName;
    private String roomNumber;
    private String bedNumber;
    @Column(name = "dob")
    private LocalDate dateOfBirth;
    @Enumerated(EnumType.STRING)
    private PatientStates state;


    public Patient(String name, String familyName, LocalDate dateOfBirth, String roomNumber, String bedNumber, PatientStates state){
        this(new PatientDTO(name,familyName,roomNumber, bedNumber,dateOfBirth.toString(), state.name()));
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
    }

    public Patient() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return Objects.equals(id, patient.id) && Objects.equals(name, patient.name) && Objects.equals(familyName, patient.familyName) && Objects.equals(roomNumber, patient.roomNumber) && Objects.equals(bedNumber, patient.bedNumber) && Objects.equals(dateOfBirth, patient.dateOfBirth) && state == patient.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, familyName, roomNumber, bedNumber, dateOfBirth, state);
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", familyName='" + familyName + '\'' +
                ", roomNumber='" + roomNumber + '\'' +
                ", bedNumber='" + bedNumber + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", state=" + state +
                '}';
    }
}
