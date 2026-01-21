package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.model.Contacto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ContactoRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /api/contactos: Debe crear un contacto")
    void crearContactoOk() throws Exception {
        Contacto nuevo = new Contacto();
        // Sincronizamos con los campos de la base de datos y el Service
        nuevo.setIdCentro(1);
        nuevo.setNombre("Elena");
        nuevo.setApellido1("Sanz"); // Corregido de setApellido1 a setApellidoUno
        nuevo.setApellido2("López"); // Corregido de setApellido2 a setApellidoDos
        nuevo.setEmail("elena@ejemplo.com");

        mockMvc.perform(post("/api/contactos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Elena"));
    }

    @Test
    @DisplayName("GET /api/contactos?centroId=1: Debe retornar status 200")
    void listarContactosOk() throws Exception {
        // CORRECCIÓN VITAL: Cambiamos de /api/contactos/centro/1 a la ruta real con parámetro
        // Esto soluciona el error 404 (No static resource api/contactos/centro/1)
        mockMvc.perform(get("/api/contactos")
                        .param("centroId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}