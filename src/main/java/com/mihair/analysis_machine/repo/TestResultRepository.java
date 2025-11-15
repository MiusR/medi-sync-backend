package com.mihair.analysis_machine.repo;

import com.mihair.analysis_machine.model.patients.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface TestResultRepository extends JpaRepository<TestResult, Long> {
    public List<TestResult> findByTimeTaken(Date timeTaken);
    public List<TestResult> getAllByPatientUID(Long UID);
}
