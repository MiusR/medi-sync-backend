package com.mihair.analysis_machine.repo;

import com.mihair.analysis_machine.model.patients.Patient;
import com.mihair.analysis_machine.model.patients.PatientStates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    public Patient findByRoomNumberAndBedNumber(String roomNumber, String bedNumber);
    public List<Patient> getAllByRoomNumber(String roomNumber);
    public List<Patient> findAllByName(String name);
    public List<Patient> findAllByFamilyName(String familyName);
    public List<Patient> findAllByNameAndFamilyName(String name, String familyName);
    public List<Patient> findAllByState(PatientStates state);
}

