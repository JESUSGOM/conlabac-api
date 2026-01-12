package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.AperturaExtra;
import com.aliro5.conlabac_api.repository.AperturaExtraRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AperturaExtraServiceTest {

    @Mock
    private AperturaExtraRepository repo;

    @InjectMocks
    private AperturaExtraService service;

    private AperturaExtra apertura;

    @BeforeEach
    void setUp() {
        apertura = new AperturaExtra();
        apertura.setIdCentro(1);
        apertura.setFecha(LocalDate.now());
        apertura.setHoraInicio(LocalTime.of(22, 0));
        apertura.setHoraFinal(LocalTime.of(23, 30));
        apertura.setMotivo("Mantenimiento urgente de servidores");
    }

    @Test
    @DisplayName("Debe guardar una apertura extraordinaria correctamente")
    void testGuardarApertura() {
        when(repo.save(any(AperturaExtra.class))).thenReturn(apertura);

        AperturaExtra guardada = service.guardar(new AperturaExtra());

        assertNotNull(guardada);
        assertEquals("Mantenimiento urgente de servidores", guardada.getMotivo());
        verify(repo, times(1)).save(any(AperturaExtra.class));
    }

    @Test
    @DisplayName("Debe listar aperturas por ID de centro")
    void testListarPorCentro() {
        when(repo.findByIdCentro(1)).thenReturn(Arrays.asList(apertura));

        List<AperturaExtra> resultado = service.listarPorCentro(1);

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(repo, times(1)).findByIdCentro(1);
    }
}