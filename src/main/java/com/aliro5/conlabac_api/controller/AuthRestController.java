package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.model.Usuario;
import com.aliro5.conlabac_api.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthRestController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MovimientoService movimientoService;

    @Autowired
    private KeyMoveService keyMoveService;

    @Autowired
    private EntreTurnoService entreTurnoService;

    @Autowired
    private PaqueteService paqueteService;

    @Autowired
    private GarajeService garajeService;

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody Map<String, String> credenciales) {
        String dni = credenciales.get("dni");
        String clave = credenciales.get("clave");

        // CORRECCIÓN: Manejo de Optional para evitar error de compilación
        // .orElse(null) extrae el usuario si existe, o pone null si no
        Usuario usuario = usuarioService.validarLogin(dni, clave).orElse(null);

        if (usuario != null) {
            // TAREAS DE MANTENIMIENTO (Segundo Plano)
            new Thread(() -> {
                try {
                    movimientoService.ejecutarMantenimientoFechas();
                    keyMoveService.ejecutarMantenimientoFechas();
                    entreTurnoService.ejecutarMantenimientoFechas();
                    paqueteService.ejecutarMantenimientoFechas();
                    garajeService.ejecutarMantenimientoFechas();
                } catch (Exception e) {
                    System.err.println("Advertencia: Error en mantenimiento post-login: " + e.getMessage());
                }
            }).start();

            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.status(401).build();
        }
    }
}