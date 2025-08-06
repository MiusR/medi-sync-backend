package com.mihair.analysis_machine.service;

import com.mihair.analysis_machine.model.staff.DTO.UserDTO;
import com.mihair.analysis_machine.model.staff.User;
import com.mihair.analysis_machine.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository repository) {
        this.userRepository = repository;
    }

    public Optional<UserDTO> getUserDTO(String username) {
        User user = userRepository.getByUsername(username);
        if (user != null)
            return Optional.of(new UserDTO(user.getUsername(), user.getPassword(), user.getRole().name()));
        return Optional.empty();
    }

    public boolean checkEnabled(String username) {
        User user = userRepository.getByUsername(username);
        return user != null && user.getEnabled();
    }
}
