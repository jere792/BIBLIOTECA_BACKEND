package com.biblioteca.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public class BoletaRequest {

    @NotEmpty
    @Valid
    private List<DetalleBoletaRequest> detalles;

    public List<DetalleBoletaRequest> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleBoletaRequest> detalles) { this.detalles = detalles; }
}
