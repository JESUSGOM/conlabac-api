package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.Alquiler;
import com.aliro5.conlabac_api.repository.AlquilerRepository;
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
class AlquilerServiceTest {

    @Mock
    private AlquilerRepository repo;

    @InjectMocks
    private AlquilerService service;

    private Alquiler alquilerPrueba;

    @BeforeEach
    void setUp() {
        // Inicializamos un objeto de prueba común
        alquilerPrueba = new Alquiler();
        // Asume que tu entidad Alquiler tiene estos setters (ajusta según tus campos reales)
        alquilerPrueba.setIdCentro(1);
        // alquilerPrueba.setDescripcion("Alquiler de prueba");
    }

    @Test
    @DisplayName("Debe listar alquileres por el ID del centro correctamente")
    void testListarPorCentro() {
        // GIVEN
        when(repo.findByIdCentro(1)).thenReturn(Arrays.asList(alquilerPrueba));

        // WHEN
        List<Alquiler> resultado = service.listarPorCentro(1);

        // THEN
        assertNotNull(resultado, "La lista no debería ser nula");
        assertEquals(1, resultado.size(), "Debería retornar exactamente 1 alquiler");
        assertEquals(1, resultado.get(0).getIdCentro());
        verify(repo, times(1)).findByIdCentro(1);
    }

    @Test
    @DisplayName("Debe guardar un nuevo alquiler y retornar el objeto guardado")
    void testGuardarAlquiler() {
        // GIVEN
        when(repo.save(any(Alquiler.class))).thenReturn(alquilerPrueba);

        // WHEN
        Alquiler guardado = service.guardar(new Alquiler());

        // THEN
        assertNotNull(guardado, "El objeto guardado no debería ser nulo");
        verify(repo, times(1)).save(any(Alquiler.class));
    }

    @Test
    @DisplayName("Debe llamar al repositorio para eliminar un alquiler por ID")
    void testEliminarAlquiler() {
        // GIVEN
        Integer idAEliminar = 100;
        doNothing().when(repo).deleteById(idAEliminar);

        // WHEN
        service.eliminar(idAEliminar);

        // THEN
        verify(repo, times(1)).deleteById(idAEliminar);
    }

    @Test
    @DisplayName("Debe retornar una lista vacía si el centro no tiene alquileres")
    void testListarPorCentroVacio() {
        // GIVEN
        when(repo.findByIdCentro(99)).thenReturn(Arrays.asList());

        // WHEN
        List<Alquiler> resultado = service.listarPorCentro(99);

        // THEN
        assertTrue(resultado.isEmpty(), "La lista debería estar vacía");
        verify(repo, times(1)).findByIdCentro(99);
    }
}