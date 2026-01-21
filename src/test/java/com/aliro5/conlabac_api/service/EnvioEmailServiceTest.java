package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.EnvioEmail;
import com.aliro5.conlabac_api.repository.EnvioEmailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnvioEmailServiceTest {

    @Mock
    private EnvioEmailRepository repo;

    @InjectMocks
    private EnvioEmailService service;

    private EnvioEmail logPrueba;

    @BeforeEach
    void setUp() {
        logPrueba = new EnvioEmail();
        logPrueba.setId(1);
        logPrueba.setDestinatario("test@itccanarias.org");
        logPrueba.setEmisor("12345678Z");
        logPrueba.setTexto("Cuerpo del mensaje de prueba");
        logPrueba.setFecha("20260113");
        logPrueba.setHora("093000");
        logPrueba.setFechaHoraDt(LocalDateTime.now());
    }

    @Test
    @DisplayName("Debe guardar un log de email correctamente")
    void testGuardarLog() {
        when(repo.save(any(EnvioEmail.class))).thenReturn(logPrueba);

        EnvioEmail resultado = service.guardarLog(new EnvioEmail());

        assertNotNull(resultado);
        assertEquals("test@itccanarias.org", resultado.getDestinatario());
        verify(repo, times(1)).save(any(EnvioEmail.class));
    }

    @Test
    @DisplayName("Debe listar envíos por destinatario")
    void testListarPorDestinatario() {
        // Uso de Collections.singletonList para evitar el warning de 'asList'
        when(repo.findByDestinatarioOrderByFechaHoraDtDesc("test@itccanarias.org"))
                .thenReturn(Collections.singletonList(logPrueba));

        List<EnvioEmail> resultado = service.listarPorDestinatario("test@itccanarias.org");

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("12345678Z", resultado.get(0).getEmisor());
    }
}