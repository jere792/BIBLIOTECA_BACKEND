package com.biblioteca.mapper;

import com.biblioteca.model.dto.LibroRequest;
import com.biblioteca.model.dto.LibroResponse;
import com.biblioteca.model.entity.Categoria;
import com.biblioteca.model.entity.Libro;
import org.springframework.stereotype.Component;

@Component
public class LibroMapper {

    public LibroResponse toResponse(Libro libro) {
        LibroResponse dto = new LibroResponse();
        dto.setIdLibro(libro.getIdLibro());
        dto.setTitulo(libro.getTitulo());
        dto.setAutor(libro.getAutor());
        dto.setAnoPublicacion(libro.getAnoPublicacion());
        dto.setDescripcion(libro.getDescripcion());
        dto.setImagen(libro.getImagen());
        dto.setPrecio(libro.getPrecio());
        dto.setStock(libro.getStock());
        if (libro.getCategoria() != null) {
            dto.setIdCategoria(libro.getCategoria().getIdCategoria());
            dto.setNombreCategoria(libro.getCategoria().getNombreCategoria());
        }
        return dto;
    }

    public Libro toEntity(LibroRequest request, Categoria categoria) {
        Libro libro = new Libro();
        libro.setTitulo(request.getTitulo());
        libro.setAutor(request.getAutor());
        libro.setAnoPublicacion(request.getAnoPublicacion());
        libro.setDescripcion(request.getDescripcion());
        libro.setImagen(request.getImagen());
        libro.setPrecio(request.getPrecio());
        libro.setStock(request.getStock());
        libro.setCategoria(categoria);
        return libro;
    }

    public void updateEntity(Libro libro, LibroRequest request, Categoria categoria) {
        libro.setTitulo(request.getTitulo());
        libro.setAutor(request.getAutor());
        libro.setAnoPublicacion(request.getAnoPublicacion());
        libro.setDescripcion(request.getDescripcion());
        libro.setImagen(request.getImagen());
        libro.setPrecio(request.getPrecio());
        libro.setStock(request.getStock());
        libro.setCategoria(categoria);
    }
}
