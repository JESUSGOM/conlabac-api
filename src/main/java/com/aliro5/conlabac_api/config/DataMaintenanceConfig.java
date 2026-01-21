package com.aliro5.conlabac_api.config; // CORRECCIÓN: Declaración de paquete

import com.aliro5.conlabac_api.repository.IncidenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
public class DataMaintenanceConfig {

    // CORRECCIÓN: Inyección mediante constructor para evitar el warning de 'Field injection'
    private final IncidenciaRepository incidenciaRepo;

    @Autowired
    public DataMaintenanceConfig(IncidenciaRepository incidenciaRepo) {
        this.incidenciaRepo = incidenciaRepo;
    }

    /**
     * Se ejecuta automáticamente cuando la aplicación está lista.
     * Actualiza IncFechaHora_dt combinando IncFecha (YYYYMMDD) e IncHora (HHMMSS).
     */
    @EventListener(ApplicationReadyEvent.class)
    public void ejecutarMantenimiento() {
        try {
            System.out.println(">>> MANTENIMIENTO: Iniciando actualización de fechas en Incidencias...");

            // Llama al método native query que añadimos al repositorio
            int actualizados = incidenciaRepo.actualizarFechasNulas();

            if (actualizados > 0) {
                System.out.println(">>> MANTENIMIENTO: Se han corregido " + actualizados + " registros exitosamente.");
            } else {
                System.out.println(">>> MANTENIMIENTO: No hay incidencias pendientes de actualizar.");
            }
        } catch (Exception e) {
            System.err.println(">>> ERROR CRÍTICO en mantenimiento: " + e.getMessage());
        }
    }
}