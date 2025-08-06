package com.mihair.analysis_machine.security.key;

import com.azure.core.credential.TokenCredential;
import com.azure.core.exception.ResourceNotFoundException;
import com.azure.core.util.polling.PollResponse;
import com.azure.core.util.polling.SyncPoller;
import com.azure.security.keyvault.keys.KeyClient;
import com.azure.security.keyvault.keys.KeyClientBuilder;
import com.azure.security.keyvault.keys.models.CreateRsaKeyOptions;
import com.azure.security.keyvault.keys.models.DeletedKey;
import com.azure.security.keyvault.keys.models.KeyVaultKey;
import com.mihair.analysis_machine.security.cred.CredentialEnvProvider;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Optional;


public class KeyProvider {
    private final KeyClient client;
    private static final HashMap<KeyProviders, KeyProvider> singletonMap = new HashMap<>();


    public static KeyProvider getInstance(KeyProviders provider) throws ProviderCreationException {
        if (singletonMap.containsKey(provider)) {
            return singletonMap.get(provider);
        } else {
            try {
                singletonMap.put(provider, new KeyProvider(provider.vaultName));
                return singletonMap.get(provider);
            } catch (Exception e) {
                throw new ProviderCreationException("Could not create key provider of type:" + provider.name());
            }
        }
    }

    private KeyProvider(String vaultName) throws Exception {
        TokenCredential c = CredentialEnvProvider.getCredentials();
        client = new KeyClientBuilder()
                .vaultUrl("https://" + vaultName + ".vault.azure.net/")
                .credential(c)
                .buildClient();
    }

    public KeyVaultKey getKeyOrCreate(String alias) {
        try {
            return client.getKey(alias);
        } catch (ResourceNotFoundException e) {
            KeyVaultKey rsaKey = client.createRsaKey(new CreateRsaKeyOptions(alias)
                    .setExpiresOn(OffsetDateTime.now().plusYears(1))
                    .setKeySize(2048));
            return rsaKey;
        }
    }

    public Optional<KeyVaultKey> getKey(String alias) {
        try {
            return Optional.of(client.getKey(alias));
        } catch (ResourceNotFoundException e) {
            return Optional.empty();
        }
    }

    public PollResponse<DeletedKey> forgetKey(String alias) {
        SyncPoller<DeletedKey, Void> deletedKeyPoller = client.beginDeleteKey(alias);
        return deletedKeyPoller.poll();
    }
}
