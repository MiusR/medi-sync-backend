package com.mihair.analysis_machine.controller;

import com.mihair.analysis_machine.model.patients.DTO.PatientDTO;
import com.mihair.analysis_machine.model.patients.Patient;
import com.mihair.analysis_machine.service.PatientService;
import com.mihair.analysis_machine.service.exception.PatientCreationException;
import com.mihair.analysis_machine.service.exception.PatientModificationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// TODO : remove from here and move to the medi proxy

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
        try {
            Patient p = this.service.createPatient(dto);
            logger.atInfo().log("Added patient with id: " + p.getUID());
            return ResponseEntity.ok("Patient {"+ p.getUID() +"} added successfully");
        } catch (PatientCreationException e) {
            logger.atInfo().log("FAILED to add patient with name: " + dto.getName() + " " + dto.getFamilyName());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/update-patient")
    public ResponseEntity<Patient> updatePatient(@RequestParam String patientId, @RequestBody PatientDTO dto) {
        try {
            Patient p = service.updatePatient(Long.valueOf(patientId), dto);
            logger.atInfo().log("Updated patient with id: " + p.getUID());
            return ResponseEntity.ok(p);
        } catch (PatientModificationException e) {
            logger.atInfo().log("Failed to update patient with id: " + patientId);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Patient>> getAllPatients() {
        return ResponseEntity.ok(service.getPatients());
    }

    @GetMapping
    public ResponseEntity<Patient> getPatient(@RequestParam String patientId) {
        Optional<Patient> patientOptional = service.getPatient(Long.valueOf(patientId));
        if(patientOptional.isEmpty())
            return ResponseEntity.notFound().build();
        Patient patient = patientOptional.get();
        return ResponseEntity.ok(patient);
    }

    @DeleteMapping("/remove-patient")
    public ResponseEntity<String> forgetPatient(@RequestParam String patientId) {
        try {
            if (this.service.forgetPatient(Long.valueOf(patientId))) {
                logger.atInfo().log("Removed patient with id: " + patientId);
                return ResponseEntity.ok("Patient {" + patientId + "} has been deleted successfully!");
            }
            throw new RuntimeException("Patient does not exist!");
        } catch (Exception e) {
            logger.atInfo().log("Failed to remove requested patient : " + patientId);
            return ResponseEntity.notFound().build();
        }
    }
}
