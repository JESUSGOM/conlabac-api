package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.model.Centro;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
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
class CentroRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /api/centros: Debe retornar lista de centros y status 200")
    void listarCentrosOk() throws Exception {
        mockMvc.perform(get("/api/centros")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("POST /api/centros: Debe crear un centro correctamente")
    void crearCentroOk() throws Exception {
        Centro nuevoCentro = new Centro();
        nuevoCentro.setDenominacion("Centro Sur");
        nuevoCentro.setDireccion("Avda. Sur 45");
        nuevoCentro.setCodigoPostal(41001);
        nuevoCentro.setProvincia("Sevilla");
        nuevoCentro.setTelefono("954123456");

        mockMvc.perform(post("/api/centros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevoCentro)))
                .andExpect(status().isOk()) // O isCreated() si devuelves 201
                .andExpect(jsonPath("$.denominacion").value("Centro Sur"));
    }
}