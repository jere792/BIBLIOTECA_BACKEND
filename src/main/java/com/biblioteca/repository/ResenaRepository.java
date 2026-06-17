package com.biblioteca.repository;

import com.biblioteca.model.entity.Resena;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResenaRepository extends JpaRepository<Resena, Integer> {
    List<Resena> findByLibroIdLibroOrderByFechaDesc(Integer idLibro);
    List<Resena> findByUsuarioIdUsuarioOrderByFechaDesc(Integer idUsuario);
}
