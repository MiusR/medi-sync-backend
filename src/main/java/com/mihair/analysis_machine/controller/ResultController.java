package com.mihair.analysis_machine.controller;

import com.mihair.analysis_machine.model.patients.DTO.TestResultDTO;
import com.mihair.analysis_machine.model.patients.TestResult;
import com.mihair.analysis_machine.service.TestResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/results")
public class ResultController {

    private final TestResultService service;
    Logger logger = LoggerFactory.getLogger(ResultController.class.getName());

    public ResultController(TestResultService service) {
        this.service = service;
    }

    @PostMapping("/add-result")
    public ResponseEntity<String> createResult(@RequestBody TestResultDTO dto){
        try {
            // TODO change from null in case a protocol actually manifests itself in brain :)
            Long id = this.service.createTestResult(dto, null);
            logger.atInfo().log("Added result with id: " + id);
            return ResponseEntity.ok("Test {"+ id +"} added successfully");
        } catch (Exception e) {
            logger.atInfo().log("FAILED to add result with name: " + dto.getTestName() + " " + dto.getTimeTaken().toString());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/remove-result")
    public ResponseEntity<String> deleteResult(@RequestParam String resultId) {
        if(this.service.deleteResult(Long.valueOf(resultId))) {
            return ResponseEntity.ok("Result with id " + resultId + "has been removed.");
        }
        return ResponseEntity.notFound().build();
    }


    @GetMapping
    public ResponseEntity<List<TestResult>> getResults(@RequestParam String patientId) {
        List<TestResult> notesList = service.getResults(Long.valueOf(patientId));
        if(notesList.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(notesList);
    }
}
