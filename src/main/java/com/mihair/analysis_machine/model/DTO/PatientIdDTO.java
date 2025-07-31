package com.mihair.analysis_machine.model.DTO;

public class PatientIdDTO {
    
    private final Long patientId;
    
    public PatientIdDTO(Long patientId) {
        Long patientId1;
        patientId1 = patientId;
        if(patientId1 < 0)
            patientId1 = null;
        this.patientId = patientId1;
    }

    public Long getPatientId() {
        return patientId;
    }
}
