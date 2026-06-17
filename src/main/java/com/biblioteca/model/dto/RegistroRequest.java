package com.biblioteca.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegistroRequest {

    @NotBlank
    @Size(min = 3, max = 50)
    private String usuario;

    @NotBlank
    @Size(min = 4, max = 255)
    private String contrasena;

    private String nombre;
    private String apellido;
    private String telefono;
    private String dni;
    private String direccion;

    @Schema(description = "Rol del usuario", allowableValues = {"1: CLIENTE", "2: EMPLEADO", "3: ADMIN"}, example = "1")
    @Min(1)
    @Max(3)
    private Integer rol;

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public Integer getRol() { return rol; }
    public void setRol(Integer rol) { this.rol = rol; }
}
