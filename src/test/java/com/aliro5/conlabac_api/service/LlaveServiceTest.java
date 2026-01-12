package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.Llave;
import com.aliro5.conlabac_api.repository.LlaveRepository;
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
class LlaveServiceTest {

    @Mock
    private LlaveRepository llaveRepository;

    @InjectMocks
    private LlaveService llaveService;

    private Llave llaveMaestra;

    @BeforeEach
    void setUp() {
        llaveMaestra = new Llave();
        llaveMaestra.setId(1);
        llaveMaestra.setCodigo("K-101");
        llaveMaestra.setIdCentro(1);
        llaveMaestra.setPuerta("Despacho Principal");
        llaveMaestra.setPlanta("Primera");
        llaveMaestra.setCajetin(5);
    }

    @Test
    @DisplayName("Debe encontrar una llave por su código único")
    void testBuscarPorCodigo() {
        when(llaveRepository.findByCodigo("K-101")).thenReturn(Optional.of(llaveMaestra));

        Llave encontrada = llaveService.obtenerPorCodigo("K-101");

        assertNotNull(encontrada);
        assertEquals("Despacho Principal", encontrada.getPuerta());
        verify(llaveRepository, times(1)).findByCodigo("K-101");
    }

    @Test
    @DisplayName("Debe listar todas las llaves de un centro específico")
    void testListarPorCentro() {
        when(llaveRepository.findByIdCentro(1)).thenReturn(Arrays.asList(llaveMaestra));

        List<Llave> resultado = llaveService.listarPorCentro(1);

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals(5, resultado.get(0).getCajetin());
    }
}