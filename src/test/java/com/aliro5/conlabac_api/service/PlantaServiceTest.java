package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.Planta;
import com.aliro5.conlabac_api.repository.PlantaRepository;
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
class PlantaServiceTest {

    @Mock
    private PlantaRepository repo;

    @InjectMocks
    private PlantaService service;

    private Planta plantaMock;

    @BeforeEach
    void setUp() {
        plantaMock = new Planta();
        plantaMock.setId(1);
        plantaMock.setIdCentro(1);
        plantaMock.setNombrePlanta("Planta 1");
    }

    @Test
    @DisplayName("Debe guardar una planta correctamente")
    void testGuardarPlanta() {
        when(repo.save(any(Planta.class))).thenReturn(plantaMock);
        Planta resultado = service.guardar(new Planta());
        assertNotNull(resultado);
        verify(repo, times(1)).save(any(Planta.class));
    }

    @Test
    @DisplayName("Debe desempaquetar Optional correctamente")
    void testObtenerPorId() {
        when(repo.findById(1)).thenReturn(Optional.of(plantaMock));

        // CORRECCIÓN: Evita el error Incompatible types
        Planta encontrada = service.obtenerPorId(1).orElse(null);

        assertNotNull(encontrada);
        assertEquals("Planta 1", encontrada.getNombrePlanta());
    }
}