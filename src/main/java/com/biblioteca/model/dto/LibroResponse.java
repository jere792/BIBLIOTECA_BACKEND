package com.biblioteca.model.dto;

import java.math.BigDecimal;

public class LibroResponse {

    private Integer idLibro;
    private String titulo;
    private String autor;
    private Integer anoPublicacion;
    private String descripcion;
    private String imagen;
    private BigDecimal precio;
    private Integer stock;
    private Integer idCategoria;
    private String nombreCategoria;

    public Integer getIdLibro() { return idLibro; }
    public void setIdLibro(Integer idLibro) { this.idLibro = idLibro; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public Integer getAnoPublicacion() { return anoPublicacion; }
    public void setAnoPublicacion(Integer anoPublicacion) { this.anoPublicacion = anoPublicacion; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public Integer getIdCategoria() { return idCategoria; }
    public void setIdCategoria(Integer idCategoria) { this.idCategoria = idCategoria; }

    public String getNombreCategoria() { return nombreCategoria; }
    public void setNombreCategoria(String nombreCategoria) { this.nombreCategoria = nombreCategoria; }
}
