package com.biblioteca.service;

import com.biblioteca.config.JwtUtil;
import com.biblioteca.exception.ResourceNotFoundException;
import com.biblioteca.mapper.UsuarioMapper;
import com.biblioteca.model.dto.*;
import com.biblioteca.model.entity.Usuario;
import com.biblioteca.model.enums.RolUsuario;
import com.biblioteca.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UsuarioMapper usuarioMapper;

    public AuthService(UsuarioRepository usuarioRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil,
                       UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.usuarioMapper = usuarioMapper;
    }

    public LoginResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByUsuario(request.getUsuario())
                .orElseThrow(() -> new ResourceNotFoundException("Credenciales invalidas"));

        if (!passwordEncoder.matches(request.getContrasena(), usuario.getContrasena())) {
            throw new IllegalArgumentException("Credenciales invalidas");
        }

        String token = jwtUtil.generateToken(
                usuario.getUsuario(),
                usuario.getIdUsuario(),
                usuario.getRol());

        UsuarioResponse userResponse = usuarioMapper.toResponse(usuario);
        return new LoginResponse(token, userResponse);
    }

    public LoginResponse register(RegistroRequest request) {
        if (usuarioRepository.existsByUsuario(request.getUsuario())) {
            throw new IllegalArgumentException("El usuario ya existe");
        }

        Usuario usuario = new Usuario();
        usuario.setUsuario(request.getUsuario());
        usuario.setContrasena(passwordEncoder.encode(request.getContrasena()));
        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setTelefono(request.getTelefono());
        usuario.setDni(request.getDni());
        usuario.setDireccion(request.getDireccion());
        usuario.setRolEnum(RolUsuario.CLIENTE);

        usuario = usuarioRepository.save(usuario);

        String token = jwtUtil.generateToken(
                usuario.getUsuario(),
                usuario.getIdUsuario(),
                usuario.getRol());

        UsuarioResponse userResponse = usuarioMapper.toResponse(usuario);
        return new LoginResponse(token, userResponse);
    }
}
