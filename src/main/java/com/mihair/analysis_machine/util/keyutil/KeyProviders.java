package com.mihair.analysis_machine.util.keyutil;

public enum KeyProviders {
    PATIENT("patientkvault");

    public final String vaultName;

    KeyProviders(String vaultName) {
        this.vaultName = vaultName;
    }
}
