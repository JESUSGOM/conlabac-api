package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.Contacto;
import com.aliro5.conlabac_api.repository.ContactoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContactoServiceTest {

    @Mock
    private ContactoRepository contactoRepository;

    @InjectMocks
    private ContactoService contactoService;

    private Contacto contactoPrueba;

    @BeforeEach
    void setUp() {
        contactoPrueba = new Contacto();
        contactoPrueba.setId(1);
        contactoPrueba.setIdCentro(1);
        contactoPrueba.setNombre("Ramón");
        contactoPrueba.setApellido1("García");
        contactoPrueba.setApellido2("Pérez");
        contactoPrueba.setEmail("rgarcia@ejemplo.com");
    }

    @Test
    @DisplayName("Debe listar contactos por centro correctamente")
    void testListarPorCentro() {
        when(contactoRepository.findByIdCentro(1)).thenReturn(Arrays.asList(contactoPrueba));

        List<Contacto> resultado = contactoService.listarPorCentro(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Ramón", resultado.get(0).getNombre());
        verify(contactoRepository, times(1)).findByIdCentro(1);
    }

    @Test
    @DisplayName("Debe guardar un contacto nuevo")
    void testGuardarContacto() {
        when(contactoRepository.save(any(Contacto.class))).thenReturn(contactoPrueba);

        Contacto guardado = contactoService.guardar(new Contacto());

        assertNotNull(guardado);
        assertEquals("rgarcia@ejemplo.com", guardado.getEmail());
        verify(contactoRepository, times(1)).save(any(Contacto.class));
    }
}