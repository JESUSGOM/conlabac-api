package com.aliro5.conlabac_api.controller;

import com.aliro5.conlabac_api.model.Usuario;
import com.aliro5.conlabac_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioRestController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    @GetMapping("/{dni}")
    public ResponseEntity<Usuario> obtener(@PathVariable String dni) {
        return usuarioRepository.findById(dni)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Usuario guardar(@RequestBody Usuario usuario) {
        // LÓGICA DE GENERACIÓN DE CLAVE AUTOMÁTICA
        // Aplicamos esto si el usuario NO envía una clave específica (es un alta automática)
        // o si queremos forzar esta lógica siempre para nuevos usuarios.

        String claveParaEncriptar = usuario.getClave(); // La que viene del formulario (si la hay)

        // Si no viene clave manual, generamos la clave algorítmica basada en el DNI
        if (claveParaEncriptar == null || claveParaEncriptar.isEmpty()) {
            try {
                claveParaEncriptar = generarClaveAlgoritmica(usuario.getDni());
                System.out.println("Clave generada para " + usuario.getDni() + ": " + claveParaEncriptar);
            } catch (Exception e) {
                // Fallback por si el DNI no tiene el formato esperado
                claveParaEncriptar = usuario.getDni();
            }
        }

        // Encriptamos la clave (sea la manual o la generada)
        if (claveParaEncriptar != null && !claveParaEncriptar.isEmpty()) {
            usuario.setClaveBcrypt(passwordEncoder.encode(claveParaEncriptar));
            usuario.setClavePlana(null); // Seguridad: Borramos la clave plana
        }

        return usuarioRepository.save(usuario);
    }

    @DeleteMapping("/{dni}")
    public ResponseEntity<Void> eliminar(@PathVariable String dni) {
        usuarioRepository.deleteById(dni);
        return ResponseEntity.ok().build();
    }

    // --- ALGORITMO DE CÁLCULO DE CLAVE ---
    private String generarClaveAlgoritmica(String dniCompleto) {
        if (dniCompleto == null || dniCompleto.length() < 2) {
            return "1234"; // Valor por defecto si el DNI está mal
        }

        // 1. Separamos números y letra
        // Asumimos formato estándar: Números al principio, Letra al final
        String parteNumerica = dniCompleto.substring(0, dniCompleto.length() - 1);
        char letra = dniCompleto.charAt(dniCompleto.length() - 1);

        // 2. Sumamos los dígitos
        int suma = 0;
        for (char c : parteNumerica.toCharArray()) {
            if (Character.isDigit(c)) {
                suma += Character.getNumericValue(c);
            }
        }

        // 3. Reducimos hasta que sea un solo dígito (1-9)
        // Ejemplo: 39 -> 3+9=12 -> 1+2=3
        while (suma > 9) {
            int subSuma = 0;
            String sumaStr = String.valueOf(suma);
            for (char c : sumaStr.toCharArray()) {
                subSuma += Character.getNumericValue(c);
            }
            suma = subSuma;
        }

        // 4. Insertamos la letra en la posición calculada
        // La posición es "suma". Como Java empieza en 0, el índice es suma - 1.
        // Ejemplo: Si suma es 3, ocupa el 3er lugar (índice 2).
        StringBuilder sb = new StringBuilder(parteNumerica);

        int indiceInsercion = suma - 1;

        // Control de límites (por si la suma fuera mayor que la longitud, aunque en DNI no pasa)
        if (indiceInsercion < 0) indiceInsercion = 0;
        if (indiceInsercion > sb.length()) indiceInsercion = sb.length();

        sb.insert(indiceInsercion, letra);

        return sb.toString().toUpperCase(); // Devolvemos la clave generada (Ej: 42Z086955)
    }
}