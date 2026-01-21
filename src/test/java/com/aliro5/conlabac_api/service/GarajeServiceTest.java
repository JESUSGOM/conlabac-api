package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.Garaje;
import com.aliro5.conlabac_api.repository.GarajeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GarajeServiceTest {

    @Mock
    private GarajeRepository repo;

    @InjectMocks
    private GarajeService service;

    private Garaje vehiculo;

    @BeforeEach
    void setUp() {
        vehiculo = new Garaje();
        vehiculo.setId(1);
        vehiculo.setMatricula("1234BBB");
        vehiculo.setFecha(LocalDate.now());
    }

    @Test
    @DisplayName("Debe limpiar matrícula (mayúsculas y sin espacios) al guardar")
    void testGuardarLimpiaMatricula() {
        Garaje nuevo = new Garaje();
        nuevo.setMatricula(" 5678 ccc ");
        when(repo.save(any(Garaje.class))).thenAnswer(i -> i.getArguments()[0]);

        Garaje guardado = service.guardar(nuevo);

        assertEquals("5678CCC", guardado.getMatricula());
        assertNotNull(guardado.getFechaTexto());
    }

    @Test
    @DisplayName("Debe retornar Garaje al buscar por ID")
    void testObtenerPorId() {
        when(repo.findById(1)).thenReturn(Optional.of(vehiculo));

        // Corrección del error de Optional:
        Garaje encontrado = service.obtenerPorId(1).orElse(null);

        assertNotNull(encontrado);
        assertEquals("1234BBB", encontrado.getMatricula());
    }
}