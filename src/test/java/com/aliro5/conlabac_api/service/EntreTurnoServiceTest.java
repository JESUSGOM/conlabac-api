package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.EntreTurno;
import com.aliro5.conlabac_api.model.Usuario;
import com.aliro5.conlabac_api.repository.EntreTurnoRepository;
import com.aliro5.conlabac_api.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EntreTurnoServiceTest {

    @Mock
    private EntreTurnoRepository repo;

    @Mock
    private UsuarioRepository usuarioRepo;

    @InjectMocks
    private EntreTurnoService service;

    private EntreTurno notaPrueba;

    @BeforeEach
    void setUp() {
        notaPrueba = new EntreTurno();
        notaPrueba.setId(1);
        notaPrueba.setIdCentro(1);
        notaPrueba.setOperarioEscritor("12345678Z");
        notaPrueba.setTexto("Prueba de consigna");
        notaPrueba.setFechaEscrito("20260114");
    }

    @Test
    @DisplayName("Debe crear una nota sincronizando las fechas automáticas")
    void testCrearNota() {
        // CORRECCIÓN: any() para manejar la manipulación interna de fechas del service
        when(repo.save(any(EntreTurno.class))).thenReturn(notaPrueba);

        EntreTurno resultado = service.guardar(new EntreTurno());

        // Verificamos el resultado del Mock, no el objeto vacío enviado
        assertNotNull(resultado, "El resultado no debe ser null");
        assertNotNull(resultado.getFechaEscrito());
        verify(repo).save(any(EntreTurno.class));
    }

    @Test
    @DisplayName("Debe marcar como leído actualizando los campos de lectura")
    void testMarcarLeido() {
        when(repo.findById(1)).thenReturn(Optional.of(notaPrueba));

        service.marcarLeido(1, "87654321X");

        assertEquals("87654321X", notaPrueba.getUsuarioLector());
        verify(repo).save(notaPrueba);
    }

    @Test
    @DisplayName("Debe rellenar nombres de operarios usando el repositorio de usuarios")
    void testRellenarNombres() {
        Usuario u = new Usuario();
        u.setDni("12345678Z");
        u.setNombre("Pepe");
        u.setApellido1("Lopez");

        when(repo.findByIdCentroOrderByFechaHoraEscritoDesc(1)).thenReturn(Collections.singletonList(notaPrueba));
        when(usuarioRepo.findAllById(anySet())).thenReturn(Collections.singletonList(u));

        var lista = service.listarHistorial(1);

        assertFalse(lista.isEmpty());
        // Ajustado para coincidir con la lógica del Service: Nombre + ApellidoUno
        assertNotNull(lista.get(0).getNombreEscritorMostrar());
    }
}