package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.Garaje;
import com.aliro5.conlabac_api.repository.GarajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional; // AÑADIR IMPORT

@Service
public class GarajeService {

    @Autowired
    private GarajeRepository repo;

    public List<Garaje> listarTodos() {
        return repo.findAllByOrderByFechaDesc();
    }

    // NUEVO MÉTODO: Necesario para que el controlador compile
    public Optional<Garaje> obtenerPorId(Integer id) {
        return repo.findById(id);
    }

    public Garaje guardar(Garaje g) {
        LocalDate hoy = LocalDate.now();
        g.setFecha(hoy);
        g.setFechaTexto(hoy.format(DateTimeFormatter.ofPattern("yyyyMMdd")));

        if (g.getMatricula() != null) {
            g.setMatricula(g.getMatricula().toUpperCase().replace(" ", ""));
        }

        return repo.save(g);
    }

    public void ejecutarMantenimientoFechas() {
        try {
            repo.corregirFechas();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}