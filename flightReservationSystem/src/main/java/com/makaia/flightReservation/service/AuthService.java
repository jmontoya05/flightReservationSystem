package com.makaia.flightReservation.service;

import com.makaia.flightReservation.dto.AuthResponseDTO;
import com.makaia.flightReservation.dto.LoginDTO;
import com.makaia.flightReservation.dto.RegisterDTO;
import com.makaia.flightReservation.model.User;
import com.makaia.flightReservation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponseDTO login(LoginDTO loginDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        UserDetails user = userRepository.findByUsername(loginDTO.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User with username " + loginDTO.getUsername() + " not found"));
        String token = jwtService.getToken(user);
        return new AuthResponseDTO(token);
    }

    public AuthResponseDTO register(RegisterDTO registerDTO) {
        User user = new User(
                0,
                registerDTO.getUsername(),
                registerDTO.getFirstName(),
                registerDTO.getLastName(),
                passwordEncoder.encode(registerDTO.getPassword()),
                1
        );

        userRepository.save(user);

        return new AuthResponseDTO(jwtService.getToken(user));
    }
}
