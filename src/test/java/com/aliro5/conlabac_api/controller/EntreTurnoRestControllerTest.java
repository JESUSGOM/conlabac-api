package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.model.EntreTurno;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class EntreTurnoRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /api/entre-turnos: Crear nota correctamente")
    void testCrearNotaAPI() throws Exception {
        EntreTurno nueva = new EntreTurno();
        nueva.setIdCentro(1);
        nueva.setOperarioEscritor("12345678Z");
        nueva.setTexto("Consigna API");

        mockMvc.perform(post("/api/entre-turnos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nueva))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /api/entre-turnos/pendientes: Listar notas sin leer")
    void testListarPendientesAPI() throws Exception {
        mockMvc.perform(get("/api/entre-turnos/pendientes")
                        .param("centroId", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("PUT /api/entre-turnos/{id}/leer: Confirmar lectura")
    void testMarcarLeidoAPI() throws Exception {
        // CORRECCIÓN: El controlador espera un Map en el body, no un parámetro DNI
        Map<String, String> body = new HashMap<>();
        body.put("lector", "87654321X");

        mockMvc.perform(put("/api/entre-turnos/1/leer") // Cambiado a PUT para coincidir con el controlador
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}