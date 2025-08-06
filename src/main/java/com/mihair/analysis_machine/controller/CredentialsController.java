package com.mihair.analysis_machine.controller;

import com.mihair.analysis_machine.model.staff.DTO.UserDTO;
import com.mihair.analysis_machine.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController("/api/auth")
public class CredentialsController {

    private UserService service;

    public CredentialsController(UserService service) {
        this.service = service;
    }

    @GetMapping("/login")
    public ResponseEntity<UserDTO> checkLogin(@RequestBody String username){
        Optional<UserDTO> dto = service.getUserDTO(username);
        if(dto.isEmpty() || ! service.checkEnabled(username))
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto.get());
    }

}
