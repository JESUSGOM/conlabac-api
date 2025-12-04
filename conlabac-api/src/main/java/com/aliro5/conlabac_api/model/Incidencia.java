package com.aliro5.conlabac_api.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Incidencias")
public class Incidencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IncId")
    private Integer id;

    @Column(name = "IncCentro", nullable = false)
    private Integer idCentro;

    // --- Fechas Legacy (Texto) ---
    @Column(name = "IncFecha", length = 8, nullable = false)
    private String fecha; // YYYYMMDD

    @Column(name = "IncHora", length = 6, nullable = false)
    private String hora; // HHMMSS

    // --- Fecha Moderna (Datetime) ---
    @Column(name = "IncFechaHora_dt")
    private LocalDateTime fechaHora;

    @Column(name = "IncTexto", length = 350, nullable = false)
    private String texto;

    @Column(name = "IncUsuario", length = 50, nullable = false)
    private String usuario; // Nombre del vigilante que reporta

    // --- Datos de Comunicación (Opcionales) ---
    @Column(name = "IncComunicadoA", length = 60)
    private String comunicadoA; // A quién se avisó (ej: "Gerente")

    @Column(name = "IncModoComunica", length = 15)
    private String modoComunica; // Ej: "Telefono", "Email"

    @Column(name = "IncEmailComunica", length = 50)
    private String emailComunica;

    public Incidencia() {
    }

    // --- GETTERS Y SETTERS ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getIdCentro() { return idCentro; }
    public void setIdCentro(Integer idCentro) { this.idCentro = idCentro; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getComunicadoA() { return comunicadoA; }
    public void setComunicadoA(String comunicadoA) { this.comunicadoA = comunicadoA; }

    public String getModoComunica() { return modoComunica; }
    public void setModoComunica(String modoComunica) { this.modoComunica = modoComunica; }

    public String getEmailComunica() { return emailComunica; }
    public void setEmailComunica(String emailComunica) { this.emailComunica = emailComunica; }
}