package com.biblioteca.service;

import com.biblioteca.exception.ResourceNotFoundException;
import com.biblioteca.mapper.BoletaMapper;
import com.biblioteca.model.dto.BoletaRequest;
import com.biblioteca.model.dto.BoletaResponse;
import com.biblioteca.model.entity.Boleta;
import com.biblioteca.model.entity.DetalleBoleta;
import com.biblioteca.model.entity.Libro;
import com.biblioteca.model.entity.Usuario;
import com.biblioteca.repository.BoletaRepository;
import com.biblioteca.repository.LibroRepository;
import com.biblioteca.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BoletaService {

    private final BoletaRepository boletaRepository;
    private final UsuarioRepository usuarioRepository;
    private final LibroRepository libroRepository;
    private final BoletaMapper boletaMapper;

    public BoletaService(BoletaRepository boletaRepository,
                         UsuarioRepository usuarioRepository,
                         LibroRepository libroRepository,
                         BoletaMapper boletaMapper) {
        this.boletaRepository = boletaRepository;
        this.usuarioRepository = usuarioRepository;
        this.libroRepository = libroRepository;
        this.boletaMapper = boletaMapper;
    }

    @Transactional
    public BoletaResponse create(BoletaRequest request, Integer idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", idUsuario));

        Boleta boleta = new Boleta();
        boleta.setUsuario(usuario);
        boleta.setTotal(BigDecimal.ZERO);

        List<DetalleBoleta> detalles = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (var item : request.getDetalles()) {
            Libro libro = libroRepository.findById(item.getIdLibro())
                    .orElseThrow(() -> new ResourceNotFoundException("Libro", item.getIdLibro()));

            if (libro.getStock() < item.getCantidad()) {
                throw new IllegalArgumentException("Stock insuficiente para: " + libro.getTitulo());
            }

            BigDecimal subtotal = libro.getPrecio().multiply(BigDecimal.valueOf(item.getCantidad()));
            total = total.add(subtotal);

            DetalleBoleta detalle = new DetalleBoleta();
            detalle.setBoleta(boleta);
            detalle.setLibro(libro);
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(libro.getPrecio());
            detalle.setSubtotal(subtotal);

            libro.setStock(libro.getStock() - item.getCantidad());
            libroRepository.save(libro);

            detalles.add(detalle);
        }

        boleta.setTotal(total);
        boleta.setDetalles(detalles);
        boleta = boletaRepository.save(boleta);

        return boletaMapper.toResponse(boleta);
    }

    public List<BoletaResponse> findAll() {
        return boletaRepository.findAll().stream()
                .map(boletaMapper::toResponse)
                .toList();
    }

    public BoletaResponse findById(Integer id) {
        Boleta boleta = boletaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Boleta", id));
        return boletaMapper.toResponse(boleta);
    }

    public List<BoletaResponse> findByUsuario(Integer idUsuario) {
        return boletaRepository.findByUsuarioIdUsuarioOrderByFechaDesc(idUsuario).stream()
                .map(boletaMapper::toResponse)
                .toList();
    }
}
