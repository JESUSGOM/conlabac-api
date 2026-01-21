package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.MovimientoEmpleado;
import com.aliro5.conlabac_api.repository.MovimientoEmpleadoRepository;
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
class MovimientoEmpleadoServiceTest {

    @Mock
    private MovimientoEmpleadoRepository repo;

    @InjectMocks
    private MovimientoEmpleadoService service;

    private MovimientoEmpleado movMock;

    @BeforeEach
    void setUp() {
        movMock = new MovimientoEmpleado();
        movMock.setId(100);
        movMock.setNifEmpleado("12345678Z");
        movMock.setCifProveedor("B12345678");
        movMock.setIdCentro(1);
        movMock.setFechaEntrada(LocalDateTime.now());
    }

    @Test
    @DisplayName("Debe registrar entrada si el empleado no está dentro")
    void testRegistrarEntradaNuevo() {
        when(repo.findTopByNifEmpleadoAndIdCentroAndFechaSalidaIsNullOrderByFechaEntradaDesc(anyString(), anyInt()))
                .thenReturn(Optional.empty());
        when(repo.save(any(MovimientoEmpleado.class))).thenReturn(movMock);

        MovimientoEmpleado resultado = service.registrarEntrada("12345678Z", "B12345678", 1);

        assertNotNull(resultado);
        verify(repo, times(1)).save(any(MovimientoEmpleado.class));
    }

    @Test
    @DisplayName("Debe gestionar Optional correctamente para evitar errores de tipo")
    void testObtenerPorId() {
        when(repo.findById(100)).thenReturn(Optional.of(movMock));

        // Solución al error Incompatible types
        MovimientoEmpleado encontrado = service.obtenerPorId(100).orElse(null);

        assertNotNull(encontrado);
        assertEquals("12345678Z", encontrado.getNifEmpleado());
    }
}