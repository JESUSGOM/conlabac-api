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

    /**
     * Calcula las estadísticas en tiempo real para el panel principal de ALIROS.
     */
    public DashboardStats obtenerEstadisticas(Integer idCentro) {

        // 1. Relevos pendientes de lectura (Turnos)
        long relevos = relevoRepo.findByIdCentroAndFechaHoraLeidoIsNullOrderByFechaHoraEscritoDesc(idCentro).size();

        // 2. Paquetes recibidos que aún no se han entregado al destinatario
        long paquetes = paqueteRepo.findByIdCentroAndComunicadoOrderByFechaHoraRecepcionDesc(idCentro, "NO").size();

        // 3. Llaves fuera del tablero (Sincronizado con la nueva lógica de KeyMoves)
        // Usamos el método que busca específicamente donde la fecha de recepción es nula o vacía
        long llaves = keyRepo.findByCentroAndFechaDevolucionIsNull(idCentro).size();

        // 4. Visitantes que han entrado HOY y aún no han registrado su salida
        LocalDateTime inicioHoy = LocalDate.now().atStartOfDay();
        LocalDateTime finHoy = LocalDate.now().atTime(LocalTime.MAX);
        long visitas = visitaRepo.findByIdCentroAndFechaSalidaIsNullAndFechaEntradaBetween(idCentro, inicioHoy, finHoy).size();

        // 5. Personal de Contratas/Empresas externas actualmente dentro de las instalaciones
        long contratas = contrataRepo.findByIdCentroAndFechaSalidaIsNullOrderByFechaEntradaDesc(idCentro).size();

        // Retornamos el DTO con todos los contadores para el Dashboard
        return new DashboardStats(relevos, paquetes, llaves, visitas, contratas);
    }
}