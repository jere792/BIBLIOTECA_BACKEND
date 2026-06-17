package com.biblioteca.model.entity;

import com.biblioteca.model.enums.RolUsuario;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario")
    private Integer idUsuario;

    @Column(nullable = false, unique = true, length = 50)
    private String usuario;

    @Column(nullable = false, length = 255)
    private String contrasena;

    @Column(length = 100)
    private String nombre;

    @Column(length = 100)
    private String apellido;

    @Column(length = 100)
    private String telefono;

    @Column(length = 20)
    private String dni;

    @Column(length = 150)
    private String direccion;

    @Column(nullable = false)
    private Integer rol;

    @OneToMany(mappedBy = "usuario")
    private List<Boleta> boletas;

    @OneToMany(mappedBy = "usuario")
    private List<Resena> resenas;

    public Usuario() {}

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

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

    public RolUsuario getRolEnum() { return RolUsuario.fromValue(rol); }
    public void setRolEnum(RolUsuario rol) { this.rol = rol.getValue(); }

    public List<Boleta> getBoletas() { return boletas; }
    public void setBoletas(List<Boleta> boletas) { this.boletas = boletas; }

    public List<Resena> getResenas() { return resenas; }
    public void setResenas(List<Resena> resenas) { this.resenas = resenas; }
}
