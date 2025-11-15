package com.mihair.analysis_machine.service;

import com.mihair.analysis_machine.model.patients.DTO.TestResultDTO;
import com.mihair.analysis_machine.model.patients.TestResult;
import com.mihair.analysis_machine.repo.TestResultRepository;
import jakarta.annotation.Nonnull;
import jakarta.transaction.Transactional;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class TestResultService {

    private final TestResultRepository repo;

    private final PatientCryptographyService cryptographyService;

    public TestResultService(TestResultRepository repo, PatientCryptographyService cryptographyService) {
        this.repo = repo;
        this.cryptographyService = cryptographyService;
    }


    @Transactional
    public Long createTestResult(TestResultDTO resultDTO, String transmissionKeyID) {
        TestResult testResult = new TestResult(resultDTO.getPatientUID(), null, resultDTO.getTimeTaken(), null,null);
        try {
            testResult.setTestName(cryptographyService.encrypt(resultDTO.getTestName(), "patient-" + testResult.getPatientUID()));
            testResult.setUnit(cryptographyService.encrypt(resultDTO.getUnit(), "patient-" + testResult.getPatientUID()));
            testResult.setValue(cryptographyService.encrypt(resultDTO.getValue(), "patient-" + testResult.getPatientUID()));
            repo.save(testResult);
        } catch (Exception e) {
            throw new RuntimeException("Failed to encrypt the note data. Aborted operation.");
        }

        return testResult.getUID() ;
    }

    @Transactional
    public Boolean deleteResult(@NonNull Long UID) {
        if(repo.findById(UID).isEmpty())
            return false;
        repo.deleteById(UID);
        return true;
    }

    public void deleteResultsOfPatient(@Nonnull Long patientUID) {
        List<TestResult> notes = repo.getAllByPatientUID(patientUID);
        repo.deleteAll(notes);
    }

    @Transactional
    public List<TestResult> getResults(Long patientUID) {
        List<TestResult> decryptedResults = repo.getAllByPatientUID(patientUID);
        List<TestResult> testResults = new java.util.ArrayList<>(Collections.emptyList());
        decryptedResults.forEach(result -> {
            try {
                TestResult copyTestResult = new TestResult();
                copyTestResult.setTestName(cryptographyService.decrypt(result.getTestName(), "patient-" + result.getPatientUID()));
                copyTestResult.setUnit(cryptographyService.decrypt(result.getUnit(), "patient-" + result.getPatientUID()));
                copyTestResult.setValue(cryptographyService.decrypt(result.getValue(), "patient-" + result.getPatientUID()));
                copyTestResult.setUID(result.getUID());
                copyTestResult.setTimeTaken(result.getTimeTaken());
                copyTestResult.setPatientUID(result.getPatientUID());
                testResults.add(copyTestResult);
            } catch (Exception e) {
                throw new RuntimeException("Failed to decrypt the result data. Aborted operation.");
            }
        });
        return testResults;
    }
}
