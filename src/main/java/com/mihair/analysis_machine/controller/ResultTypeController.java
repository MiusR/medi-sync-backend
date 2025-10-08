package com.mihair.analysis_machine.controller;

import com.mihair.analysis_machine.model.util.TestType;
import com.mihair.analysis_machine.model.util.Versioned;
import com.mihair.analysis_machine.service.TestTypeService;
import com.mihair.analysis_machine.service.exception.ValueNotFound;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/result-types")
public class ResultTypeController extends VersionedDataController {

    private final TestTypeService service;

    public ResultTypeController(TestTypeService service) {
        super("1.3");
        this.service = service;
    }

    // TODO : make versioned pull from a version table in data base

    @GetMapping("/all")
    public ResponseEntity<Versioned<List<TestType>>> getAllTestTypes() {
        return ResponseEntity.ok(new Versioned<>(this.version, "predefined_tests", service.getTestTypes()));
    }

    @GetMapping
    public ResponseEntity<Versioned<TestType>> getAllTestTypes(@RequestParam String testName) {
        try {
            return ResponseEntity.ok(new Versioned<>(this.version, "predefined_test", service.getTestByName(testName)));
        }catch (ValueNotFound e) {
            return ResponseEntity.notFound().build()  ;
        }
    }
}
