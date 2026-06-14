package com.biblioteca.controller;

import com.biblioteca.model.dto.LoginRequest;
import com.biblioteca.model.dto.LoginResponse;
import com.biblioteca.model.dto.RegistroRequest;
import com.biblioteca.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Autenticación", description = "Endpoints de login y registro")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Autentica un usuario y devuelve un token JWT")
    @ApiResponse(responseCode = "200", description = "Login exitoso")
    @ApiResponse(responseCode = "400", description = "Credenciales invalidas", content = @Content)
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar usuario", description = "Crea un nuevo usuario y devuelve un token JWT")
    @ApiResponse(responseCode = "201", description = "Usuario registrado exitosamente")
    @ApiResponse(responseCode = "400", description = "El usuario ya existe o datos invalidos", content = @Content)
    public ResponseEntity<LoginResponse> register(@Valid @RequestBody RegistroRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }
}
