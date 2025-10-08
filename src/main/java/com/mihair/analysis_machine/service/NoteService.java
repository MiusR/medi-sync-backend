package com.mihair.analysis_machine.service;

import com.mihair.analysis_machine.model.patients.DTO.NoteDTO;
import com.mihair.analysis_machine.model.patients.Note;
import com.mihair.analysis_machine.repo.NoteRepository;
import jakarta.annotation.Nonnull;
import jakarta.transaction.Transactional;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class NoteService {

    // TODO receive only encrypted data and decrypt

    private NoteRepository repo;

    private PatientCryptographyService cryptographyService;

    public NoteService(NoteRepository repo, PatientCryptographyService cryptographyService) {
        this.repo = repo;
        this.cryptographyService = cryptographyService;
    }

    /*
       Makes an unencrypted Note and saves it's encrypted version into the DB. Requires keyId to match the private key to the encrypted
    */
    @Transactional
    public Long createNote(NoteDTO noteDTO, String transmissionKeyID) {
        Note note = new Note(noteDTO.getPatientUID(), noteDTO.getTimeTaken(),"","");

        try {
            note.setTitle(cryptographyService.encrypt(noteDTO.getTitle(), "patient-" + note.getPatientUID()));
            note.setInfo(cryptographyService.encrypt(noteDTO.getInfo(), "patient-" + note.getPatientUID()));
            repo.save(note);
        } catch (Exception e) {
            throw new RuntimeException("Failed to encrypt the note data. Aborted operation.");
        }

        return note.getUID() ;
    }

    @Transactional
    public Boolean deleteNote(@NonNull Long id) {
        if(repo.findById(id).isEmpty())
            return false;
        repo.deleteById(id);
        return true;
    }

    public void deleteNotesOfPatient(@Nonnull Long patientId) {
        List<Note> notes = repo.getAllByPatientUID(patientId);
        repo.deleteAll(notes);
    }

    @Transactional
    public List<Note> getNotes(Long id) {
        List<Note> decryptedNotes = repo.getAllByPatientUID(id);
        List<Note> notes = new java.util.ArrayList<>(Collections.emptyList());
        decryptedNotes.forEach(note -> {
            try {
                Note copyNote = new Note(note.getPatientUID(), note.getTimeTaken(), "", "");
                copyNote.setTitle(cryptographyService.decrypt(note.getTitle(), "patient-" + note.getPatientUID()));
                copyNote.setInfo(cryptographyService.decrypt(note.getInfo(), "patient-" + note.getPatientUID()));
                copyNote.setUID(note.getUID());
                notes.add(copyNote);
            } catch (Exception e) {
                throw new RuntimeException("Failed to decrypt the note data. Aborted operation.");
            }
        });
        return notes;
    }
}
