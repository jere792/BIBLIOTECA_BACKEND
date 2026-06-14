package com.biblioteca.controller;

import com.biblioteca.config.JwtUserDetails;
import com.biblioteca.model.dto.BoletaRequest;
import com.biblioteca.model.dto.BoletaResponse;
import com.biblioteca.service.BoletaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/boletas")
@Tag(name = "Boletas", description = "Gestión de boletas de venta")
public class BoletaController {

    private final BoletaService boletaService;

    public BoletaController(BoletaService boletaService) {
        this.boletaService = boletaService;
    }

    @PostMapping
    @Operation(summary = "Crear boleta", description = "Registra una nueva venta con sus detalles")
    @ApiResponse(responseCode = "201", description = "Boleta creada exitosamente")
    @ApiResponse(responseCode = "400", description = "Stock insuficiente o datos invalidos", content = @Content)
    public ResponseEntity<BoletaResponse> create(@Valid @RequestBody BoletaRequest request,
                                                  Authentication authentication) {
        JwtUserDetails userDetails = (JwtUserDetails) authentication.getDetails();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(boletaService.create(request, userDetails.getUserId()));
    }

    @GetMapping
    @Operation(summary = "Listar boletas", description = "Obtiene todas las boletas registradas (solo admin)")
    @ApiResponse(responseCode = "200", description = "Lista de boletas")
    public ResponseEntity<List<BoletaResponse>> findAll() {
        return ResponseEntity.ok(boletaService.findAll());
    }

    @GetMapping("/mis-boletas")
    @Operation(summary = "Mis boletas", description = "Obtiene las boletas del usuario autenticado")
    @ApiResponse(responseCode = "200", description = "Lista de boletas del usuario")
    public ResponseEntity<List<BoletaResponse>> misBoletas(Authentication authentication) {
        JwtUserDetails userDetails = (JwtUserDetails) authentication.getDetails();
        return ResponseEntity.ok(boletaService.findByUsuario(userDetails.getUserId()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener boleta por ID")
    @ApiResponse(responseCode = "200", description = "Boleta encontrada")
    @ApiResponse(responseCode = "404", description = "Boleta no encontrada", content = @Content)
    public ResponseEntity<BoletaResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(boletaService.findById(id));
    }
}
