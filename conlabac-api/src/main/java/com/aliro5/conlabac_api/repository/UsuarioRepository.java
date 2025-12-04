package com.aliro5.conlabac_api.repository;

import com.aliro5.conlabac_api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    // CORRECCIÓN: Eliminamos findByDniAndClave porque ya no existe el campo 'clave' directo.
    // Usamos solo búsqueda por DNI.
    Optional<Usuario> findByDni(String dni);

    @Query("SELECT u FROM Usuario u WHERE u.idCentro = :idCentro AND u.tipo IN :tipos")
    List<Usuario> findByCentroAndTipoIn(Integer idCentro, List<String> tipos);

    // Busca personal operativo (U) o jefes (Y) del centro
    @Query("SELECT u FROM Usuario u WHERE u.idCentro = :idCentro AND u.tipo IN ('U', 'Y')")
    List<Usuario> findPersonalCentro(Integer idCentro);
}