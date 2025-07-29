/*package com.mihair.analysis_machine.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// Model of Patient
@Document
public class Patient {
    @Id
    private ObjectId UID;
    private String name, familyName;
    private String roomNumber, bedNumber;
    private PatientStates state;
    private String bloodType;


    public Patient(String name, String family_name, String roomNumber, String bedNumber, PatientStates state, String bloodType){
        this.familyName = family_name;
        this.name = name;
        this.roomNumber = roomNumber;
        this.bedNumber = bedNumber;
        this.state = state;
        this.bloodType = bloodType;
        this.UID = new ObjectId();
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

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public PatientStates getState() {
        return state;
    }

    public void setState(PatientStates state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return String.format("{ id = %s  name = %s  family-name = %s  room-number = %s  bed-number = %s  blood-type = %s  status = %s }",UID ,name ,familyName ,roomNumber ,bedNumber ,bloodType ,state.name());
    }
}
*/