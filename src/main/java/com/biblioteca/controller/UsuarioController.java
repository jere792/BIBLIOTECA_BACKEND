package com.biblioteca.controller;

import com.biblioteca.model.dto.UsuarioResponse;
import com.biblioteca.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
@Tag(name = "Usuarios", description = "Gestión de usuarios (solo admin)")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    @Operation(summary = "Listar usuarios", description = "Obtiene todos los usuarios registrados (solo admin)")
    @ApiResponse(responseCode = "200", description = "Lista de usuarios")
    public ResponseEntity<List<UsuarioResponse>> findAll() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario por ID")
    @ApiResponse(responseCode = "200", description = "Usuario encontrado")
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    public ResponseEntity<UsuarioResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(usuarioService.findById(id));
    }
}
