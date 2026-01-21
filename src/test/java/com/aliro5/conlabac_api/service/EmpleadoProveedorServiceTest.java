package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.EmpleadoProveedor;
import com.aliro5.conlabac_api.repository.EmpleadoProveedorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmpleadoProveedorServiceTest {

    @Mock
    private EmpleadoProveedorRepository repo;

    @InjectMocks
    private EmpleadoProveedorService service;

    private EmpleadoProveedor empleado;

    @BeforeEach
    void setUp() {
        empleado = new EmpleadoProveedor();
        empleado.setId(1);
        empleado.setNombre("Carlos");
        empleado.setApellido1("Ruiz");
        empleado.setCifProveedor("B12345678");
        empleado.setIdCentro(1);
        empleado.setFechaAcceso(LocalDate.now());
    }

    @Test
    @DisplayName("Debe listar empleados por CIF de proveedor y centro")
    void testListarPorCifYCentro() {
        when(repo.findByCifProveedorAndIdCentro("B12345678", 1))
                .thenReturn(Collections.singletonList(empleado));

        List<EmpleadoProveedor> resultado = service.listarPorProveedorEnCentro("B12345678", 1);

        assertFalse(resultado.isEmpty());
        assertEquals("Carlos", resultado.get(0).getNombre());
    }

    @Test
    @DisplayName("Debe buscar un empleado por su ID y devolver el objeto directamente")
    void testBuscarPorId() {
        when(repo.findById(1)).thenReturn(Optional.of(empleado));

        // Aplicamos la corrección de Optional detectada en tus errores previos
        EmpleadoProveedor encontrado = service.obtenerPorId(1).orElse(null);

        assertNotNull(encontrado);
        assertEquals("Ruiz", encontrado.getApellido1());
    }
}