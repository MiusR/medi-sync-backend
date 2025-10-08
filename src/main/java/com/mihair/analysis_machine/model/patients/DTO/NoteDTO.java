package com.mihair.analysis_machine.model.patients.DTO;

import java.util.Date;

public class NoteDTO {
    private Long patientUID;
    private Date timeTaken;
    private String title;
    private String info;

    public NoteDTO(Long patientUID, Date timeTaken, String title, String info) {
        this.patientUID = patientUID;
        this.timeTaken = timeTaken;
        this.title = title;
        this.info = info;
    }

    public Long getPatientUID() {
        return patientUID;
    }

    public void setPatientUID(Long patientUID) {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
