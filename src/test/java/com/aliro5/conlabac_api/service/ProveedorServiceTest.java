package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.Proveedor;
import com.aliro5.conlabac_api.model.ProveedorId;
import com.aliro5.conlabac_api.repository.ProveedorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProveedorServiceTest {

    @Mock
    private ProveedorRepository repo;

    @InjectMocks
    private ProveedorService service;

    private Proveedor prov;

    @BeforeEach
    void setUp() {
        prov = new Proveedor();
        prov.setCif("B12345678");
        prov.setIdCentro(1);
        prov.setDenominacion("Proveedor Test");
    }

    @Test
    @DisplayName("Debe obtener un proveedor por su clave compuesta")
    void testObtenerPorId() {
        ProveedorId idCompuesto = new ProveedorId("B12345678", 1);
        when(repo.findById(idCompuesto)).thenReturn(Optional.of(prov));

        // Corrección para evitar 'Incompatible types'
        Proveedor encontrado = service.obtenerPorId("B12345678", 1).orElse(null);

        assertNotNull(encontrado);
        assertEquals("Proveedor Test", encontrado.getDenominacion());
    }
}