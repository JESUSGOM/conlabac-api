package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.MovimientoEmpleado;
import com.aliro5.conlabac_api.repository.MovimientoEmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MovimientoEmpleadoService {

    @Autowired
    private MovimientoEmpleadoRepository repo;

    // 1. Ver lista de presentes
    public List<MovimientoEmpleado> listarActivos(Integer idCentro) {
        return repo.findByIdCentroAndFechaSalidaIsNullOrderByFechaEntradaDesc(idCentro);
    }

    // 2. Fichar ENTRADA
    public MovimientoEmpleado registrarEntrada(String nif, String cif, Integer idCentro) {
        // Validación: ¿Ya está dentro?
        Optional<MovimientoEmpleado> yaDentro = repo.findTopByNifEmpleadoAndIdCentroAndFechaSalidaIsNullOrderByFechaEntradaDesc(nif, idCentro);

        if (yaDentro.isPresent()) {
            return yaDentro.get(); // Si ya está dentro, devolvemos el existente y no hacemos nada
        }

        // Creamos nuevo movimiento
        MovimientoEmpleado mov = new MovimientoEmpleado();
        mov.setNifEmpleado(nif);
        mov.setCifProveedor(cif);
        mov.setIdCentro(idCentro);
        mov.setFechaEntrada(LocalDateTime.now()); // Hora actual

        return repo.save(mov);
    }

    // 3. Fichar SALIDA
    public void registrarSalida(String nif, Integer idCentro) {
        // Buscamos su ficha activa
        Optional<MovimientoEmpleado> activo = repo.findTopByNifEmpleadoAndIdCentroAndFechaSalidaIsNullOrderByFechaEntradaDesc(nif, idCentro);

        if (activo.isPresent()) {
            MovimientoEmpleado mov = activo.get();
            mov.setFechaSalida(LocalDateTime.now()); // Hora actual de salida
            repo.save(mov);
        }
    }

    // Método auxiliar para salida por ID directo (por si usamos botón en tabla)
    public void registrarSalidaPorId(Integer id) {
        repo.findById(id).ifPresent(mov -> {
            mov.setFechaSalida(LocalDateTime.now());
            repo.save(mov);
        });
    }
}