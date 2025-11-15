package com.mihair.analysis_machine.service;

import com.mihair.analysis_machine.model.patients.DTO.ModelValidationException;
import com.mihair.analysis_machine.model.patients.DTO.PatientDTO;
import com.mihair.analysis_machine.model.patients.Patient;
import com.mihair.analysis_machine.repo.PatientRepository;
import com.mihair.analysis_machine.service.exception.PatientCreationException;
import com.mihair.analysis_machine.service.exception.PatientModificationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {
    private final PatientRepository repo;
    private final NoteService noteService;
    private final TestResultService testResultService;

    private final PatientCryptographyService cryptographyService;

    public PatientService(PatientCryptographyService cryptographyService, PatientRepository patientRepository, NoteService noteService, TestResultService testResultService) {
        this.repo = patientRepository;
        this.cryptographyService = cryptographyService;
        this.noteService = noteService;
        this.testResultService = testResultService;
    }

    public Patient createPatient(PatientDTO dto) throws PatientCreationException {
        Patient saved;
        try {
            saved = repo.save(new Patient(dto));
        } catch (ModelValidationException e) {
            throw new PatientCreationException("Incomplete patient data: " + e.getMessage());
        }

        String keyName = "patient-" + saved.getUID();
        try {
            cryptographyService.getSecretOrCreateByName(keyName); // Generate and cache keyName
        } catch (Exception e) {
            repo.delete(saved);  // Remove created patient and abort
            throw new PatientCreationException(e.getMessage());
        }
        return saved;
    }

    public List<Patient> getPatients(){
        return repo.findAll();
    }


    public Optional<Patient> getPatient(Long id) {
        return repo.findById(id);
    }


    public boolean forgetPatient(Long id) {
        Optional<Patient> option = repo.findById(id);

        // Found patient with specified ID
        if (option.isPresent()) {
            cryptographyService.forgetSecret("patient-" + id); // Forget encryption keys
            repo.delete(option.get());
            noteService.deleteNotesOfPatient(id);
            testResultService.deleteResultsOfPatient(id);
            return true;
        }
        return false;
    }

    public Patient updatePatient(Long id, PatientDTO dto) throws PatientModificationException {
        if(getPatient(id).isEmpty())
            throw new PatientModificationException("Did not find specified patient.");

        Patient p = repo.getReferenceById(id);
        Patient updatedPatient = new Patient(dto);
        p.setBedNumber(updatedPatient.getBedNumber());
        p.setDateOfBirth(updatedPatient.getDateOfBirth());
        p.setFamilyName(updatedPatient.getFamilyName());
        p.setName(updatedPatient.getName());
        p.setState(updatedPatient.getState());
        p.setEmail(updatedPatient.getEmail());
        p.setPhoneNumber(updatedPatient.getPhoneNumber());
        repo.save(p);
        return updatedPatient;
    }
}
