/*package com.mihair.analysis_machine.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Date;
@Document
public class Note {
    @Id
    private ObjectId UID;
    private ObjectId patientUID;
    private Date timeTaken;
    private String info;


    public Note(ObjectId patientUID, Date timeTaken, String info) {
        this.patientUID = patientUID;
        this.timeTaken = timeTaken;
        this.info = info;
        this.UID = new ObjectId();
    }

    public Note(ObjectId patientUID, String info) {
        this(patientUID, Date.from(Instant.ofEpochMilli(System.currentTimeMillis())), info);
    }

    public ObjectId getUID() {
        return UID;
    }


    public ObjectId getPatientUID() {
        return patientUID;
    }

    public void setPatientUID(ObjectId patientUID) {
        this.patientUID = patientUID;
    }

    public Date getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(Date timeTaken) {
        this.timeTaken = timeTaken;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
*/