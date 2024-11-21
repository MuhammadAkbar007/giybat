package uz.akbar.giybat.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uz.akbar.giybat.dto.AppResponse;
import uz.akbar.giybat.dto.LoginDto;
import uz.akbar.giybat.dto.RegistrationDto;
import uz.akbar.giybat.enums.AppLanguage;
import uz.akbar.giybat.service.AuthService;

/** AuthController */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired private AuthService service;

    @PostMapping("/registration")
    public ResponseEntity<?> registration(
            @Valid @RequestBody RegistrationDto dto,
            @RequestHeader("Accept-Language") AppLanguage lang) {

        AppResponse response = service.registration(dto, lang);
        return ResponseEntity.ok(response.getData());
    }

    @GetMapping("/registration/verification/{profileId}")
    public ResponseEntity<?> regVerification(
            @PathVariable("profileId") Integer profileId,
            @RequestHeader("Accept-Language") AppLanguage lang) {

        AppResponse response = service.regVerification(profileId, lang);
        return ResponseEntity.ok(response.getData());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody LoginDto dto, @RequestHeader("Accept-Language") AppLanguage lang) {

        AppResponse response = service.login(dto, lang);
        return ResponseEntity.ok(response.getData());
    }
}
