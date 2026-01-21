package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.model.Planta;
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
class PlantaRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /api/plantas: Registrar una nueva planta")
    void testCrearPlanta() throws Exception {
        Planta p = new Planta();
        p.setIdCentro(1);
        p.setNombrePlanta("Sótano 1");

        mockMvc.perform(post("/api/plantas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(p)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombrePlanta").value("Sótano 1"));
    }

    @Test
    @DisplayName("GET /api/plantas/centro/1: Listar plantas del centro")
    void testListarPorCentro() throws Exception {
        mockMvc.perform(get("/api/plantas/centro/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}