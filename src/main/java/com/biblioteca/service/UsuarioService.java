package com.biblioteca.service;

import com.biblioteca.exception.ResourceNotFoundException;
import com.biblioteca.mapper.UsuarioMapper;
import com.biblioteca.model.dto.UsuarioResponse;
import com.biblioteca.model.entity.Usuario;
import com.biblioteca.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    public List<UsuarioResponse> findAll() {
        return usuarioRepository.findAll().stream()
                .map(usuarioMapper::toResponse)
                .toList();
    }

    public UsuarioResponse findById(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", id));
        return usuarioMapper.toResponse(usuario);
    }
}
