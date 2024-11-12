package uz.akbar.giybat.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.akbar.giybat.dto.RegistrationDto;
import uz.akbar.giybat.service.AuthService;

/** AuthController */
@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired private AuthService service;

  @PostMapping("/registration")
  public ResponseEntity<?> registration(@Valid @RequestBody RegistrationDto dto) {
    return ResponseEntity.ok(service.registration(dto));
  }

  @GetMapping("/registration/verification/{profileId}")
  public ResponseEntity<?> regVerification(@PathVariable("profileId") Integer profileId) {
    return ResponseEntity.ok(service.regVerification(profileId));
  }
}
