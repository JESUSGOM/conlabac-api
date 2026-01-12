package com.aliro5.conlabac_api.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "EnvioEmail")
public class EnvioEmail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EnEmId")
    private Integer id;

    @Column(name = "EnEmDestinatario", length = 50, nullable = false)
    private String destinatario;

    @Column(name = "EnEmFecha", length = 8, nullable = false)
    private String fecha;

    @Column(name = "EnEmHora", length = 6, nullable = false)
    private String hora;

    @Column(name = "EnEmFechaHora_dt")
    private LocalDateTime fechaHoraDt;

    @Column(name = "EnEmTexto", columnDefinition = "LONGTEXT", nullable = false)
    private String texto;

    @Column(name = "EnEmEmisor", length = 9, nullable = false)
    private String emisor; // DNI o ID del usuario

    // Constructor vacío
    public EnvioEmail() {}

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getDestinatario() { return destinatario; }
    public void setDestinatario(String destinatario) { this.destinatario = destinatario; }
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }
    public LocalDateTime getFechaHoraDt() { return fechaHoraDt; }
    public void setFechaHoraDt(LocalDateTime fechaHoraDt) { this.fechaHoraDt = fechaHoraDt; }
    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }
    public String getEmisor() { return emisor; }
    public void setEmisor(String emisor) { this.emisor = emisor; }
}