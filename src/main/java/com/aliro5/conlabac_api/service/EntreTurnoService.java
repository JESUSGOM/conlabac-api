package com.aliro5.conlabac_api.service;

import com.aliro5.conlabac_api.model.EntreTurno;
import com.aliro5.conlabac_api.model.Usuario;
import com.aliro5.conlabac_api.repository.EntreTurnoRepository;
import com.aliro5.conlabac_api.repository.UsuarioRepository;
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

    // MÉTODO CREAR ORIGINAL
    public EntreTurno crear(EntreTurno nota) {
        LocalDateTime ahora = LocalDateTime.now();
        nota.setFechaHoraEscrito(ahora);
        nota.setFechaEscrito(ahora.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        nota.setHoraEscrito(ahora.format(DateTimeFormatter.ofPattern("HHmmss")));
        nota.setFechaHoraLeido(null);
        nota.setFechaLeido(null);
        nota.setHoraLeido(null);
        nota.setUsuarioLector(null);
        nota.setNombreCompletoMostrar(null);
        nota.setNombreEscritorMostrar(null);
        return repo.save(nota);
    }

    // CORRECCIÓN: Alias para que el controlador compile
    public EntreTurno guardar(EntreTurno nota) {
        return this.crear(nota);
    }

    // MARCAR LEÍDO
    public void marcarLeido(Integer id, String dniLector) {
        Optional<EntreTurno> opcional = repo.findById(id);
        if (opcional.isPresent()) {
            EntreTurno nota = opcional.get();
            LocalDateTime ahora = LocalDateTime.now();
            nota.setUsuarioLector(dniLector);
            nota.setFechaHoraLeido(ahora);
            nota.setFechaLeido(ahora.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
            nota.setHoraLeido(ahora.format(DateTimeFormatter.ofPattern("HHmmss")));
            repo.save(nota);
        }
    }

    public void ejecutarMantenimientoFechas() {
        try {
            repo.corregirFechasEscritura();
            repo.corregirFechasLectura();
        } catch (Exception e) { e.printStackTrace(); }
    }
}