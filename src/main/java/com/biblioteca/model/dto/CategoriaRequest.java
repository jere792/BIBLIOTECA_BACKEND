package com.biblioteca.model.dto;

import jakarta.validation.constraints.NotBlank;

public class CategoriaRequest {

    @NotBlank
    private String nombreCategoria;

    private String descripcion;

    public String getNombreCategoria() { return nombreCategoria; }
    public void setNombreCategoria(String nombreCategoria) { this.nombreCategoria = nombreCategoria; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
