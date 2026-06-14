package com.biblioteca.service;

import com.biblioteca.exception.ResourceNotFoundException;
import com.biblioteca.mapper.LibroMapper;
import com.biblioteca.model.dto.LibroRequest;
import com.biblioteca.model.dto.LibroResponse;
import com.biblioteca.model.entity.Categoria;
import com.biblioteca.model.entity.Libro;
import com.biblioteca.repository.CategoriaRepository;
import com.biblioteca.repository.LibroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class LibroService {

    private final LibroRepository libroRepository;
    private final CategoriaRepository categoriaRepository;
    private final LibroMapper libroMapper;

    public LibroService(LibroRepository libroRepository,
                        CategoriaRepository categoriaRepository,
                        LibroMapper libroMapper) {
        this.libroRepository = libroRepository;
        this.categoriaRepository = categoriaRepository;
        this.libroMapper = libroMapper;
    }

    public List<LibroResponse> findAll(String busqueda, Integer idCategoria) {
        List<Libro> libros;

        if (busqueda != null && !busqueda.isBlank()) {
            libros = libroRepository.findByTituloContainingIgnoreCase(busqueda);
        } else if (idCategoria != null) {
            libros = libroRepository.findByCategoriaIdCategoria(idCategoria);
        } else {
            libros = libroRepository.findAll();
        }

        return libros.stream()
                .map(libroMapper::toResponse)
                .toList();
    }

    public LibroResponse findById(Integer id) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Libro", id));
        return libroMapper.toResponse(libro);
    }

    @Transactional
    public LibroResponse create(LibroRequest request) {
        Categoria categoria = null;
        if (request.getIdCategoria() != null) {
            categoria = categoriaRepository.findById(request.getIdCategoria())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoria", request.getIdCategoria()));
        }

        Libro libro = libroMapper.toEntity(request, categoria);
        libro = libroRepository.save(libro);
        return libroMapper.toResponse(libro);
    }

    @Transactional
    public LibroResponse update(Integer id, LibroRequest request) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Libro", id));

        Categoria categoria = null;
        if (request.getIdCategoria() != null) {
            categoria = categoriaRepository.findById(request.getIdCategoria())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoria", request.getIdCategoria()));
        }

        libroMapper.updateEntity(libro, request, categoria);
        libro = libroRepository.save(libro);
        return libroMapper.toResponse(libro);
    }

    @Transactional
    public void delete(Integer id) {
        if (!libroRepository.existsById(id)) {
            throw new ResourceNotFoundException("Libro", id);
        }
        libroRepository.deleteById(id);
    }
}
