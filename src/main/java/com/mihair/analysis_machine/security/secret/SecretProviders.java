package com.mihair.analysis_machine.security.secret;

public enum SecretProviders {
    API_SECRETS("hospital-api-keys");

    public final String vaultName;

    SecretProviders(String vaultName) {
        this.vaultName = vaultName;
    }
}
