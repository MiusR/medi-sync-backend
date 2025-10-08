package com.mihair.analysis_machine.model.util;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="test_types")
public class TestType {
    @Id
    private Long UID;
    private String name;
    private String unit;
    private Double upper_bound;
    private Double lower_bound;

    public void setUID(Long id) {
        this.UID = id;
    }

    public Long getUID() {
        return UID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getUpper_bound() {
        return upper_bound;
    }

    public void setUpper_bound(Double upper_bound) {
        this.upper_bound = upper_bound;
    }

    public Double getLower_bound() {
        return lower_bound;
    }

    public void setLower_bound(Double lower_bound) {
        this.lower_bound = lower_bound;
    }
}
