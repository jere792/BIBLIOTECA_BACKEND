package com.biblioteca.model.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "Libro")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idLibro")
    private Integer idLibro;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(nullable = false, length = 150)
    private String autor;

    @Column(name = "anoPublicacion")
    private Integer anoPublicacion;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(length = 500)
    private String imagen;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(nullable = false)
    private Integer stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCategoria")
    private Categoria categoria;

    @OneToMany(mappedBy = "libro")
    private List<DetalleBoleta> detalles;

    @OneToMany(mappedBy = "libro")
    private List<Resena> resenas;

    public Libro() {}

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

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    public List<DetalleBoleta> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleBoleta> detalles) { this.detalles = detalles; }

    public List<Resena> getResenas() { return resenas; }
    public void setResenas(List<Resena> resenas) { this.resenas = resenas; }
}
