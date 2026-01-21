package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.model.Incidencia;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    void testCrearIncidenciaFlow() throws Exception {
        Incidencia inc = new Incidencia();
        inc.setIdCentro(1);
        inc.setTexto("Avería detectada");
        inc.setUsuario("Vigilante 01");

        mockMvc.perform(post("/api/incidencias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inc)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comunicadoA").value("María Carmen Betancor Reula"));
    }
}