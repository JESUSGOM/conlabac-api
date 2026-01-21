package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.Telefono;
import com.aliro5.conlabac_api.repository.TelefonoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TelefonoServiceTest {

    @Mock
    private TelefonoRepository repo;

    @InjectMocks
    private TelefonoService service;

    private Telefono mockTel;

    @BeforeEach
    void setUp() {
        mockTel = new Telefono();
        mockTel.setId(1);
        mockTel.setEmisor("Empresa X");
        mockTel.setDestinatario("Juan Garcia");
        mockTel.setMensaje("Llamar urgente");
        mockTel.setFecha("20260114");
        mockTel.setComunicado(0);
    }

    @Test
    @DisplayName("Debe registrar llamada y asignar estado pendiente (0)")
    void testRegistrarLlamada() {
        // CORRECCIÓN: any() asegura que Mockito devuelva el objeto tras los sets internos del service
        when(repo.save(any(Telefono.class))).thenReturn(mockTel);

        Telefono resultado = service.registrar(new Telefono());

        assertNotNull(resultado, "El resultado no debe ser null");
        assertEquals(0, resultado.getComunicado());
        verify(repo).save(any(Telefono.class));
    }

    @Test
    @DisplayName("Debe desempaquetar Optional sin error de tipos")
    void testObtenerPorId() {
        when(repo.findById(1)).thenReturn(Optional.of(mockTel));

        Telefono encontrado = service.obtenerPorId(1).orElse(null);

        assertNotNull(encontrado);
        assertEquals("Empresa X", encontrado.getEmisor());
    }
}