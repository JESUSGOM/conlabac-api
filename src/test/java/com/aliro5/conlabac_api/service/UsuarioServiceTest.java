package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.Usuario;
import com.aliro5.conlabac_api.repository.UsuarioRepository;
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
public class UsuarioServiceTest {

    @Mock private UsuarioRepository repo;
    @Mock private PasswordEncoder encoder;
    @InjectMocks private UsuarioService service;

    @Test
    @DisplayName("Debe generar la clave algorítmica correctamente")
    void testAlgoritmoClave() {
        // Ejemplo: DNI 12345678Z -> Suma 36 -> 9. Letra Z en pos 8.
        String clave = service.generarClaveAlgoritmica("12345678Z");
        assertEquals("12345678Z", clave);
    }

    @Test
    @DisplayName("Debe evitar error de tipos al obtener por DNI")
    void testObtenerDni() {
        Usuario u = new Usuario();
        u.setDni("123X");
        when(repo.findById("123X")).thenReturn(Optional.of(u));

        // Corrección Incompatible types
        Usuario resultado = service.obtenerPorDni("123X").orElse(null);
        assertNotNull(resultado);
    }
}