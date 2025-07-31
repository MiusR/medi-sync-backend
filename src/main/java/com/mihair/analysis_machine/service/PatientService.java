package com.mihair.analysis_machine.service;

import com.mihair.analysis_machine.model.DTO.PatientDTO;
import com.mihair.analysis_machine.model.Patient;
import com.mihair.analysis_machine.repo.PatientRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.MissingResourceException;
import java.util.Optional;

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
        cryptographyService.getClientByKeyName(keyName); // Generate and cache keyName
        return saved;
    }

    public Patient getPatient(Long id) throws MissingResourceException {
        Optional<Patient> patient = repo.findById(id);
        if(patient.isEmpty())
            throw new MissingResourceException("Could not find patient. Maybe they were deleted!", Patient.class.getName(), id.toString());
        return patient.get();
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
