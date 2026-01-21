package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.model.Telefono;
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
public class TelefonoRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /api/telefonos: Debe crear aviso de llamada")
    void testRegistrarLlamadaAPI() throws Exception {
        Telefono t = new Telefono();
        t.setIdCentro(1);
        t.setEmisor("Amazon");
        t.setDestinatario("Recepcion");
        t.setMensaje("Paquete en puerta");

        mockMvc.perform(post("/api/telefonos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(t)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.emisor").value("Amazon"));
    }

    @Test
    @DisplayName("PUT /api/telefonos/1/comunicar: Debe marcar como entregado")
    void testMarcarComunicadoAPI() throws Exception {
        mockMvc.perform(put("/api/telefonos/1/comunicar")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}