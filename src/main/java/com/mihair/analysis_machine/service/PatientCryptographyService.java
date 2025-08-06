package com.mihair.analysis_machine.service;

import com.azure.security.keyvault.keys.cryptography.CryptographyClient;
import com.azure.security.keyvault.keys.cryptography.CryptographyClientBuilder;
import com.azure.security.keyvault.keys.cryptography.models.EncryptResult;
import com.azure.security.keyvault.keys.models.KeyVaultKey;
import com.mihair.analysis_machine.service.exception.CryptoClientCreationException;
import com.mihair.analysis_machine.security.cred.CredentialEnvProvider;
import com.mihair.analysis_machine.security.key.KeyProvider;
import com.mihair.analysis_machine.security.key.KeyProviders;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PatientCryptographyService {
    //TODO : read from a config file, do not set it up with hardcoded value
    private final static Integer CACHE_INVALIDATION_SECONDS = 60 * 10;
    private final Map<String, Pair<CryptographyClient, Date>> cryptoClientCache = new ConcurrentHashMap<>();

    public CryptographyClient getClientOrCreateByKeyName(String keyName) throws CryptoClientCreationException {
        if (cryptoClientCache.containsKey(keyName) && Date.from(cryptoClientCache.get(keyName).getSecond().toInstant().plusSeconds(CACHE_INVALIDATION_SECONDS)).before(Date.from(Instant.ofEpochMilli(System.currentTimeMillis()))))
            cryptoClientCache.remove(keyName);

        return cryptoClientCache.computeIfAbsent(keyName, name -> {
            KeyVaultKey key = KeyProvider.getInstance(KeyProviders.PATIENT).getKeyOrCreate(name); // gets latest version
            try {
                return Pair.of(createCryptoClient(key.getId()), Date.from(Instant.ofEpochMilli(System.currentTimeMillis())));
            } catch (Exception e) {
                throw new CryptoClientCreationException(e.getMessage());
            }
        }).getFirst();
    }

    //TODO : review code
    public CryptographyClient getClientByKeyName(String keyName) throws CryptoClientCreationException {
        if (cryptoClientCache.containsKey(keyName) && Date.from(cryptoClientCache.get(keyName).getSecond().toInstant().plusSeconds(CACHE_INVALIDATION_SECONDS)).before(Date.from(Instant.ofEpochMilli(System.currentTimeMillis()))))
            cryptoClientCache.remove(keyName);

        Optional<KeyVaultKey> key = KeyProvider.getInstance(KeyProviders.PATIENT).getKey(keyName);
        if(key.isPresent()) {
            try {
                cryptoClientCache.put(keyName, Pair.of(createCryptoClient(key.get().getId()), Date.from(Instant.ofEpochMilli(System.currentTimeMillis()))));
            } catch (Exception e) {
                throw new CryptoClientCreationException(e.getMessage());
            }
            return cryptoClientCache.get(keyName).getFirst();
        }
        return null;
    }



    public void forgetClient(String keyName) {
        cryptoClientCache.remove(keyName);

        KeyProvider.getInstance(KeyProviders.PATIENT).forgetKey(keyName);
    }


    public CryptographyClient getClientByKeyId(String keyId) throws CryptoClientCreationException{
        if (cryptoClientCache.containsKey(keyId) && Date.from(cryptoClientCache.get(keyId).getSecond().toInstant().plusSeconds(CACHE_INVALIDATION_SECONDS)).before(Date.from(Instant.ofEpochMilli(System.currentTimeMillis()))))
            cryptoClientCache.remove(keyId);

        return cryptoClientCache.computeIfAbsent(keyId,  name -> {
            try {
                return Pair.of(createCryptoClient(name), Date.from(Instant.ofEpochMilli(System.currentTimeMillis())));
            } catch (Exception e) {
                throw new CryptoClientCreationException(e.getMessage());
            }
        }).getFirst();
    }

    private CryptographyClient createCryptoClient(String keyId) throws Exception {
        return new CryptographyClientBuilder()
                .keyIdentifier(keyId)
                .credential(CredentialEnvProvider.getCredentials())
                .buildClient();
    }

    public String encrypt(String plainText, String keyName) {
        CryptographyClient client = getClientByKeyName(keyName);

        if (client == null)
            return null; // Abort encryption


        EncryptResult result = client.encrypt(
                com.azure.security.keyvault.keys.cryptography.models.EncryptionAlgorithm.RSA_OAEP,
                plainText.getBytes(StandardCharsets.UTF_8)
        );

        return Base64.getEncoder().encodeToString(result.getCipherText());
    }


    public String decrypt(String cipherTextBase64, String keyName) {
        CryptographyClient client = getClientByKeyId(keyName);

        if (client == null)
            return null; // Aborted decryption

        byte[] cipherText = Base64.getDecoder().decode(cipherTextBase64);

        var result = client.decrypt(
                com.azure.security.keyvault.keys.cryptography.models.EncryptionAlgorithm.RSA_OAEP,
                cipherText
        );

        return new String(result.getPlainText());
    }
}
