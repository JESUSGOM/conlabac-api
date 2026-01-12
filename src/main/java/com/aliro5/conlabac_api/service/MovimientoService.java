package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.Movimiento;
import com.aliro5.conlabac_api.repository.MovimientoRepository;
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

    public List<Movimiento> listarTodos() {
        // Devuelve todos (cuidado si hay millones de registros, idealmente se pagina)
        return repo.findAll();
    }

    public List<Movimiento> listarPorCentro(Integer idCentro) {
        return repo.findByIdCentro(idCentro);
    }

    public Movimiento guardar(Movimiento mov) {
        return repo.save(mov);
    }

    public Optional<Movimiento> obtenerPorId(Integer id) {
        return repo.findById(id);
    }

    public List<Movimiento> listarActivosHoyPorCentro(Integer idCentro) {
        // Calculamos el rango de "HOY"
        LocalDateTime inicioHoy = LocalDate.now().atStartOfDay(); // Hoy a las 00:00:00
        LocalDateTime finHoy = LocalDate.now().atTime(LocalTime.MAX); // Hoy a las 23:59:59.999

        return repo.findByIdCentroAndFechaSalidaIsNullAndFechaEntradaBetween(idCentro, inicioHoy, finHoy);
    }

    public void registrarSalida(Integer id) {
        // 1. Buscamos el movimiento
        Optional<Movimiento> opcional = repo.findById(id);

        if (opcional.isPresent()) {
            Movimiento mov = opcional.get();
            // 2. Le ponemos fecha de salida AHORA MISMO
            mov.setFechaSalida(LocalDateTime.now());
            // 3. Guardamos
            repo.save(mov);
        }
    }

    public void ejecutarMantenimientoFechas() {
        try {
            System.out.println("Ejecutando scripts de mantenimiento de fechas...");
            repo.corregirFechasEntrada();
            repo.corregirFechasSalida();
            System.out.println("Mantenimiento de fechas finalizado.");
        } catch (Exception e) {
            System.err.println("Error en mantenimiento de fechas: " + e.getMessage());
            e.printStackTrace();
        }
    }

}