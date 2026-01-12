package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.Incidencia;
import com.aliro5.conlabac_api.repository.IncidenciaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IncidenciaServiceTest {

    @Mock
    private IncidenciaRepository repo;

    @Mock
    private EmailService emailService; // Mockeamos el servicio de email

    @InjectMocks
    private IncidenciaService service;

    @Test
    @DisplayName("Debe asignar Maria Carmen si el centro es 1")
    void testGuardarCentro1() {
        // GIVEN
        Incidencia inc = new Incidencia();
        inc.setIdCentro(1);
        inc.setTexto("Test");

        when(repo.save(any(Incidencia.class))).thenAnswer(i -> i.getArguments()[0]);

        // WHEN
        Incidencia resultado = service.guardar(inc);

        // THEN
        assertEquals("María Carmen Betancor Reula", resultado.getComunicadoA());
        assertEquals("cbetancor@itccanarias.org", resultado.getEmailComunica());
        assertEquals("EMAIL", resultado.getModoComunica());
        assertNotNull(resultado.getFecha()); // Verifica que generó la fecha yyyyMMdd
    }

    @Test
    @DisplayName("Debe asignar Adriana si el centro es 2")
    void testGuardarCentro2() {
        // GIVEN
        Incidencia inc = new Incidencia();
        inc.setIdCentro(2);

        when(repo.save(any(Incidencia.class))).thenAnswer(i -> i.getArguments()[0]);

        // WHEN
        Incidencia resultado = service.guardar(inc);

        // THEN
        assertEquals("Adriana Dominguez Sicilia", resultado.getComunicadoA());
        assertEquals("adominguez@itccanarias.org", resultado.getEmailComunica());
    }
}