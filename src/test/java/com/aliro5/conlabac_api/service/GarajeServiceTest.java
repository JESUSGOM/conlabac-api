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

import java.time.LocalDateTime;
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
        // CORRECCIÓN: Usamos fechaEntrada y LocalDateTime
        vehiculo.setFechaEntrada(LocalDateTime.now());
    }

    @Test
    @DisplayName("Debe limpiar matrícula (mayúsculas y sin espacios) al guardar")
    void testGuardarLimpiaMatricula() {
        Garaje nuevo = new Garaje();
        nuevo.setMatricula(" 5678 ccc ");

        // Simulamos el guardado devolviendo el mismo objeto
        when(repo.save(any(Garaje.class))).thenAnswer(i -> i.getArguments()[0]);

        Garaje guardado = service.guardar(nuevo);

        // Verificamos que la matrícula se haya limpiado correctamente
        assertEquals("5678CCC", guardado.getMatricula());
        // Verificamos que se haya generado la fecha de entrada
        assertNotNull(guardado.getFechaEntrada());
        assertNotNull(guardado.getFechaTexto());
    }

    @Test
    @DisplayName("Debe retornar Garaje al buscar por ID")
    void testObtenerPorId() {
        when(repo.findById(1)).thenReturn(Optional.of(vehiculo));

        Optional<Garaje> encontradoOpt = service.obtenerPorId(1);

        assertTrue(encontradoOpt.isPresent());
        assertEquals("1234BBB", encontradoOpt.get().getMatricula());
    }

    @Test
    @DisplayName("Debe registrar la fecha de salida al ejecutar registrarSalida")
    void testRegistrarSalida() {
        when(repo.findById(1)).thenReturn(Optional.of(vehiculo));
        when(repo.save(any(Garaje.class))).thenAnswer(i -> i.getArguments()[0]);

        service.registrarSalida(1);

        // Verificamos que ahora el vehículo tenga fecha de salida
        assertNotNull(vehiculo.getFechaSalida());
        verify(repo, times(1)).save(vehiculo);
    }
}