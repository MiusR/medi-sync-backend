package com.mihair.analysis_machine.util;

import com.azure.core.credential.TokenCredential;
import com.azure.identity.*;

public class CredentialEnvProvider {

    public static final String AZURE_MANAGED_IDENTITY = "AZURE_MANAGED_IDENTITY";
    public static final String AZURE_TENANT_ID = "AZURE_TENANT_ID";
    public static final String AZURE_CLIENT_ID = "AZURE_CLIENT_ID";
    public static final String AZURE_CLIENT_SECRET = "AZURE_CLIENT_SECRET";

    // Attempt to connect via managed identity then via client credentials depending on env variables
    public static TokenCredential getCredentials() throws Exception {
        System.setProperty("azure.identity.logging.level", "verbose");
        String managedIdentity = System.getenv(AZURE_MANAGED_IDENTITY);
        if (System.getenv("") != null) {

            ManagedIdentityCredential managedIdentityCredential = new ManagedIdentityCredentialBuilder()
                    .clientId(managedIdentity)
                    .build();

            return managedIdentityCredential;

        }else {
            String tenantId = System.getenv(AZURE_TENANT_ID);
            String clientId = System.getenv(AZURE_CLIENT_ID);
            String clientSecret = System.getenv(AZURE_CLIENT_SECRET);

            if (tenantId == null || clientId == null || clientSecret == null)
                throw new RuntimeException("Could not connect by any provided means. Please add a managed identity or client credentials.");

            ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder()
                    .tenantId(tenantId)
                    .clientId(clientId)
                    .clientSecret(clientSecret)
                    .build();

            return clientSecretCredential;

        }
    }


}
