package com.mihair.analysis_machine.security.secret;


import com.azure.core.credential.TokenCredential;
import com.azure.core.exception.ResourceNotFoundException;
import com.azure.core.util.polling.PollResponse;
import com.azure.core.util.polling.SyncPoller;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import com.azure.security.keyvault.secrets.models.DeletedSecret;
import com.azure.security.keyvault.secrets.models.KeyVaultSecret;
import com.mihair.analysis_machine.security.cred.CredentialEnvProvider;
import com.mihair.analysis_machine.security.key.ProviderCreationException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Optional;

public class SecretProvider {
    private final SecretClient client;
    private static final HashMap<SecretProviders, SecretProvider> singletonMap = new HashMap<>();


    public static SecretProvider getInstance(SecretProviders provider) throws ProviderCreationException {
        if (singletonMap.containsKey(provider)) {
            return singletonMap.get(provider);
        } else {
            try {
                singletonMap.put(provider, new SecretProvider(provider.vaultName));
                return singletonMap.get(provider);
            } catch (Exception e) {
                throw new ProviderCreationException("Could not create key provider of type:" + provider.name());
            }
        }
    }

    private SecretProvider(String vaultName) throws Exception {
        TokenCredential c = CredentialEnvProvider.getCredentials();
        client = new SecretClientBuilder()
                .vaultUrl("https://" + vaultName + ".vault.azure.net/")
                .credential(c)
                .buildClient();
    }

    // Secret client can work with multiple
    public SecretClient getClient() {
        return client;
    }

    public Optional<KeyVaultSecret> getSecret(String alias) {
        try {
            return Optional.of(client.getSecret(alias));
        } catch (ResourceNotFoundException e) {
            return Optional.empty();
        }
    }

    public PollResponse<DeletedSecret> forgetSecret(String alias) {
        SyncPoller<DeletedSecret, Void> deletedKeyPoller = client.beginDeleteSecret(alias);
        return deletedKeyPoller.poll();
    }

    public KeyVaultSecret getSecretOrCreate(String name) {
        try {
            return client.getSecret(name);
        } catch (ResourceNotFoundException e) {
            try {
                KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
                keyGenerator.init(256); // magic value for size?
                SecretKey key = keyGenerator.generateKey();
                String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());
                return client.setSecret(new KeyVaultSecret(name, encodedKey));

            } catch (NoSuchAlgorithmException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
