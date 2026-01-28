package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.EntreTurno;
import com.aliro5.conlabac_api.model.Usuario;
import com.aliro5.conlabac_api.repository.EntreTurnoRepository;
import com.aliro5.conlabac_api.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class EntreTurnoService {

    @Autowired private EntreTurnoRepository repo;
    @Autowired private UsuarioRepository usuarioRepo;

    // Loggers para discriminación de sedes
    private static final Logger logTF = LoggerFactory.getLogger("TenerifeLogger");
    private static final Logger logGC = LoggerFactory.getLogger("GranCanariaLogger");

    public List<EntreTurno> listarHistorial(Integer idCentro) {
        List<EntreTurno> lista = repo.findByIdCentroOrderByFechaHoraEscritoDesc(idCentro);
        rellenarNombres(lista);
        return lista;
    }

    public List<EntreTurno> listarPendientes(Integer idCentro) {
        List<EntreTurno> lista = repo.findByIdCentroAndFechaHoraLeidoIsNullOrderByFechaHoraEscritoDesc(idCentro);
        rellenarNombres(lista);
        return lista;
    }

    private void rellenarNombres(List<EntreTurno> lista) {
        if (lista == null || lista.isEmpty()) return;
        Set<String> dnis = lista.stream()
                .flatMap(t -> Stream.of(t.getOperarioEscritor(), t.getUsuarioLector()))
                .filter(dni -> dni != null && !dni.isEmpty())
                .collect(Collectors.toSet());
        if (dnis.isEmpty()) return;
        List<Usuario> usuariosEncontrados = usuarioRepo.findAllById(dnis);
        Map<String, String> mapaNombres = usuariosEncontrados.stream()
                .collect(Collectors.toMap(
                        Usuario::getDni,
                        u -> u.getNombre() + " " + (u.getApellido1() != null ? u.getApellido1() : "")
                ));
        for (EntreTurno turno : lista) {
            String dniLector = turno.getUsuarioLector();
            if (dniLector != null && mapaNombres.containsKey(dniLector)) {
                turno.setNombreCompletoMostrar(mapaNombres.get(dniLector).trim());
            } else {
                turno.setNombreCompletoMostrar(dniLector);
            }
            String dniEscritor = turno.getOperarioEscritor();
            if (dniEscritor != null && mapaNombres.containsKey(dniEscritor)) {
                turno.setNombreEscritorMostrar(mapaNombres.get(dniEscritor).trim());
            } else {
                turno.setNombreEscritorMostrar(dniEscritor);
            }
        }
    }

    public EntreTurno crear(EntreTurno nota) {
        LocalDateTime ahora = LocalDateTime.now();
        nota.setFechaHoraEscrito(ahora);
        nota.setFechaEscrito(ahora.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        nota.setHoraEscrito(ahora.format(DateTimeFormatter.ofPattern("HHmmss")));
        nota.setFechaHoraLeido(null);
        nota.setFechaLeido(null);
        nota.setHoraLeido(null);

        EntreTurno guardado = repo.save(nota);

        // LOG DE SEDE
        String msg = "ENTRE TURNO escrito por " + nota.getOperarioEscritor() + ": " + nota.getTexto();
        registrarEnSede(nota.getIdCentro(), msg);

        return guardado;
    }

    public EntreTurno guardar(EntreTurno nota) {
        return this.crear(nota);
    }

    public void marcarLeido(Integer id, String dniLector) {
        repo.findById(id).ifPresent(nota -> {
            LocalDateTime ahora = LocalDateTime.now();
            nota.setUsuarioLector(dniLector);
            nota.setFechaHoraLeido(ahora);
            nota.setFechaLeido(ahora.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
            nota.setHoraLeido(ahora.format(DateTimeFormatter.ofPattern("HHmmss")));
            repo.save(nota);

            // LOG DE SEDE
            String msg = "ENTRE TURNO leído por " + dniLector + ". Ref ID: " + id;
            registrarEnSede(nota.getIdCentro(), msg);
        });
    }

    private void registrarEnSede(Integer centro, String msg) {
        if (centro == null) return;
        if (centro == 1) logTF.info(msg);
        else if (centro == 2) logGC.info(msg);
    }

    public void ejecutarMantenimientoFechas() {
        try {
            repo.corregirFechasEscritura();
            repo.corregirFechasLectura();
        } catch (Exception e) { e.printStackTrace(); }
    }
}