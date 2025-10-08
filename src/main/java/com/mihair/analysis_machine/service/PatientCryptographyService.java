package com.mihair.analysis_machine.service;

import com.azure.security.keyvault.secrets.models.KeyVaultSecret;
import com.mihair.analysis_machine.security.secret.SecretProvider;
import com.mihair.analysis_machine.security.secret.SecretProviders;
import com.mihair.analysis_machine.service.exception.CryptoClientCreationException;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PatientCryptographyService {
    //TODO : read from a config file, do not set it up with hardcoded value
    private final static Integer CACHE_INVALIDATION_SECONDS = 60 * 10;
    private final Map<String, Pair<KeyVaultSecret, Date>> secretCache = new ConcurrentHashMap<>();

    public KeyVaultSecret getSecretOrCreateByName(String keyName) throws CryptoClientCreationException {
        if (secretCache.containsKey(keyName) && Date.from(secretCache.get(keyName).getSecond().toInstant().plusSeconds(CACHE_INVALIDATION_SECONDS)).before(Date.from(Instant.ofEpochMilli(System.currentTimeMillis()))))
            secretCache.remove(keyName);

        return secretCache.computeIfAbsent(keyName, name -> {
            KeyVaultSecret key = SecretProvider.getInstance(SecretProviders.PATIENT_SECRETS).getSecretOrCreate(name); // gets latest version
            try {
                return Pair.of(key, Date.from(Instant.ofEpochMilli(System.currentTimeMillis())));
            } catch (Exception e) {
                throw new CryptoClientCreationException(e.getMessage());
            }
        }).getFirst();
    }

    public KeyVaultSecret getSecretByName(String keyName) throws CryptoClientCreationException {
        if (secretCache.containsKey(keyName) && Date.from(secretCache.get(keyName).getSecond().toInstant().plusSeconds(CACHE_INVALIDATION_SECONDS)).before(Date.from(Instant.ofEpochMilli(System.currentTimeMillis()))))
            secretCache.remove(keyName);

        Optional<KeyVaultSecret> key = SecretProvider.getInstance(SecretProviders.PATIENT_SECRETS).getSecret(keyName);
        if(key.isPresent()) {
            try {
                secretCache.put(keyName, Pair.of(key.get(), Date.from(Instant.ofEpochMilli(System.currentTimeMillis()))));
            } catch (Exception e) {
                throw new CryptoClientCreationException(e.getMessage());
            }
            return secretCache.get(keyName).getFirst();
        }
        return null;
    }



    public void forgetSecret(String keyName) {
        secretCache.remove(keyName);

        SecretProvider.getInstance(SecretProviders.PATIENT_SECRETS).forgetSecret(keyName);
    }

    public byte[] encrypt(String plainText, String keyName) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        String keyBase64 = getSecretByName(keyName).getValue();
        if (keyBase64 == null) {
            throw new RuntimeException("Could not find key!"); // Abort if no key found
        }
        byte[] keyBytes = Base64.getDecoder().decode(keyBase64);

        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");

        // IV
        byte[] iv = new byte[12];
        new SecureRandom().nextBytes(iv);

        // why does this default to ECB?
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec gcmSpec = new GCMParameterSpec(128, iv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, gcmSpec);

        byte[] cipherText = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

        // prepend initial vector
        byte[] combined = new byte[iv.length + cipherText.length];
        System.arraycopy(iv, 0, combined, 0, iv.length);
        System.arraycopy(cipherText, 0, combined, iv.length, cipherText.length);

        return combined;
    }


    public byte[] decrypt(byte[] encryptedText, String keyName) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        // Fetch key and decode from Base64
        String keyBase64 = getSecretByName(keyName).getValue();
        if (keyBase64 == null) {
            throw new RuntimeException("Could not find key!");
        }
        byte[] keyBytes = Base64.getDecoder().decode(keyBase64);
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");

        // Decode Base64
        byte[] combined = encryptedText;
        // Extract IV and ciphertext
        byte[] iv = Arrays.copyOfRange(combined, 0, 12);
        byte[] cipherText = Arrays.copyOfRange(combined, 12, combined.length);

        // Decrypt
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec gcmSpec = new GCMParameterSpec(128, iv);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, gcmSpec);

        return cipher.doFinal(cipherText);
    }
}
