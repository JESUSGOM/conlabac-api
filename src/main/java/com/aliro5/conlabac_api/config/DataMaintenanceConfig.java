package com.aliro5.conlabac_api.config;

import com.aliro5.conlabac_api.repository.IncidenciaRepository;
import com.aliro5.conlabac_api.repository.EnvioEmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
public class DataMaintenanceConfig {

    private final IncidenciaRepository incidenciaRepo;
    private final EnvioEmailRepository emailRepo;

    @Autowired
    public DataMaintenanceConfig(IncidenciaRepository incidenciaRepo, EnvioEmailRepository emailRepo) {
        this.incidenciaRepo = incidenciaRepo;
        this.emailRepo = emailRepo;
    }

    /**
     * Se ejecuta automáticamente cuando la aplicación está lista.
     * Sincroniza los campos datetime de Incidencias y EnvioEmail.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void ejecutarMantenimiento() {
        // 1. Mantenimiento de Incidencias
        try {
            System.out.println(">>> MANTENIMIENTO: Iniciando actualización de fechas en Incidencias...");
            int actualizadosInc = incidenciaRepo.actualizarFechasNulas();
            if (actualizadosInc > 0) {
                System.out.println(">>> MANTENIMIENTO [Incidencias]: Se han corregido " + actualizadosInc + " registros.");
            } else {
                System.out.println(">>> MANTENIMIENTO [Incidencias]: No hay pendientes.");
            }
        } catch (Exception e) {
            System.err.println(">>> ERROR en mantenimiento de Incidencias: " + e.getMessage());
        }

        // 2. Mantenimiento de EnvioEmail (Basado en tu SQL aportado)
        try {
            System.out.println(">>> MANTENIMIENTO: Iniciando actualización de fechas en EnvioEmail...");

            // Llama al método que combina EnEmFecha y EnEmHora
            int actualizadosEmail = emailRepo.actualizarFechasNulasEmails();

            if (actualizadosEmail > 0) {
                System.out.println(">>> MANTENIMIENTO [EnvioEmail]: Se han corregido " + actualizadosEmail + " registros exitosamente.");
            } else {
                System.out.println(">>> MANTENIMIENTO [EnvioEmail]: No hay correos pendientes de actualizar.");
            }
        } catch (Exception e) {
            System.err.println(">>> ERROR CRÍTICO en mantenimiento de Emails: " + e.getMessage());
        }
    }
}