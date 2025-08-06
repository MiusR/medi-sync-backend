package com.mihair.analysis_machine.service;

import com.azure.security.keyvault.secrets.models.KeyVaultSecret;
import com.mihair.analysis_machine.security.cred.APIAuthMapper;
import com.mihair.analysis_machine.security.secret.SecretProvider;
import com.mihair.analysis_machine.security.secret.SecretProviders;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthenticationService {
    private static final String AUTH_TOKEN_HEADER_NAME = "H-API-KEY";

    private final static Integer CACHE_INVALIDATION_SECONDS = 60 * 10;
    private static final Map<String, Pair<KeyVaultSecret, Date>> apiKeysCache = new ConcurrentHashMap<>();


    public static APIAuthMapper getAuthentication(HttpServletRequest request) {
        String apiKey = request.getHeader(AUTH_TOKEN_HEADER_NAME);
        if (apiKey == null) {
            throw new BadCredentialsException("Invalid API Key");
        }

        String[] tokens = apiKey.split(":");

        if(tokens.length != 2)
            throw new BadCredentialsException("Missing API Key details!");

        if(apiKeysCache.containsKey(tokens[0]) && Date.from(apiKeysCache.get(tokens[0]).getSecond().toInstant().plusSeconds(CACHE_INVALIDATION_SECONDS)).before(Date.from(Instant.ofEpochMilli(System.currentTimeMillis())))) {
            apiKeysCache.remove(tokens[0]); // Cache expired
        }

        // Check cache
        if(apiKeysCache.containsKey(tokens[0]) && apiKeysCache.get(tokens[0]).getFirst().getValue().equals(tokens[1])) {
            return new APIAuthMapper(apiKey, AuthorityUtils.NO_AUTHORITIES);
        }

        Optional<KeyVaultSecret> secretOptional =  SecretProvider.getInstance(SecretProviders.API_SECRETS).getSecret(tokens[0]);

        if(secretOptional.isPresent() && secretOptional.get().getValue().equals(tokens[1])) {
            apiKeysCache.put(tokens[0], Pair.of(secretOptional.get(), Date.from(Instant.ofEpochMilli(System.currentTimeMillis()))));
            return new APIAuthMapper(apiKey, AuthorityUtils.NO_AUTHORITIES);
        }else
            throw new BadCredentialsException("Invalid API Key");
    }
}
