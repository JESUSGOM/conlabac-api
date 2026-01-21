package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.model.Proveedor;
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
class ProveedorRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /api/proveedores: Registrar nuevo proveedor")
    void testCrearProveedorAPI() throws Exception {
        Proveedor p = new Proveedor();
        p.setCif("A88888888");
        p.setIdCentro(1);
        p.setDenominacion("Proveedor Integración");

        mockMvc.perform(post("/api/proveedores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(p)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.denominacion").value("Proveedor Integración"));
    }

    @Test
    @DisplayName("GET /api/proveedores: Listar proveedores de un centro")
    void testListarProveedoresAPI() throws Exception {
        mockMvc.perform(get("/api/proveedores")
                        .param("centroId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}