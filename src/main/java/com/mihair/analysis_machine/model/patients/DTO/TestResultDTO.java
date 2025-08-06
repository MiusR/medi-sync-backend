package com.mihair.analysis_machine.model.patients.DTO;

import java.util.Date;

public class TestResultDTO {
    private Long patientUID;
    private String testName;
    private Date timeTaken;
    private Double value;
    private String unit;

    public TestResultDTO(Long patientUID, String testName, Date timeTaken, Double value, String unit) {
        this.patientUID = patientUID;
        this.testName = testName;
        this.timeTaken = timeTaken;
        this.value = value;
        this.unit = unit;
    }

    public Long getPatientUID() {
        return patientUID;
    }

    public void setPatientUID(Long patientUID) {
        this.patientUID = patientUID;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public Date getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(Date timeTaken) {
        this.timeTaken = timeTaken;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
