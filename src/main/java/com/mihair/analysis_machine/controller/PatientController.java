package com.mihair.analysis_machine.controller;

import com.mihair.analysis_machine.model.patients.DTO.PatientDTO;
import com.mihair.analysis_machine.model.patients.DTO.PatientIdDTO;
import com.mihair.analysis_machine.model.patients.Patient;
import com.mihair.analysis_machine.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService service;
    Logger logger = LoggerFactory.getLogger(PatientController.class.getName());


    public PatientController(PatientService service) {
        this.service = service;
    }

    @PostMapping("/add-patient")
    public ResponseEntity<String> createPatient(@RequestBody PatientDTO dto){
        Patient p = this.service.createPatient(dto);
        logger.atInfo().log("Added patient with id: " + p.getId());
        return ResponseEntity.ok("Patient {"+ p.getId() +"} added successfully");
    }

    @GetMapping("/patients")
    public ResponseEntity<List<PatientDTO>> createPatient() {
        return ResponseEntity.ok(service.getPatients());
    }

    @GetMapping("/patients/{id}")
    public ResponseEntity<PatientDTO> getPatient(@RequestParam Long patientID) {
        Optional<Patient> patientOptional = service.getPatient(patientID);
        if(patientOptional.isEmpty())
            return ResponseEntity.notFound().build();
        Patient patient = patientOptional.get();
        return ResponseEntity.ok(new PatientDTO(patient.getName(), patient.getFamilyName(), patient.getRoomNumber(), patient.getBedNumber(), patient.getDateOfBirth().toString(), patient.getState().name()));
    }

    @DeleteMapping("/remove-patient")
    public ResponseEntity<String> forgetPatient(@RequestBody PatientIdDTO patientId) {
        long validRemovals = patientId.getPatientId().stream().filter(id -> this.service.getPatient(id).isPresent()).count();
        if (validRemovals == patientId.getPatientId().size()) {
            patientId.getPatientId().forEach(this.service::forgetPatient);
            return ResponseEntity.ok("Patient {"+ patientId.getPatientId() +"} has been deleted successfully!");
        }
        return ResponseEntity.notFound().build();
    }
}
