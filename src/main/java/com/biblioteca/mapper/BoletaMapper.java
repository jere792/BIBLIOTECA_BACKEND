package com.biblioteca.mapper;

import com.biblioteca.model.dto.BoletaResponse;
import com.biblioteca.model.dto.DetalleBoletaResponse;
import com.biblioteca.model.entity.Boleta;
import com.biblioteca.model.entity.DetalleBoleta;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BoletaMapper {

    public BoletaResponse toResponse(Boleta boleta) {
        BoletaResponse dto = new BoletaResponse();
        dto.setIdBoleta(boleta.getIdBoleta());
        dto.setIdUsuario(boleta.getUsuario().getIdUsuario());
        dto.setNombreUsuario(boleta.getUsuario().getNombre() + " " + boleta.getUsuario().getApellido());
        dto.setFecha(boleta.getFecha());
        dto.setTotal(boleta.getTotal());

        List<DetalleBoletaResponse> detalles = boleta.getDetalles().stream()
                .map(this::toDetalleResponse)
                .toList();
        dto.setDetalles(detalles);

        return dto;
    }

    private DetalleBoletaResponse toDetalleResponse(DetalleBoleta detalle) {
        DetalleBoletaResponse dto = new DetalleBoletaResponse();
        dto.setIdDetalle(detalle.getIdDetalle());
        dto.setIdLibro(detalle.getLibro().getIdLibro());
        dto.setTituloLibro(detalle.getLibro().getTitulo());
        dto.setCantidad(detalle.getCantidad());
        dto.setPrecioUnitario(detalle.getPrecioUnitario());
        dto.setSubtotal(detalle.getSubtotal());
        return dto;
    }
}
