package com.biblioteca.model.dto;

import java.time.LocalDateTime;

public class ResenaResponse {

    private Integer idResena;
    private Integer idUsuario;
    private String nombreUsuario;
    private Integer idLibro;
    private String tituloLibro;
    private String comentario;
    private Integer calificacion;
    private LocalDateTime fecha;

    public Integer getIdResena() { return idResena; }
    public void setIdResena(Integer idResena) { this.idResena = idResena; }

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public Integer getIdLibro() { return idLibro; }
    public void setIdLibro(Integer idLibro) { this.idLibro = idLibro; }

    public String getTituloLibro() { return tituloLibro; }
    public void setTituloLibro(String tituloLibro) { this.tituloLibro = tituloLibro; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }

    public Integer getCalificacion() { return calificacion; }
    public void setCalificacion(Integer calificacion) { this.calificacion = calificacion; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}
