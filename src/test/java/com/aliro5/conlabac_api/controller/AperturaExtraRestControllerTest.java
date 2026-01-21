package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.model.AperturaExtra;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AperturaExtraRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /api/aperturas-extra: Debe crear registro con fechas y horas")
    void crearAperturaOk() throws Exception {
        AperturaExtra nueva = new AperturaExtra();
        nueva.setIdCentro(1);
        nueva.setFecha(LocalDate.of(2026, 1, 12));
        nueva.setHoraInicio(LocalTime.of(18, 0));
        nueva.setHoraFinal(LocalTime.of(20, 0));
        nueva.setMotivo("Acceso extraordinario");

        mockMvc.perform(post("/api/aperturas-extra")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nueva))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.motivo").value("Acceso extraordinario"));
    }

    @Test
    @DisplayName("GET /api/aperturas-extra?centroId=1: Debe retornar lista")
    void listarAperturasOk() throws Exception {
        // CORRECCIÓN: Se usa .param("centroId", "1") para coincidir con el controlador
        mockMvc.perform(get("/api/aperturas-extra")
                        .param("centroId", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}