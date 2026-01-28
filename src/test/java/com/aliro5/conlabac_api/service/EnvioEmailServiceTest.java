package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.EnvioEmail;
import com.aliro5.conlabac_api.model.dto.EmailRequestDTO;
import com.aliro5.conlabac_api.repository.EnvioEmailRepository;
import com.aliro5.conlabac_api.repository.IncidenciaRepository;
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
    private EnvioEmailRepository emailRepo;

    @Mock
    private IncidenciaRepository incidenciaRepo;

    @InjectMocks
    private EnvioEmailService service;

    private EnvioEmail logPrueba;
    private EmailRequestDTO dtoPrueba;

    @BeforeEach
    void setUp() {
        logPrueba = new EnvioEmail();
        logPrueba.setDestinatario("test@itccanarias.org");
        logPrueba.setEmisor("12345678Z");
        logPrueba.setTexto("Mensaje de prueba");
        logPrueba.setFecha("20260127");
        logPrueba.setHora("120000");

        dtoPrueba = new EmailRequestDTO();
        dtoPrueba.setDestinatario("test@itccanarias.org");
        dtoPrueba.setAsunto("Asunto Test");
        dtoPrueba.setMensaje("Cuerpo Test");
        dtoPrueba.setIdCentro(1);
        dtoPrueba.setDniEmisor("12345678Z");
    }

    @Test
    @DisplayName("Debe listar envíos por destinatario")
    void testListarPorDestinatario() {
        when(emailRepo.findByDestinatarioOrderByFechaHoraDtDesc("test@itccanarias.org"))
                .thenReturn(Collections.singletonList(logPrueba));

        List<EnvioEmail> resultado = service.listarPorDestinatario("test@itccanarias.org");

        assertFalse(resultado.isEmpty());
        assertEquals("12345678Z", resultado.get(0).getEmisor());
    }

    @Test
    @DisplayName("Debe procesar el envío de email libre")
    void testEnviarEmailLibre() throws Exception {
        // Ejecutamos el método corregido
        service.enviarEmailLibre(dtoPrueba);

        // Verificamos que se intentó guardar en el repositorio de emails
        verify(emailRepo, atLeastOnce()).save(any(EnvioEmail.class));
    }

    @Test
    @DisplayName("Debe procesar el envío de incidencia")
    void testEnviarYRegistrarIncidencia() throws Exception {
        // Ejecutamos el método corregido para incidencias
        service.enviarYRegistrarIncidencia(dtoPrueba);

        // Verificamos que se guardó tanto en la tabla de incidencias como en la de envíos
        verify(incidenciaRepo, atLeastOnce()).save(any());
        verify(emailRepo, atLeastOnce()).save(any(EnvioEmail.class));
    }
}