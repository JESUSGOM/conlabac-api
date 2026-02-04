package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.Garaje;
import com.aliro5.conlabac_api.repository.GarajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class GarajeService {

    @Autowired
    private GarajeRepository repo;

    public List<Garaje> listarActivos() {
        return repo.findByFechaSalidaIsNullOrderByFechaEntradaDesc();
    }

    public List<Garaje> listarTodos() {
        return repo.findAllByOrderByFechaEntradaDesc();
    }

    public Optional<Garaje> obtenerPorId(Integer id) {
        return repo.findById(id);
    }

    @Transactional
    public Garaje guardar(Garaje g) {
        LocalDateTime ahora = LocalDateTime.now();

        // 1. Forzamos la fecha de entrada si viene vacía (Alta nueva)
        if (g.getFechaEntrada() == null) {
            g.setFechaEntrada(ahora);
        }

        // 2. REGLA CRÍTICA: La base de datos exige GrjFecha (varchar 8).
        // Si no se rellena, el INSERT fallará.
        g.setFechaTexto(ahora.format(DateTimeFormatter.ofPattern("yyyyMMdd")));

        // 3. Limpieza de matrícula
        if (g.getMatricula() != null) {
            g.setMatricula(g.getMatricula().toUpperCase().trim().replace(" ", ""));
        }

        return repo.save(g);
    }

    @Transactional
    public void registrarSalida(Integer id) {
        repo.findById(id).ifPresent(v -> {
            v.setFechaSalida(LocalDateTime.now());
            repo.save(v);
        });
    }

    public void ejecutarMantenimientoFechas() {
        try {
            repo.corregirFechas();
        } catch (Exception e) {
            System.err.println("Error en mantenimiento de fechas garaje: " + e.getMessage());
        }
    }
}