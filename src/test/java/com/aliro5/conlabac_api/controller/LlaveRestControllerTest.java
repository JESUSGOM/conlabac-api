package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.model.Llave;
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
class LlaveRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /api/llaves?centroId=1: Debe listar todas las llaves")
    void listarLlavesOk() throws Exception {
        mockMvc.perform(get("/api/llaves")
                        .param("centroId", "1") // Envío de parámetro @RequestParam
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST /api/llaves: Crear nueva llave")
    void crearLlaveOk() throws Exception {
        Llave ll = new Llave();
        ll.setCodigo("K-999");
        ll.setIdCentro(1);
        ll.setPuerta("Almacén Test");
        ll.setPlanta("Planta 0");
        ll.setCajetin(100);

        mockMvc.perform(post("/api/llaves")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ll)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigo").value("K-999"));
    }
}