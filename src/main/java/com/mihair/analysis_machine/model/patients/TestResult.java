package com.mihair.analysis_machine.model.patients;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;

@Entity
public class TestResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long UID;
    private Long patientUID;
    private String testName;
    private Date timeTaken;
    private Double value;
    private String unit;


    public TestResult(String testName, Date timeTaken, double value, String unit) {
        this.testName = testName;
        this.timeTaken = timeTaken;
        this.value = value;
        this.unit = unit;
    }

    public TestResult(String testName, double value, String unit) {
        this(testName, Date.from(Instant.ofEpochSecond(System.currentTimeMillis())), value, unit);
    }

    public TestResult() {
        this("New Test", 0D, "NO UNIT");
    }

    public Long getUID() {
        return UID;
    }

    public void setUID(Long UID) {
        this.UID = UID;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestResult that = (TestResult) o;
        return Objects.equals(UID, that.UID) && Objects.equals(patientUID, that.patientUID) && Objects.equals(testName, that.testName) && Objects.equals(timeTaken, that.timeTaken) && Objects.equals(value, that.value) && Objects.equals(unit, that.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(UID, patientUID, testName, timeTaken, value, unit);
    }

    @Override
    public String toString() {
        return "TestResult{" +
                "UID=" + UID +
                ", patientUID=" + patientUID +
                ", testName='" + testName + '\'' +
                ", timeTaken=" + timeTaken +
                ", value=" + value +
                ", unit='" + unit + '\'' +
                '}';
    }
}
