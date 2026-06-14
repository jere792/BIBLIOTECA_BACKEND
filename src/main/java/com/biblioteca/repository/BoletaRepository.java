package com.biblioteca.repository;

import com.biblioteca.model.entity.Boleta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoletaRepository extends JpaRepository<Boleta, Integer> {
    List<Boleta> findByUsuarioIdUsuarioOrderByFechaDesc(Integer idUsuario);
}
