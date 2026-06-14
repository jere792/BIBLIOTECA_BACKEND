package com.biblioteca.model.dto;

public class UsuarioResponse {

    private Integer idUsuario;
    private String usuario;
    private String nombre;
    private String apellido;
    private String telefono;
    private String dni;
    private String direccion;
    private Integer rol;

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

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
