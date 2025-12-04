package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.model.Usuario;
import com.aliro5.conlabac_api.service.*; // Importamos todos los servicios
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    @Autowired
    private UsuarioService usuarioService;

    // Servicios para mantenimiento
    @Autowired
    private MovimientoService movimientoService;

    @Autowired
    private KeyMoveService keyMoveService;

    @Autowired
    private EntreTurnoService entreTurnoService;

    @Autowired
    private PaqueteService paqueteService;

    @Autowired
    private GarajeService garajeService; // <--- NUEVA INYECCIÓN PARA GARAJE

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody Map<String, String> credenciales) {
        String dni = credenciales.get("dni");
        String clave = credenciales.get("clave");

        // 1. Validar usuario
        Usuario usuario = usuarioService.validarLogin(dni, clave);

        if (usuario != null) {

            // 2. TAREAS DE MANTENIMIENTO (Segundo Plano)
            new Thread(() -> {
                try {
                    // A. Movimientos Personas
                    movimientoService.ejecutarMantenimientoFechas();

                    // B. Llaves
                    keyMoveService.ejecutarMantenimientoFechas();

                    // C. Relevos
                    entreTurnoService.ejecutarMantenimientoFechas();

                    // D. Paquetería
                    paqueteService.ejecutarMantenimientoFechas();

                    // E. Garaje (NUEVO)
                    garajeService.ejecutarMantenimientoFechas();

                } catch (Exception e) {
                    System.err.println("Advertencia: Error en mantenimiento post-login: " + e.getMessage());
                }
            }).start();

            // 3. OK
            return ResponseEntity.ok(usuario);

        } else {
            // 4. Error
            return ResponseEntity.status(401).build();
        }
    }
}