package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.model.Incidencia;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class IncidenciaRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /api/incidencias: Debe persistir y devolver la incidencia")
    void testCrearIncidencia() throws Exception {
        Incidencia nueva = new Incidencia();
        nueva.setIdCentro(1);
        nueva.setTexto("Incidencia de prueba");
        nueva.setUsuario("Vigilante_Test");

        mockMvc.perform(post("/api/incidencias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nueva)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comunicadoA").value("María Carmen Betancor Reula"))
                .andExpect(jsonPath("$.modoComunica").value("EMAIL"));
    }
}