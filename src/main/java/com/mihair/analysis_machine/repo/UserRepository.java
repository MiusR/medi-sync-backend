package com.mihair.analysis_machine.repo;

import com.mihair.analysis_machine.model.staff.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    public User getByUsername(String username);
}
