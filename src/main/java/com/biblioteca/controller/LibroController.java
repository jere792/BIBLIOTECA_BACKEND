package com.biblioteca.controller;

import com.biblioteca.model.dto.LibroRequest;
import com.biblioteca.model.dto.LibroResponse;
import com.biblioteca.service.LibroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/libros")
@Tag(name = "Libros", description = "CRUD del catálogo de libros")
public class LibroController {

    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    @GetMapping
    @Operation(summary = "Listar libros", description = "Obtiene todos los libros con filtros opcionales")
    @ApiResponse(responseCode = "200", description = "Lista de libros")
    public ResponseEntity<List<LibroResponse>> findAll(
            @RequestParam(required = false) String busqueda,
            @RequestParam(required = false) Integer categoria) {
        return ResponseEntity.ok(libroService.findAll(busqueda, categoria));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener libro por ID")
    @ApiResponse(responseCode = "200", description = "Libro encontrado")
    @ApiResponse(responseCode = "404", description = "Libro no encontrado", content = @Content)
    public ResponseEntity<LibroResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(libroService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Crear libro", description = "Crea un nuevo libro (solo admin)")
    @ApiResponse(responseCode = "201", description = "Libro creado")
    @ApiResponse(responseCode = "400", description = "Datos invalidos", content = @Content)
    public ResponseEntity<LibroResponse> create(@Valid @RequestBody LibroRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(libroService.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar libro", description = "Actualiza un libro existente (solo admin)")
    @ApiResponse(responseCode = "200", description = "Libro actualizado")
    @ApiResponse(responseCode = "404", description = "Libro no encontrado", content = @Content)
    public ResponseEntity<LibroResponse> update(@PathVariable Integer id,
                                                 @Valid @RequestBody LibroRequest request) {
        return ResponseEntity.ok(libroService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar libro", description = "Elimina un libro del catálogo (solo admin)")
    @ApiResponse(responseCode = "204", description = "Libro eliminado")
    @ApiResponse(responseCode = "404", description = "Libro no encontrado", content = @Content)
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        libroService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
