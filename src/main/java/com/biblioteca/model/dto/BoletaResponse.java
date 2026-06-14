package com.biblioteca.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class BoletaResponse {

    private Integer idBoleta;
    private Integer idUsuario;
    private String nombreUsuario;
    private LocalDateTime fecha;
    private BigDecimal total;
    private List<DetalleBoletaResponse> detalles;

    public Integer getIdBoleta() { return idBoleta; }
    public void setIdBoleta(Integer idBoleta) { this.idBoleta = idBoleta; }

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public List<DetalleBoletaResponse> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleBoletaResponse> detalles) { this.detalles = detalles; }
}
