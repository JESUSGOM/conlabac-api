package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.Usuario;
import com.aliro5.conlabac_api.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuarioBase;

    @BeforeEach
    void setUp() {
        usuarioBase = new Usuario();
        usuarioBase.setDni("12345678Z");
        usuarioBase.setNombre("Test");
    }

    @Test
    @DisplayName("Login con BCrypt: Debe retornar usuario si la clave coincide")
    void loginBcryptExitoso() {
        usuarioBase.setClaveBcrypt("$2a$10$encodedHash");
        when(usuarioRepository.findByDni("12345678Z")).thenReturn(Optional.of(usuarioBase));
        when(passwordEncoder.matches("password123", "$2a$10$encodedHash")).thenReturn(true);

        Usuario resultado = usuarioService.validarLogin("12345678Z", "password123");

        assertNotNull(resultado);
        assertEquals("12345678Z", resultado.getDni());
    }

    @Test
    @DisplayName("Migración: Debe encriptar clave plana y guardar si coincide")
    void loginMigracionClavePlana() {
        // Simulamos usuario con clave plana (guardada en el campo 'clave' según tu Service)
        usuarioBase.setClave("12345678Z");
        usuarioBase.setClaveBcrypt(null);

        when(usuarioRepository.findByDni("12345678Z")).thenReturn(Optional.of(usuarioBase));
        when(passwordEncoder.encode("12345678Z")).thenReturn("$2a$10$newHash");

        Usuario resultado = usuarioService.validarLogin("12345678Z", "12345678Z");

        assertNotNull(resultado);
        assertEquals("$2a$10$newHash", resultado.getClaveBcrypt());
        verify(usuarioRepository, times(1)).save(usuarioBase);
    }
}