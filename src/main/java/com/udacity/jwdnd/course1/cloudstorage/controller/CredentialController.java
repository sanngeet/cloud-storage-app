package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.SecureRandom;
import java.util.Base64;

@RequestMapping("/credential")
@Controller
public class CredentialController {
    private CredentialService credentialService;
    private UserService userService;
    private EncryptionService encryptionService;

    public CredentialController(CredentialService credentialService, UserService userService, EncryptionService encryptionService) {
        this.credentialService = credentialService;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    @PostMapping
    public String addUpdate(Authentication authentication, Credentials credential, Model model, RedirectAttributes redirectAttributes) {
        String errorMessage = null;

        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        credential.setKey(encodedKey);
        credential.setPassword(encryptedPassword);

        if (credential.getCredentialId() == null) {
            credential.setUserId(this.userService.getUserId(authentication.getName()));
            if (credentialService.add(credential) < 1) {
                errorMessage = "There was an error saving credentials. Please try again.";
            }
        } else {
            if (credentialService.update(credential) < 1) {
                errorMessage = "There was an error updating credentials. Please try again.";
            }
        }
        redirectAttributes.addFlashAttribute("errorMessage", errorMessage);

        return "redirect:/result";
    }

    @PostMapping("/delete")
    public String deleteCredentials(@RequestParam Integer credentialId, Model model, RedirectAttributes redirectAttributes) {
        String errorMessage = null;

        if (credentialService.delete(credentialId) < 1) {
            errorMessage = "There was an error deleting the credentials. Please try again.";
        }

        redirectAttributes.addFlashAttribute("errorMessage", errorMessage);

        return "redirect:/result";
    }

    @GetMapping(value = "/getDecryptedPassword/{credentialId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getDecryptedPassword(@PathVariable(value = "credentialId") Integer credentialId) {
        String result = credentialService.getDecryptedCredentialPassword(credentialId);
        return ResponseEntity.ok(result);
    }
}
