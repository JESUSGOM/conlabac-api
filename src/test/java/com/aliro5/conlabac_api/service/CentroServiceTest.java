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

import java.util.Collections; // Mejor que Arrays.asList para un solo elemento
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
        // Uso de Collections.singletonList para evitar el warning de asList
        when(centroRepository.findAll()).thenReturn(Collections.singletonList(centroPrueba));

        List<Centro> resultado = centroService.listarTodos();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("Centro Norte", resultado.get(0).getDenominacion());
    }

    @Test
    @DisplayName("Debe encontrar un centro por su ID")
    void testBuscarPorId() {
        // Configuramos el mock para devolver un Optional
        when(centroRepository.findById(1)).thenReturn(Optional.of(centroPrueba));

        // CORRECCIÓN image_be8266.png: Extraemos el valor para que sea tipo Centro
        Centro encontrado = centroService.obtenerPorId(1).orElse(null);

        assertNotNull(encontrado);
        assertEquals("Madrid", encontrado.getProvincia());
    }
}