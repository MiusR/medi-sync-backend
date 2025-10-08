package com.mihair.analysis_machine.model.util;

public class Versioned<T> {
    private String version;
    private String feature;
    private T data;

    public Versioned(String version, String feature, T data) {
        this.version = version;
        this.feature = feature;
        this.data = data;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
