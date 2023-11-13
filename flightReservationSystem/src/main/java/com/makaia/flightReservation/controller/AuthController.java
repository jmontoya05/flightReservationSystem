package com.makaia.flightReservation.controller;

import com.makaia.flightReservation.dto.AuthResponseDTO;
import com.makaia.flightReservation.dto.LoginDTO;
import com.makaia.flightReservation.dto.RegisterDTO;
import com.makaia.flightReservation.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        return new ResponseEntity<>(authService.login(loginDTO), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterDTO registerDTO) {
        return new ResponseEntity<>(authService.register(registerDTO), HttpStatus.OK);
    }
}
