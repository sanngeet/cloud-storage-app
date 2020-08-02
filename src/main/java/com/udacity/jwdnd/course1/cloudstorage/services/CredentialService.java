package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {
    private EncryptionService encryptionService;
    private CredentialMapper credentialMapper;

    public CredentialService(EncryptionService encryptionService, CredentialMapper credentialMapper) {
        this.encryptionService = encryptionService;
        this.credentialMapper = credentialMapper;
    }

    public List<Credentials> getAll(Integer credentialId) {
        return credentialMapper.getAll(credentialId);
    }

    public int add(Credentials credentials) {
        return credentialMapper.add(credentials);
    }

    public int update(Credentials credentials) {
        return credentialMapper.update(credentials);
    }

    public int delete(Integer credentialId) {
        return credentialMapper.delete(credentialId);
    }

    public String getDecryptedCredentialPassword(Integer credentialId) {
        Credentials credentialToDecrypt = credentialMapper.getCredentialById(credentialId);
        return encryptionService.decryptValue(credentialToDecrypt.getPassword(),
                credentialToDecrypt.getKey());
    }
}
