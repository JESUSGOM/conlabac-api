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

    @Column(name = "IncCentro")
    private Integer centro;

    @Column(name = "IncFecha", length = 8)
    private String fecha;

    @Column(name = "IncHora", length = 6)
    private String hora;

    @Column(name = "IncTexto", columnDefinition = "LONGTEXT")
    private String texto;

    @Column(name = "IncComunicadoA", length = 100)
    private String comunicadoA;

    @Column(name = "IncModoComunica", length = 20)
    private String modoComunica = "EMAIL";

    @Column(name = "IncEmailComunica", length = 100)
    private String emailComunica;

    @Column(name = "IncUsuario", length = 100)
    private String usuario;

    @Column(name = "IncFechaHora_dt")
    private LocalDateTime fechaHoraDt;

    public Incidencia() {}

    // --- MÉTODOS ALIAS PARA COMPATIBILIDAD CON SERVICES ---
    public Integer getIdCentro() { return this.centro; }
    public void setIdCentro(Integer idCentro) { this.centro = idCentro; }
    public LocalDateTime getFechaHora() { return this.fechaHoraDt; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHoraDt = fechaHora; }

    // --- Getters y Setters Estándar ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getCentro() { return centro; }
    public void setCentro(Integer centro) { this.centro = centro; }
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }
    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }
    public String getComunicadoA() { return comunicadoA; }
    public void setComunicadoA(String comunicadoA) { this.comunicadoA = comunicadoA; }
    public String getModoComunica() { return modoComunica; }
    public void setModoComunica(String modoComunica) { this.modoComunica = modoComunica; }
    public String getEmailComunica() { return emailComunica; }
    public void setEmailComunica(String emailComunica) { this.emailComunica = emailComunica; }
    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
    public LocalDateTime getFechaHoraDt() { return fechaHoraDt; }
    public void setFechaHoraDt(LocalDateTime fechaHoraDt) { this.fechaHoraDt = fechaHoraDt; }
}