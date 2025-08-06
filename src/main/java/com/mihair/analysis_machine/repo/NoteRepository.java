package com.mihair.analysis_machine.repo;

import com.mihair.analysis_machine.model.patients.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    public Note findByPatientUID(Long patientUID);
    public List<Note> findByTimeTaken(Date timeTaken);
}