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

    @Column(name = "EnEmDestinatario", length = 100, nullable = false)
    private String destinatario;

    @Column(name = "EnEmFecha", length = 8, nullable = false)
    private String fecha; // Formato YYYYMMDD

    @Column(name = "EnEmHora", length = 6, nullable = false)
    private String hora;  // Formato HHmmss

    @Column(name = "EnEmTexto", columnDefinition = "LONGTEXT", nullable = false)
    private String texto;

    @Column(name = "EnEmEmisor", length = 100, nullable = false)
    private String emisor; // Se aumenta a 100 para admitir nombre completo si fuera necesario

    @Column(name = "EnEmFechaHora_dt")
    private LocalDateTime fechaHoraDt;

    // --- Constructor Vacío (Obligatorio para JPA) ---
    public EnvioEmail() {
    }

    // --- Getters y Setters ---

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getDestinatario() { return destinatario; }
    public void setDestinatario(String destinatario) { this.destinatario = destinatario; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }

    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }

    public String getEmisor() { return emisor; }
    public void setEmisor(String emisor) { this.emisor = emisor; }

    public LocalDateTime getFechaHoraDt() { return fechaHoraDt; }
    public void setFechaHoraDt(LocalDateTime fechaHoraDt) { this.fechaHoraDt = fechaHoraDt; }
}