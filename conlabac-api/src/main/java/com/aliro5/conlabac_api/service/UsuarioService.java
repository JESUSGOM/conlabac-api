package com.aliro5.conlabac_api.service;


import com.aliro5.conlabac_api.model.Usuario;
import com.aliro5.conlabac_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;



    public Usuario validarLogin(String dni, String claveInput) {
        Optional<Usuario> opcional = usuarioRepository.findByDni(dni);

        if (opcional.isPresent()) {
            Usuario usuario = opcional.get();

            // CASO A: Usuario moderno (Ya tiene BCrypt) -> Solo comprobamos hash
            if (usuario.getClaveBcrypt() != null && !usuario.getClaveBcrypt().isEmpty()) {
                if (passwordEncoder.matches(claveInput, usuario.getClaveBcrypt())) {
                    return usuario;
                }
            }

            // CASO B: Usuario antiguo (Solo tiene clave plana) -> MIGRACIÓN AUTOMÁTICA
            else if (usuario.getClave() != null) {
                if (usuario.getClave().equals(claveInput)) {

                    // 1. Encriptamos la clave que acaba de usar y la guardamos en el campo seguro
                    usuario.setClaveBcrypt(passwordEncoder.encode(claveInput));

                    // 2. (Opcional) Borramos la clave plana para limpiar la BD de texto legible
                    // usuario.setClavePlana(null);

                    // 3. Guardamos los cambios en la BD
                    usuarioRepository.save(usuario);

                    return usuario;
                }
            }
        }

        return null;
    }
}