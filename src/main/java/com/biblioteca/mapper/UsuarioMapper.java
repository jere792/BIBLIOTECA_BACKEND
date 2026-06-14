package com.biblioteca.mapper;

import com.biblioteca.model.dto.UsuarioResponse;
import com.biblioteca.model.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioResponse toResponse(Usuario usuario) {
        UsuarioResponse dto = new UsuarioResponse();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setUsuario(usuario.getUsuario());
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setTelefono(usuario.getTelefono());
        dto.setDni(usuario.getDni());
        dto.setDireccion(usuario.getDireccion());
        dto.setRol(usuario.getRol());
        return dto;
    }
}
