package by.payyzau.tennis.controller;

import by.payyzau.tennis.dtos.AuthenticationRequest;
import by.payyzau.tennis.dtos.AuthenticationResponse;
import by.payyzau.tennis.dtos.RegisterRequest;
import by.payyzau.tennis.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    private final AuthService service;

    @PostMapping("/singIn")
    public ResponseEntity<AuthenticationResponse> singIn(
            @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.signIn(request));
    }
    @PostMapping("/singUp")
    public ResponseEntity<AuthenticationResponse> singUp(
            @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.signUp(request));
    }
}
