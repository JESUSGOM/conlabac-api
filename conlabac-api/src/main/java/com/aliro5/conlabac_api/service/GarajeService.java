package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.Garaje;
import com.aliro5.conlabac_api.repository.GarajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class GarajeService {

    @Autowired
    private GarajeRepository repo;

    // 1. Listar todos los registros (Ordenados por fecha descendente)
    public List<Garaje> listarTodos() {
        return repo.findAllByOrderByFechaDesc();
    }

    // 2. Guardar nuevo registro (Entrada de vehículo)
    public Garaje guardar(Garaje g) {
        // Obtenemos la fecha de hoy
        LocalDate hoy = LocalDate.now();

        // Rellenamos el campo moderno (Date)
        g.setFecha(hoy);

        // Rellenamos el campo antiguo (String YYYYMMDD) para compatibilidad
        g.setFechaTexto(hoy.format(DateTimeFormatter.ofPattern("yyyyMMdd")));

        // Limpieza de datos: Matrícula en mayúsculas y sin espacios
        if (g.getMatricula() != null) {
            g.setMatricula(g.getMatricula().toUpperCase().replace(" ", ""));
        }

        return repo.save(g);
    }

    // 3. Mantenimiento Automático (Corrección de fechas históricas)
    public void ejecutarMantenimientoFechas() {
        try {
            // Llama a la consulta nativa definida en el Repositorio
            repo.corregirFechas();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}