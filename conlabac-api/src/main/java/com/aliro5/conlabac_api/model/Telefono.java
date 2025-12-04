package com.aliro5.conlabac_api.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Telefonos")
public class Telefono {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TelId")
    private Integer id;

    @Column(name = "TelCentro", nullable = false)
    private Integer idCentro;

    // --- FECHA REGISTRO (Cuando se recibe la llamada) ---
    @Column(name = "TelFecha", length = 8, nullable = false)
    private String fecha; // YYYYMMDD

    @Column(name = "TelHora", length = 6, nullable = false)
    private String hora; // HHMMSS

    @Column(name = "TelFechaHoraRegistro_dt")
    private LocalDateTime fechaHoraRegistro;

    // --- DATOS DEL MENSAJE ---
    @Column(name = "TelEmisor", length = 50, nullable = false)
    private String emisor; // Quién llama

    @Column(name = "TelDestinatario", length = 50, nullable = false)
    private String destinatario; // Para quién es

    @Column(name = "TelMensaje", length = 300, nullable = false)
    private String mensaje;

    // --- ESTADO Y ENTREGA ---
    @Column(name = "TelComunicado")
    private Integer comunicado; // 0 = Pendiente, 1 = Comunicado

    @Column(name = "TelFechaEntrega", length = 8)
    private String fechaEntrega;

    @Column(name = "TelHoraEntrega", length = 6)
    private String horaEntrega;

    @Column(name = "TelFechaHoraEntrega_dt")
    private LocalDateTime fechaHoraEntrega;

    public Telefono() {}

    // --- GETTERS Y SETTERS ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getIdCentro() { return idCentro; }
    public void setIdCentro(Integer idCentro) { this.idCentro = idCentro; }
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }
    public LocalDateTime getFechaHoraRegistro() { return fechaHoraRegistro; }
    public void setFechaHoraRegistro(LocalDateTime fechaHoraRegistro) { this.fechaHoraRegistro = fechaHoraRegistro; }
    public String getEmisor() { return emisor; }
    public void setEmisor(String emisor) { this.emisor = emisor; }
    public String getDestinatario() { return destinatario; }
    public void setDestinatario(String destinatario) { this.destinatario = destinatario; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public Integer getComunicado() { return comunicado; }
    public void setComunicado(Integer comunicado) { this.comunicado = comunicado; }
    public String getFechaEntrega() { return fechaEntrega; }
    public void setFechaEntrega(String fechaEntrega) { this.fechaEntrega = fechaEntrega; }
    public String getHoraEntrega() { return horaEntrega; }
    public void setHoraEntrega(String horaEntrega) { this.horaEntrega = horaEntrega; }
    public LocalDateTime getFechaHoraEntrega() { return fechaHoraEntrega; }
    public void setFechaHoraEntrega(LocalDateTime fechaHoraEntrega) { this.fechaHoraEntrega = fechaHoraEntrega; }
}