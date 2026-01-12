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
@ActiveProfiles("test") // Utiliza application-test.properties con H2
class ContactoRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /api/contactos: Debe crear un contacto y devolverlo")
    void crearContactoOk() throws Exception {
        Contacto nuevo = new Contacto();
        nuevo.setIdCentro(1);
        nuevo.setNombre("Elena");
        nuevo.setApellido1("Sanz");
        nuevo.setApellido2("López");
        nuevo.setEmail("esanz@ejemplo.com");

        mockMvc.perform(post("/api/contactos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Elena"))
                .andExpect(jsonPath("$.email").value("esanz@ejemplo.com"));
    }

    @Test
    @DisplayName("GET /api/contactos/centro/1: Debe retornar status 200")
    void listarContactosOk() throws Exception {
        mockMvc.perform(get("/api/contactos/centro/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}