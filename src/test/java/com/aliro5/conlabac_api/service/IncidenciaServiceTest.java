package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.Incidencia;
import com.aliro5.conlabac_api.repository.IncidenciaRepository;
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
class IncidenciaServiceTest {

    @Mock
    private IncidenciaRepository repo;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private IncidenciaService service;

    private Incidencia mockInc;

    @BeforeEach
    void setUp() {
        mockInc = new Incidencia();
        mockInc.setId(1);
        mockInc.setIdCentro(1);
        mockInc.setTexto("Incidencia de prueba");
    }

    @Test
    @DisplayName("Debe guardar e invocar el servicio de email")
    void testGuardarIncidenciaCompleto() {
        when(repo.save(any(Incidencia.class))).thenReturn(mockInc);

        Incidencia resultado = service.guardar(new Incidencia());

        assertNotNull(resultado);
        verify(emailService, times(1)).procesarIncidencia(any(Incidencia.class));
    }

    @Test
    @DisplayName("Debe desempaquetar Optional correctamente")
    void testObtenerPorId() {
        when(repo.findById(1)).thenReturn(Optional.of(mockInc));

        // Corrección Incompatible types: Optional<Incidencia> vs Incidencia
        Incidencia encontrada = service.obtenerPorId(1).orElse(null);

        assertNotNull(encontrada);
        assertEquals(1, encontrada.getId());
    }
}