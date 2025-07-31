package com.mihair.analysis_machine.service;

import com.azure.security.keyvault.keys.cryptography.CryptographyClient;
import com.azure.security.keyvault.keys.cryptography.CryptographyClientBuilder;
import com.azure.security.keyvault.keys.cryptography.models.EncryptResult;
import com.azure.security.keyvault.keys.models.KeyVaultKey;
import com.mihair.analysis_machine.service.exception.CryptoClientCreationException;
import com.mihair.analysis_machine.util.CredentialEnvProvider;
import com.mihair.analysis_machine.util.keyutil.KeyProvider;
import com.mihair.analysis_machine.util.keyutil.KeyProviders;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PatientCryptographyService {
    //TODO : add lazy cache invalidation if a record is older then *X* mins

    private final Map<String, CryptographyClient> cryptoClientCache = new ConcurrentHashMap<>();

    public CryptographyClient getClientByKeyName(String keyName) throws CryptoClientCreationException {
        return cryptoClientCache.computeIfAbsent(keyName, name -> {
            KeyVaultKey key = KeyProvider.getInstance(KeyProviders.PATIENT).requestKey(name); // gets latest version
            try {
                return createCryptoClient(key.getId());
            } catch (Exception e) {
                throw new CryptoClientCreationException(e.getMessage());
            }
        });
    }

    public void forgetClient(String keyName) {
        cryptoClientCache.remove(keyName);

        KeyProvider.getInstance(KeyProviders.PATIENT).forgetKey(keyName);
    }


    public CryptographyClient getClientByKeyId(String keyId) throws CryptoClientCreationException{
        return cryptoClientCache.computeIfAbsent(keyId,  name -> {
            try {
                return createCryptoClient(name);
            } catch (Exception e) {
                throw new CryptoClientCreationException(e.getMessage());
            }
        });
    }

    private CryptographyClient createCryptoClient(String keyId) throws Exception {
        return new CryptographyClientBuilder()
                .keyIdentifier(keyId)
                .credential(CredentialEnvProvider.getCredentials())
                .buildClient();
    }

    public String encrypt(String plainText, String keyName) {
        CryptographyClient client = getClientByKeyName(keyName);

        EncryptResult result = client.encrypt(
                com.azure.security.keyvault.keys.cryptography.models.EncryptionAlgorithm.RSA_OAEP,
                plainText.getBytes(StandardCharsets.UTF_8)
        );

        return Base64.getEncoder().encodeToString(result.getCipherText());
    }


    public String decrypt(String cipherTextBase64, String keyName) {
        CryptographyClient client = getClientByKeyName(keyName);

        byte[] cipherText = Base64.getDecoder().decode(cipherTextBase64);

        var result = client.decrypt(
                com.azure.security.keyvault.keys.cryptography.models.EncryptionAlgorithm.RSA_OAEP,
                cipherText
        );

        return new String(result.getPlainText());
    }
}
