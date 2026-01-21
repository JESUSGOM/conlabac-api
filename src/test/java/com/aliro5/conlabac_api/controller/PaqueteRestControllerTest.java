package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.model.Paquete;
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
class PaqueteRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /api/paqueteria: Registrar nuevo paquete")
    void testRecibirPaqueteAPI() throws Exception {
        Paquete p = new Paquete();
        p.setIdCentro(1);
        p.setEmisor("Amazon");
        p.setDestinatario("Marta Lopez");
        p.setMensajeria("Correos");
        p.setBultos(1);
        p.setTipo("Caja");
        p.setOperario("12345678Z");

        mockMvc.perform(post("/api/paqueteria")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(p)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.destinatario").value("Marta Lopez"))
                .andExpect(jsonPath("$.comunicado").value("NO"));
    }

    @Test
    @DisplayName("GET /api/paqueteria/pendientes: Listar paquetes sin entregar")
    void testListarPendientesAPI() throws Exception {
        mockMvc.perform(get("/api/paqueteria/pendientes")
                        .param("centroId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}