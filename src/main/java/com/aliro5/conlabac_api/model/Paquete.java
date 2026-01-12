package com.aliro5.conlabac_api.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Paqueteria")
public class Paquete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PktId")
    private Integer id;

    @Column(name = "PktCentro", nullable = false)
    private Integer idCentro;

    // --- FECHAS DE RECEPCIÓN ---
    @Column(name = "PktFecha", length = 8, nullable = false)
    private String fecha; // YYYYMMDD

    @Column(name = "PktHora", length = 6, nullable = false)
    private String hora; // HHMMSS

    @Column(name = "PktFechaHoraRecepcion_dt")
    private LocalDateTime fechaHoraRecepcion;

    // --- DATOS DEL PAQUETE ---
    @Column(name = "PktEmisor", length = 30, nullable = false)
    private String emisor; // Ej: Amazon

    @Column(name = "PktDestinatario", length = 30, nullable = false)
    private String destinatario; // Ej: Juan Pérez

    @Column(name = "PktMensajeria", length = 30, nullable = false)
    private String mensajeria; // Ej: SEUR

    @Column(name = "PktBultos", nullable = false)
    private Integer bultos;

    @Column(name = "PktTipo", length = 30, nullable = false)
    private String tipo; // Ej: Sobre, Caja, Palet

    // --- ESTADO ---
    @Column(name = "PktComunicado", length = 2, nullable = false)
    private String comunicado; // "SI" o "NO" (Si se avisó al destinatario)

    @Column(name = "PktOperario", length = 9, nullable = false)
    private String operario; // Quién lo recepcionó

    // Constructor vacío
    public Paquete() {}

    // --- GETTERS Y SETTERS ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getIdCentro() { return idCentro; }
    public void setIdCentro(Integer idCentro) { this.idCentro = idCentro; }
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }
    public LocalDateTime getFechaHoraRecepcion() { return fechaHoraRecepcion; }
    public void setFechaHoraRecepcion(LocalDateTime fechaHoraRecepcion) { this.fechaHoraRecepcion = fechaHoraRecepcion; }
    public String getEmisor() { return emisor; }
    public void setEmisor(String emisor) { this.emisor = emisor; }
    public String getDestinatario() { return destinatario; }
    public void setDestinatario(String destinatario) { this.destinatario = destinatario; }
    public String getMensajeria() { return mensajeria; }
    public void setMensajeria(String mensajeria) { this.mensajeria = mensajeria; }
    public Integer getBultos() { return bultos; }
    public void setBultos(Integer bultos) { this.bultos = bultos; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getComunicado() { return comunicado; }
    public void setComunicado(String comunicado) { this.comunicado = comunicado; }
    public String getOperario() { return operario; }
    public void setOperario(String operario) { this.operario = operario; }
}