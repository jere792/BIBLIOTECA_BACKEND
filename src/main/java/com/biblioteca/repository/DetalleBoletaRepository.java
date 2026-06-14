package com.biblioteca.repository;

import com.biblioteca.model.entity.DetalleBoleta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleBoletaRepository extends JpaRepository<DetalleBoleta, Integer> {
}
