package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.dto.DashboardStats;
import com.aliro5.conlabac_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class DashboardService {

    @Autowired
    private EntreTurnoRepository relevoRepo;

    @Autowired
    private PaqueteRepository paqueteRepo;

    @Autowired
    private KeyMoveRepository keyRepo;

    @Autowired
    private MovimientoRepository visitaRepo;

    @Autowired
    private MovimientoEmpleadoRepository contrataRepo;

    public DashboardStats obtenerEstadisticas(Integer idCentro) {
        // 1. Relevos sin leer
        long relevos = relevoRepo.findByIdCentroAndFechaHoraLeidoIsNullOrderByFechaHoraEscritoDesc(idCentro).size();

        // 2. Paquetes comunicados="NO"
        long paquetes = paqueteRepo.findByIdCentroAndComunicadoOrderByFechaHoraRecepcionDesc(idCentro, "NO").size();

        // 3. Llaves fuera (Activos)
        long llaves = keyRepo.findActivosPorCentro(idCentro).size();

        // 4. Visitas dentro HOY
        LocalDateTime inicioHoy = LocalDate.now().atStartOfDay();
        LocalDateTime finHoy = LocalDate.now().atTime(LocalTime.MAX);
        long visitas = visitaRepo.findByIdCentroAndFechaSalidaIsNullAndFechaEntradaBetween(idCentro, inicioHoy, finHoy).size();

        // 5. Contratas dentro
        long contratas = contrataRepo.findByIdCentroAndFechaSalidaIsNullOrderByFechaEntradaDesc(idCentro).size();

        return new DashboardStats(relevos, paquetes, llaves, visitas, contratas);
    }
}