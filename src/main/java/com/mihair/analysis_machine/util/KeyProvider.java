package com.mihair.analysis_machine.util;

import com.azure.core.exception.ResourceNotFoundException;
import com.azure.core.util.polling.PollResponse;
import com.azure.core.util.polling.SyncPoller;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.identity.ManagedIdentityCredentialBuilder;
import com.azure.security.keyvault.keys.KeyClient;
import com.azure.security.keyvault.keys.KeyClientBuilder;
import com.azure.security.keyvault.keys.models.CreateRsaKeyOptions;
import com.azure.security.keyvault.keys.models.DeletedKey;
import com.azure.security.keyvault.keys.models.KeyVaultKey;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.logging.Logger;

public class KeyProvider {
    private final KeyClient client;


// TODO : add local caching of keys with in memory encryption
public KeyProvider(String vaultName) {
        client = new KeyClientBuilder()
                .vaultUrl("https://" + vaultName + ".vault.azure.net/")
                .credential(new ManagedIdentityCredentialBuilder().clientId(System.getenv("AZURE_CLIENT_ID")).build())
                .buildClient();
    }

    public KeyVaultKey requestKey(String alias) {
       try {
           // Attempt fetch

           return client.getKey(alias);
       } catch (ResourceNotFoundException e ) {
           KeyVaultKey rsaKey = client.createRsaKey(new CreateRsaKeyOptions(alias)
                   .setExpiresOn(OffsetDateTime.now().plusYears(1))
                   .setKeySize(2048));
           return rsaKey;
       }
    }

    public PollResponse<DeletedKey> forgetKey(String alias) {
        SyncPoller<DeletedKey, Void> deletedKeyPoller = client.beginDeleteKey(alias);
        return deletedKeyPoller.poll();
    }
}
