package com.mihair.analysis_machine.service;

import com.mihair.analysis_machine.model.patients.DTO.PatientDTO;
import com.mihair.analysis_machine.model.patients.Patient;
import com.mihair.analysis_machine.repo.PatientRepository;
import com.mihair.analysis_machine.service.exception.CryptoClientCreationException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PatientService {
    private final PatientRepository repo;

    private final PatientCryptographyService cryptographyService;

    public PatientService(PatientCryptographyService cryptographyService, PatientRepository patientRepository) {
        this.repo = patientRepository;
        this.cryptographyService = cryptographyService;
    }

    @Transactional
    public Patient createPatient(PatientDTO dto) {
        Patient saved = repo.save(new Patient(dto));
        String keyName = "patient-" + saved.getId();
        try {
            cryptographyService.getClientOrCreateByKeyName(keyName); // Generate and cache keyName
        } catch (Exception e) {
            repo.delete(saved);  // Remove created patient and abort
            throw new CryptoClientCreationException(e.getMessage());
        }
        return saved;
    }

    public List<PatientDTO> getPatients(){
        ArrayList<PatientDTO> patients = new ArrayList<>();
        repo.findAll().forEach(patient -> patients.add(new PatientDTO(patient.getName(),patient.getFamilyName(),patient.getRoomNumber(),patient.getBedNumber(), patient.getDateOfBirth().toString(), patient.getState().toString())));
        return patients;
    }


    public Optional<Patient> getPatient(Long id) {
        return repo.findById(id);
    }


    public boolean forgetPatient(Long id) {
        Optional<Patient> option = repo.findById(id);

        // Found patient with specified ID
        if (option.isPresent()) {
            cryptographyService.forgetClient("patient-" + id); // Forget encryption keys
            repo.delete(option.get());
            return true;
            // TODO : sync deletion with other repositories
        }

        return false;
    }
}
