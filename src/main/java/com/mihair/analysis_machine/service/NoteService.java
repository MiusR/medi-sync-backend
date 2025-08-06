package com.mihair.analysis_machine.service;

import com.mihair.analysis_machine.model.patients.DTO.NoteDTO;
import com.mihair.analysis_machine.model.patients.Note;
import com.mihair.analysis_machine.repo.NoteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

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
    public Note createNote(NoteDTO noteDTO, String transmissionKeyID) {
        Note note = new Note(noteDTO.getPatientUID(), noteDTO.getTimeTaken(), "");

        // TODO : uncomment in prod after you have a client that actually encrypts the data
        // String decryptedInfo = cryptographyService.decrypt(noteDTO.getInfoENC(), "api-transmission-" + transmissionKeyID);
        String decryptedInfo = note.getInfo();

        if(decryptedInfo == null)
            return null; // Could not decrypt data

        note.setInfo(cryptographyService.encrypt(decryptedInfo, "patient-" + note.getPatientUID()));

        repo.save(note);

        return note ;
    }


}
