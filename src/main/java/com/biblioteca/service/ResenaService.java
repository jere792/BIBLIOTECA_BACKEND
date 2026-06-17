package com.biblioteca.service;

import com.biblioteca.exception.ResourceNotFoundException;
import com.biblioteca.model.dto.ResenaRequest;
import com.biblioteca.model.dto.ResenaResponse;
import com.biblioteca.model.entity.Libro;
import com.biblioteca.model.entity.Resena;
import com.biblioteca.model.entity.Usuario;
import com.biblioteca.repository.LibroRepository;
import com.biblioteca.repository.ResenaRepository;
import com.biblioteca.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ResenaService {

    private final ResenaRepository resenaRepository;
    private final UsuarioRepository usuarioRepository;
    private final LibroRepository libroRepository;

    public ResenaService(ResenaRepository resenaRepository,
                          UsuarioRepository usuarioRepository,
                          LibroRepository libroRepository) {
        this.resenaRepository = resenaRepository;
        this.usuarioRepository = usuarioRepository;
        this.libroRepository = libroRepository;
    }

    public List<ResenaResponse> findAll() {
        return resenaRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public ResenaResponse findById(Integer id) {
        Resena resena = resenaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resena", id));
        return toResponse(resena);
    }

    public List<ResenaResponse> findByLibro(Integer idLibro) {
        return resenaRepository.findByLibroIdLibroOrderByFechaDesc(idLibro).stream()
                .map(this::toResponse)
                .toList();
    }

    public List<ResenaResponse> findByUsuario(Integer idUsuario) {
        return resenaRepository.findByUsuarioIdUsuarioOrderByFechaDesc(idUsuario).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public ResenaResponse create(ResenaRequest request, Integer idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", idUsuario));
        Libro libro = libroRepository.findById(request.getIdLibro())
                .orElseThrow(() -> new ResourceNotFoundException("Libro", request.getIdLibro()));

        Resena resena = new Resena();
        resena.setUsuario(usuario);
        resena.setLibro(libro);
        resena.setComentario(request.getComentario());
        resena.setCalificacion(request.getCalificacion());
        resena = resenaRepository.save(resena);
        return toResponse(resena);
    }

    @Transactional
    public ResenaResponse update(Integer id, ResenaRequest request, Integer idUsuario) {
        Resena resena = resenaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resena", id));

        if (!resena.getUsuario().getIdUsuario().equals(idUsuario)) {
            throw new IllegalArgumentException("No puedes editar una resena de otro usuario");
        }

        Libro libro = libroRepository.findById(request.getIdLibro())
                .orElseThrow(() -> new ResourceNotFoundException("Libro", request.getIdLibro()));

        resena.setLibro(libro);
        resena.setComentario(request.getComentario());
        resena.setCalificacion(request.getCalificacion());
        resena = resenaRepository.save(resena);
        return toResponse(resena);
    }

    @Transactional
    public void delete(Integer id, Integer idUsuario) {
        Resena resena = resenaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resena", id));

        if (!resena.getUsuario().getIdUsuario().equals(idUsuario)) {
            throw new IllegalArgumentException("No puedes eliminar una resena de otro usuario");
        }

        resenaRepository.delete(resena);
    }

    @Transactional
    public void deleteAsAdmin(Integer id) {
        if (!resenaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Resena", id);
        }
        resenaRepository.deleteById(id);
    }

    private ResenaResponse toResponse(Resena resena) {
        ResenaResponse dto = new ResenaResponse();
        dto.setIdResena(resena.getIdResena());
        dto.setIdUsuario(resena.getUsuario().getIdUsuario());
        dto.setNombreUsuario(resena.getUsuario().getNombre() + " " + resena.getUsuario().getApellido());
        dto.setIdLibro(resena.getLibro().getIdLibro());
        dto.setTituloLibro(resena.getLibro().getTitulo());
        dto.setComentario(resena.getComentario());
        dto.setCalificacion(resena.getCalificacion());
        dto.setFecha(resena.getFecha());
        return dto;
    }
}
