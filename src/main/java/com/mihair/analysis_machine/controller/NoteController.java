package com.mihair.analysis_machine.controller;

import com.mihair.analysis_machine.model.patients.DTO.NoteDTO;
import com.mihair.analysis_machine.model.patients.Note;
import com.mihair.analysis_machine.service.NoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteService service;
    Logger logger = LoggerFactory.getLogger(NoteController.class.getName());

    public NoteController(NoteService service) {
        this.service = service;
    }

    @PostMapping("/add-note")
    public ResponseEntity<String> createNote(@RequestBody NoteDTO dto){
        try {
            // TODO change from null in case a protocol actually manifests itself in brain :)
            Long id = this.service.createNote(dto, null);
            logger.atInfo().log("Added note with id: " + id);
            return ResponseEntity.ok("Note {"+ id +"} added successfully");
        } catch (Exception e) {
            logger.atInfo().log("FAILED to add note with title: " + dto.getTitle() + " " + dto.getTimeTaken().toString());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/remove-note")
    public ResponseEntity<String> deleteNote(@RequestParam String noteId) {
        if(this.service.deleteNote(Long.valueOf(noteId))) {
            return ResponseEntity.ok("Note with id " + noteId + "has been removed.");
        }
        return ResponseEntity.notFound().build();
    }


    @GetMapping
    public ResponseEntity<List<Note>> getNotes(@RequestParam String patientId) {
        List<Note> notesList = service.getNotes(Long.valueOf(patientId));
        if(notesList.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(notesList);
    }
}
