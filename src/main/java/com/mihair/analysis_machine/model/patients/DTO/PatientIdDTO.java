package com.mihair.analysis_machine.model.patients.DTO;

import com.mihair.analysis_machine.model.patients.Patient;

import java.util.List;
import java.util.stream.Collectors;

public class PatientIdDTO {
    
    private final List<Long> patientId;
    
    public PatientIdDTO(List<Long> patientId) {
        this.patientId = patientId.stream().filter(id -> id > 0).collect(Collectors.toList());
    }

    public PatientIdDTO(Long patientId) {
        this(List.of(patientId));
    }

    public List<Long> getPatientId() {
        return patientId;
    }
}
