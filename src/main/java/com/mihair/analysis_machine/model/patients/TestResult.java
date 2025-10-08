package com.mihair.analysis_machine.model.patients;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;

@Entity
public class TestResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long UID;
    private Long patientUID;
    @Lob
    @Basic(fetch = FetchType.EAGER)
    private byte[] testName;
    private Date timeTaken;
    @Lob
    @Basic(fetch = FetchType.EAGER)
    private byte[] value;
    @Lob
    @Basic(fetch = FetchType.EAGER)
    private byte[] unit;


    public TestResult(Long patientUID, byte[] testName, Date timeTaken, byte[] value, byte[] unit) {
        this.patientUID = patientUID;
        this.testName = testName;
        this.timeTaken = timeTaken;
        this.value = value;
        this.unit = unit;
    }

    public TestResult(Long patientUID, byte[] testName, byte[] value, byte[] unit) {
        this(patientUID, testName, Date.from(Instant.ofEpochSecond(System.currentTimeMillis())), value, unit);
    }

    public TestResult() {

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

    public byte[] getTestName() {
        return testName;
    }

    public void setTestName(byte[] testName) {
        this.testName = testName;
    }

    public Date getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(Date timeTaken) {
        this.timeTaken = timeTaken;
    }

    public byte[] getValue() {
        return value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }

    public byte[] getUnit() {
        return unit;
    }

    public void setUnit(byte[] unit) {
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
