// MP/backend/src/main/java/com/jquiguantar/backend/controller/AuthController.java
package com.jquiguantar.backend.controller;

import com.jquiguantar.backend.dto.AuthRequest;
import com.jquiguantar.backend.dto.AuthResponse;
import com.jquiguantar.backend.entity.User;
import com.jquiguantar.backend.security.jwt.JwtUtil;
import com.jquiguantar.backend.security.user.CustomUserDetailsService;
import com.jquiguantar.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService; // Para crear usuarios iniciales

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect username or password");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(jwt));
    }

    @PostMapping("/register") // Endpoint para registrar un nuevo usuario (solo para propósito de testeo/admin)
    public ResponseEntity<?> registerUser(@Valid @RequestBody AuthRequest authRequest) {
        // En un entorno real, añadirías más validaciones y quizás un rol por defecto.
        // Este es solo un ejemplo básico.
        if (userService.saveUser(new User(null, authRequest.getUsername(), authRequest.getPassword(), "ROLE_ADMIN")) != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User registration failed");
    }
}
