package com.aliro5.conlabac_api.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "AperturasExtra")
public class AperturaExtra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AeId")
    private Integer id;

    @Column(name = "AeCentro", nullable = false)
    private Integer idCentro;

    @Column(name = "AeFecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "AeHoraInicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "AeHoraFinal", nullable = false)
    private LocalTime horaFinal;

    @Column(name = "AeMotivo", columnDefinition = "LONGTEXT", nullable = false)
    private String motivo;

    public AperturaExtra() {}

    // --- GETTERS Y SETTERS ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getIdCentro() { return idCentro; }
    public void setIdCentro(Integer idCentro) { this.idCentro = idCentro; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }
    public LocalTime getHoraFinal() { return horaFinal; }
    public void setHoraFinal(LocalTime horaFinal) { this.horaFinal = horaFinal; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
}