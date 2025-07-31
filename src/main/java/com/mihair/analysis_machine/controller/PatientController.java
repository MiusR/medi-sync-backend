package com.mihair.analysis_machine.controller;

import com.mihair.analysis_machine.model.DTO.PatientDTO;
import com.mihair.analysis_machine.model.DTO.PatientIdDTO;
import com.mihair.analysis_machine.model.Patient;
import com.mihair.analysis_machine.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.MissingResourceException;

@RestController
@RequestMapping("/patients")
public class PatientController {
    // TODO : add end to end encryption with rotating keys - asymmetric ones


    private final PatientService service;


    public PatientController(PatientService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> createPatient(@RequestBody PatientDTO dto){
        Patient p = this.service.createPatient(dto);
        return ResponseEntity.ok("Patient {"+ p.getId() +"} added successfully");
    }

    // TODO : change this into a global ControllerAdvice and make the error throw MissingPatient
    @ExceptionHandler(MissingResourceException.class)
    public ResponseEntity<?> handleServiceExceptions() {
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping
    public ResponseEntity<String> forgetPatient(@RequestBody PatientIdDTO patientId) {
        if (patientId.getPatientId() != null && this.service.forgetPatient(patientId.getPatientId())) {
            return ResponseEntity.ok("Patient {"+ patientId +"} has been deleted successfully!");
        }
        return ResponseEntity.notFound().build();
    }
}
