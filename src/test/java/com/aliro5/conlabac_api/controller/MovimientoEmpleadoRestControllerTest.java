package com.aliro5.conlabac_api.controller;

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
class MovimientoEmpleadoRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /api/contratas/entrada: Registrar fichaje de entrada")
    void testFicharEntrada() throws Exception {
        Map<String, Object> body = new HashMap<>();
        body.put("nif", "87654321X");
        body.put("cif", "A555666");
        body.put("centroId", 1);

        mockMvc.perform(post("/api/contratas/entrada")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nifEmpleado").value("87654321X"));
    }

    @Test
    @DisplayName("GET /api/contratas/activos: Listar presentes en centro")
    void testListarActivos() throws Exception {
        mockMvc.perform(get("/api/contratas/activos")
                        .param("centroId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}