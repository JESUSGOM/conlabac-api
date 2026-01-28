package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.Movimiento;
import com.aliro5.conlabac_api.repository.MovimientoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class MovimientoService {

    @Autowired
    private MovimientoRepository repo;

    private static final Logger logTF = LoggerFactory.getLogger("TenerifeLogger");
    private static final Logger logGC = LoggerFactory.getLogger("GranCanariaLogger");

    public List<Movimiento> listarTodos() {
        return repo.findAll();
    }

    public List<Movimiento> listarPorCentro(Integer idCentro) {
        return repo.findByIdCentro(idCentro);
    }

    public Movimiento guardar(Movimiento mov) {
        // Primero nos aseguramos de que la fecha de entrada esté puesta si es nuevo
        if (mov.getFechaEntrada() == null) {
            mov.setFechaEntrada(LocalDateTime.now());
        }

        Movimiento m = repo.save(mov);

        // CORRECCIÓN: Usamos nombre y apellidos ya que la entidad Movimiento no tiene campo DNI
        String nombreCompleto = m.getNombre() + " " + (m.getApellido1() != null ? m.getApellido1() : "");
        String msg = "MOVIMIENTO registrado - Persona: " + nombreCompleto + " - Motivo: " + m.getMotivo();

        if (m.getIdCentro() != null && m.getIdCentro() == 1) {
            logTF.info(msg);
        } else if (m.getIdCentro() != null && m.getIdCentro() == 2) {
            logGC.info(msg);
        }

        return m;
    }

    public void registrarSalida(Integer id) {
        Optional<Movimiento> opcional = repo.findById(id);
        if (opcional.isPresent()) {
            Movimiento mov = opcional.get();
            mov.setFechaSalida(LocalDateTime.now());
            repo.save(mov);

            String nombreCompleto = mov.getNombre() + " " + (mov.getApellido1() != null ? mov.getApellido1() : "");
            String msg = "SALIDA registrada - Persona: " + nombreCompleto;

            if (mov.getIdCentro() != null && mov.getIdCentro() == 1) {
                logTF.info(msg);
            } else if (mov.getIdCentro() != null && mov.getIdCentro() == 2) {
                logGC.info(msg);
            }
        }
    }

    public Optional<Movimiento> obtenerPorId(Integer id) {
        return repo.findById(id);
    }

    public List<Movimiento> listarActivosHoyPorCentro(Integer idCentro) {
        LocalDateTime inicioHoy = LocalDate.now().atStartOfDay();
        LocalDateTime finHoy = LocalDate.now().atTime(LocalTime.MAX);
        return repo.findByIdCentroAndFechaSalidaIsNullAndFechaEntradaBetween(idCentro, inicioHoy, finHoy);
    }

    public void ejecutarMantenimientoFechas() {
        try {
            repo.corregirFechasEntrada();
            repo.corregirFechasSalida();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}