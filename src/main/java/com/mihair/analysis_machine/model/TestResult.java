package com.mihair.analysis_machine.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Date;
@Document
public class TestResult {
    @Id
    private ObjectId UID;
    private ObjectId patientUID;
    private String testName;
    private Date timeTaken;
    private double value;
    private String unit;


    public TestResult(String testName, Date timeTaken, double value, String unit) {
        this.testName = testName;
        this.timeTaken = timeTaken;
        this.value = value;
        this.unit = unit;
        this.UID = new ObjectId();
    }

    public TestResult(String testName, double value, String unit) {
        this(testName, Date.from(Instant.ofEpochSecond(System.currentTimeMillis())), value, unit);
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

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
