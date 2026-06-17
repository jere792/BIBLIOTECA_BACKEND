package com.biblioteca.controller;

import com.biblioteca.config.JwtUserDetails;
import com.biblioteca.model.dto.ResenaRequest;
import com.biblioteca.model.dto.ResenaResponse;
import com.biblioteca.service.ResenaService;
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
@RequestMapping("/api/v1/resenas")
@Tag(name = "Reseñas", description = "Gestión de reseñas de libros")
public class ResenaController {

    private final ResenaService resenaService;

    public ResenaController(ResenaService resenaService) {
        this.resenaService = resenaService;
    }

    @GetMapping
    @Operation(summary = "Listar reseñas", description = "Obtiene todas las reseñas (solo admin)")
    @ApiResponse(responseCode = "200", description = "Lista de reseñas")
    public ResponseEntity<List<ResenaResponse>> findAll() {
        return ResponseEntity.ok(resenaService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener reseña por ID")
    @ApiResponse(responseCode = "200", description = "Reseña encontrada")
    @ApiResponse(responseCode = "404", description = "Reseña no encontrada", content = @Content)
    public ResponseEntity<ResenaResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(resenaService.findById(id));
    }

    @GetMapping("/libro/{idLibro}")
    @Operation(summary = "Listar reseñas por libro", description = "Obtiene todas las reseñas de un libro")
    @ApiResponse(responseCode = "200", description = "Lista de reseñas del libro")
    public ResponseEntity<List<ResenaResponse>> findByLibro(@PathVariable Integer idLibro) {
        return ResponseEntity.ok(resenaService.findByLibro(idLibro));
    }

    @GetMapping("/usuario")
    @Operation(summary = "Mis reseñas", description = "Obtiene las reseñas del usuario autenticado")
    @ApiResponse(responseCode = "200", description = "Lista de reseñas del usuario")
    public ResponseEntity<List<ResenaResponse>> misResenas(Authentication authentication) {
        JwtUserDetails userDetails = (JwtUserDetails) authentication.getDetails();
        return ResponseEntity.ok(resenaService.findByUsuario(userDetails.getUserId()));
    }

    @PostMapping
    @Operation(summary = "Crear reseña", description = "Registra una nueva reseña para un libro")
    @ApiResponse(responseCode = "201", description = "Reseña creada exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos invalidos", content = @Content)
    public ResponseEntity<ResenaResponse> create(@Valid @RequestBody ResenaRequest request,
                                                  Authentication authentication) {
        JwtUserDetails userDetails = (JwtUserDetails) authentication.getDetails();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(resenaService.create(request, userDetails.getUserId()));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar reseña", description = "Actualiza una reseña propia")
    @ApiResponse(responseCode = "200", description = "Reseña actualizada")
    @ApiResponse(responseCode = "400", description = "Datos invalidos", content = @Content)
    @ApiResponse(responseCode = "404", description = "Reseña no encontrada", content = @Content)
    public ResponseEntity<ResenaResponse> update(@PathVariable Integer id,
                                                  @Valid @RequestBody ResenaRequest request,
                                                  Authentication authentication) {
        JwtUserDetails userDetails = (JwtUserDetails) authentication.getDetails();
        return ResponseEntity.ok(resenaService.update(id, request, userDetails.getUserId()));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar reseña", description = "Elimina una reseña propia (admin puede eliminar cualquiera)")
    @ApiResponse(responseCode = "204", description = "Reseña eliminada")
    @ApiResponse(responseCode = "404", description = "Reseña no encontrada", content = @Content)
    public ResponseEntity<Void> delete(@PathVariable Integer id, Authentication authentication) {
        JwtUserDetails userDetails = (JwtUserDetails) authentication.getDetails();
        if (userDetails.getRol() == 3) {
            resenaService.deleteAsAdmin(id);
        } else {
            resenaService.delete(id, userDetails.getUserId());
        }
        return ResponseEntity.noContent().build();
    }
}
