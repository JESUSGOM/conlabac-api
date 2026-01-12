package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.Centro;
import com.aliro5.conlabac_api.repository.CentroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CentroServiceTest {

    @Mock
    private CentroRepository centroRepository;

    @InjectMocks
    private CentroService centroService;

    private Centro centroPrueba;

    @BeforeEach
    void setUp() {
        centroPrueba = new Centro(1, "Centro Norte", "Calle Falsa 123", 28001, "Madrid", "912345678", null);
    }

    @Test
    @DisplayName("Debe retornar todos los centros")
    void testListarTodos() {
        when(centroRepository.findAll()).thenReturn(Arrays.asList(centroPrueba));

        List<Centro> resultado = centroService.listarTodos(); // Ajusta según tu nombre de método

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("Centro Norte", resultado.get(0).getDenominacion());
    }

    @Test
    @DisplayName("Debe encontrar un centro por su ID")
    void testBuscarPorId() {
        when(centroRepository.findById(1)).thenReturn(Optional.of(centroPrueba));

        Centro encontrado = centroService.obtenerPorId(1);

        assertNotNull(encontrado);
        assertEquals("Madrid", encontrado.getProvincia());
    }
}