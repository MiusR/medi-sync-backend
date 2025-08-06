package com.mihair.analysis_machine.security.key;

public enum KeyProviders {
    PATIENT("patientkvault"),
    API_TRANSMISSION("hospital-api-keys");

    public final String vaultName;

    KeyProviders(String vaultName) {
        this.vaultName = vaultName;
    }
}
