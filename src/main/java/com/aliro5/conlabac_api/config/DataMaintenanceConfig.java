package com.aliro5.conlabac_api.config;

import com.aliro5.conlabac_api.repository.IncidenciaRepository;
import com.aliro5.conlabac_api.repository.EnvioEmailRepository;
import com.aliro5.conlabac_api.repository.MovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
public class DataMaintenanceConfig {

    private final IncidenciaRepository incidenciaRepo;
    private final EnvioEmailRepository emailRepo;
    private final MovimientoRepository movimientoRepo;

    @Autowired
    public DataMaintenanceConfig(IncidenciaRepository incidenciaRepo,
                                 EnvioEmailRepository emailRepo,
                                 MovimientoRepository movimientoRepo) {
        this.incidenciaRepo = incidenciaRepo;
        this.emailRepo = emailRepo;
        this.movimientoRepo = movimientoRepo;
    }

    /**
     * Se ejecuta automáticamente cuando la aplicación está lista.
     * Sincroniza los campos datetime y de texto en Incidencias, Emails y Movimientos.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void ejecutarMantenimiento() {

        // 1. Mantenimiento de Incidencias (Asigna fecha actual a nulos)
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

        // 2. Mantenimiento de EnvioEmail (Combina texto Fecha/Hora en campo datetime)
        try {
            System.out.println(">>> MANTENIMIENTO: Iniciando actualización de fechas en EnvioEmail...");
            int actualizadosEmail = emailRepo.actualizarFechasNulasEmails();
            if (actualizadosEmail > 0) {
                System.out.println(">>> MANTENIMIENTO [EnvioEmail]: Se han corregido " + actualizadosEmail + " registros.");
            } else {
                System.out.println(">>> MANTENIMIENTO [EnvioEmail]: No hay correos pendientes de actualizar.");
            }
        } catch (Exception e) {
            System.err.println(">>> ERROR CRÍTICO en mantenimiento de Emails: " + e.getMessage());
        }

        // 3. Mantenimiento de Movimientos (Visitantes) - Bidireccional
        try {
            System.out.println(">>> MANTENIMIENTO: Sincronizando tabla Movimientos (Movadoj)...");

            // A: Rellenar el campo moderno (_dt) desde los textos antiguos
            movimientoRepo.corregirFechasEntrada();
            movimientoRepo.corregirFechasSalida();

            // B: Rellenar los campos de texto antiguos desde el campo moderno (_dt)
            // Esto es lo que solicitaste para que las nuevas entradas se reflejen en los campos antiguos
            int entradaTexto = movimientoRepo.corregirTextosDesdeDateTimeEntrada();
            int salidaTexto = movimientoRepo.corregirTextosDesdeDateTimeSalida();

            if (entradaTexto > 0 || salidaTexto > 0) {
                System.out.println(">>> MANTENIMIENTO [Movimientos]: Se han sincronizado " + entradaTexto + " textos de entrada y " + salidaTexto + " de salida.");
            } else {
                System.out.println(">>> MANTENIMIENTO [Movimientos]: Todos los campos de texto están al día.");
            }
        } catch (Exception e) {
            System.err.println(">>> ERROR en mantenimiento de Movimientos: " + e.getMessage());
        }
    }
}