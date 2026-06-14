package com.biblioteca.repository;

import com.biblioteca.model.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Integer> {
    List<Libro> findByTituloContainingIgnoreCase(String titulo);
    List<Libro> findByCategoriaIdCategoria(Integer idCategoria);
}
