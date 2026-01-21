package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.Usuario;
import com.aliro5.conlabac_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public List<Usuario> listarTodos() {
        return repository.findAll();
    }

    public Optional<Usuario> obtenerPorDni(String dni) {
        return repository.findById(dni);
    }

    public Usuario guardar(Usuario usuario) {
        if (usuario.getClave() == null || usuario.getClave().isEmpty()) {
            usuario.setClave(generarClaveAlgoritmica(usuario.getDni()));
        }
        // Opcional: Aquí podrías asignar la clave al campo clavePlana si es necesario
        usuario.setClavePlana(usuario.getClave());
        return repository.save(usuario);
    }

    public void eliminar(String dni) {
        repository.deleteById(dni);
    }

    public Optional<Usuario> validarLogin(String dni, String clave) {
        // Llamamos al método corregido del repositorio
        return repository.findByDniAndClavePlana(dni, clave);
    }

    public String generarClaveAlgoritmica(String dni) {
        if (dni == null || dni.length() < 9) {
            return dni;
        }
        String numeros = dni.substring(0, 8);
        String letra = dni.substring(8).toUpperCase();
        String parte1 = numeros.substring(0, 2);
        String parte2 = numeros.substring(2);
        return parte1 + letra + parte2;
    }
}