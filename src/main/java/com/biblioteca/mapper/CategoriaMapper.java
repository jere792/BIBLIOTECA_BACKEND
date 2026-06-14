package com.biblioteca.mapper;

import com.biblioteca.model.dto.CategoriaResponse;
import com.biblioteca.model.entity.Categoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {

    public CategoriaResponse toResponse(Categoria categoria) {
        CategoriaResponse dto = new CategoriaResponse();
        dto.setIdCategoria(categoria.getIdCategoria());
        dto.setNombreCategoria(categoria.getNombreCategoria());
        dto.setDescripcion(categoria.getDescripcion());
        return dto;
    }
}
