package com.mihair.analysis_machine.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public class VersionedDataController {

    protected final String version;

    public VersionedDataController(String version){
        this.version= version;
    }

    @GetMapping("/version")
    public ResponseEntity<String> getVersion() {
        return ResponseEntity.ok(version);
    }


}
