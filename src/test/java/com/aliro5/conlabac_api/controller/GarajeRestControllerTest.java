package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.model.Garaje;
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
class GarajeRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCrearRegistroYVerificarJSON() throws Exception {
        Garaje g = new Garaje();
        g.setMatricula("9999XYZ");
        g.setNombreConductor("Test User");
        g.setEmpresa("ITC");
        g.setMarca("Ford");
        g.setModelo("Focus");
        g.setColor("Rojo");

        mockMvc.perform(post("/api/garaje")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(g)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.matricula").value("9999XYZ"));
    }
}