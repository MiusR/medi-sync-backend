package com.mihair.analysis_machine.model.patients.DTO;

import java.util.Date;

public class NoteDTO {
    private Long patientUID;
    private Date timeTaken;
    private String infoENC;

    public NoteDTO(Long patientUID, Date timeTaken, String infoENC) {
        this.patientUID = patientUID;
        this.timeTaken = timeTaken;
        this.infoENC = infoENC;
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

    public String getInfoENC() {
        return infoENC;
    }

    public void setInfoENC(String infoENC) {
        this.infoENC = infoENC;
    }
}
