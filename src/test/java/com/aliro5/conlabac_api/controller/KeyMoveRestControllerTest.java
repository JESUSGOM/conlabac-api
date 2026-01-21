package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.model.KeyMove;
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
class KeyMoveRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Debe registrar y listar movimientos de llaves correctamente")
    void testFlujoCompletoLlave() throws Exception {
        KeyMove km = new KeyMove();

        // CORRECCIÓN 1: El valor anterior 'K-API-TEST' (10 chars) excedía el límite de 8 de la columna KEYLLVORDEN
        String codigoValido = "K-TEST";
        km.setCodigoLlave(codigoValido);
        km.setIdCentro(1);
        km.setNombre("Jesus");
        km.setApellido1("Gomez");

        // 1. Probar entrega (POST)
        // Se asegura que la URL sea la correcta para evitar el 404
        mockMvc.perform(post("/api/keymoves")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(km)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigoLlave").value(codigoValido));

        // 2. Probar listado de activos (GET)
        mockMvc.perform(get("/api/keymoves/activos")
                        .param("centroId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}