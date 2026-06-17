package com.biblioteca.service;

import com.biblioteca.exception.ResourceNotFoundException;
import com.biblioteca.mapper.CategoriaMapper;
import com.biblioteca.model.dto.CategoriaRequest;
import com.biblioteca.model.dto.CategoriaResponse;
import com.biblioteca.model.entity.Categoria;
import com.biblioteca.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    public CategoriaService(CategoriaRepository categoriaRepository, CategoriaMapper categoriaMapper) {
        this.categoriaRepository = categoriaRepository;
        this.categoriaMapper = categoriaMapper;
    }

    public List<CategoriaResponse> findAll() {
        return categoriaRepository.findAll().stream()
                .map(categoriaMapper::toResponse)
                .toList();
    }

    public CategoriaResponse findById(Integer id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria", id));
        return categoriaMapper.toResponse(categoria);
    }

    public CategoriaResponse create(CategoriaRequest request) {
        Categoria categoria = new Categoria();
        categoria.setNombreCategoria(request.getNombreCategoria());
        categoria.setDescripcion(request.getDescripcion());
        categoria = categoriaRepository.save(categoria);
        return categoriaMapper.toResponse(categoria);
    }

    public CategoriaResponse update(Integer id, CategoriaRequest request) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria", id));
        categoria.setNombreCategoria(request.getNombreCategoria());
        categoria.setDescripcion(request.getDescripcion());
        categoria = categoriaRepository.save(categoria);
        return categoriaMapper.toResponse(categoria);
    }

    public void delete(Integer id) {
        if (!categoriaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Categoria", id);
        }
        categoriaRepository.deleteById(id);
    }
}
