package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.model.Usuario;
import com.aliro5.conlabac_api.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName; // IMPORTACIÓN QUE FALTABA
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
class UsuarioRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /api/usuarios: Debe crear usuario con clave algorítmica si no se envía clave")
    void crearUsuarioClaveAlgoritmica() throws Exception {
        Usuario nuevo = new Usuario();
        nuevo.setDni("12345678Z");
        nuevo.setNombre("Prueba");
        nuevo.setApellido1("Test");
        nuevo.setIdCentro(1);
        nuevo.setTipo("U");

        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isOk())
                // Verificamos que el campo claveBcrypt se haya generado
                .andExpect(jsonPath("$.claveBcrypt").exists())
                // Según tu lógica de controlador, clavePlana se pone a null
                .andExpect(jsonPath("$.clavePlana").doesNotExist());
    }

    @Test
    @DisplayName("GET /api/usuarios/{dni}: Retorna 404 si no existe")
    void obtenerInexistenteRetorna404() throws Exception {
        mockMvc.perform(get("/api/usuarios/noexiste"))
                .andExpect(status().isNotFound());
    }
}