package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.Movimiento;
import com.aliro5.conlabac_api.repository.MovimientoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovimientoServiceTest {

    @Mock
    private MovimientoRepository repo;

    @InjectMocks
    private MovimientoService service;

    private Movimiento movPrueba;

    @BeforeEach
    void setUp() {
        movPrueba = new Movimiento();
        movPrueba.setId(1);
        movPrueba.setIdCentro(1);
        movPrueba.setNombre("Carlos");
        movPrueba.setApellido1("Gomez");
        movPrueba.setFechaEntrada(LocalDateTime.now().minusHours(2));
        movPrueba.setFechaSalida(null); // Activo
    }

    @Test
    @DisplayName("Debe listar movimientos activos de hoy filtrando por fechas")
    void testListarActivosHoy() {
        when(repo.findByIdCentroAndFechaSalidaIsNullAndFechaEntradaBetween(any(), any(), any()))
                .thenReturn(Collections.singletonList(movPrueba));

        List<Movimiento> resultado = service.listarActivosHoyPorCentro(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertNull(resultado.get(0).getFechaSalida());
    }

    @Test
    @DisplayName("Debe registrar la salida estableciendo la fecha actual")
    void testRegistrarSalida() {
        when(repo.findById(1)).thenReturn(Optional.of(movPrueba));

        service.registrarSalida(1);

        assertNotNull(movPrueba.getFechaSalida());
        verify(repo, times(1)).save(movPrueba);
    }

    @Test
    @DisplayName("Debe desempaquetar Optional correctamente para evitar errores de tipo")
    void testObtenerPorId() {
        when(repo.findById(1)).thenReturn(Optional.of(movPrueba));

        // Solución al error Incompatible types
        Movimiento encontrado = service.obtenerPorId(1).orElse(null);

        assertNotNull(encontrado);
        assertEquals("Carlos", encontrado.getNombre());
    }
}