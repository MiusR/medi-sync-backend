package com.mihair.analysis_machine.repo;

import com.mihair.analysis_machine.model.patients.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestResultRepository extends JpaRepository<TestResult, Long> {

}
