package com.aliro5.conlabac_api.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "EntreTurnos")
public class EntreTurno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EntId")
    private Integer id;

    @Column(name = "EntCentro", nullable = false)
    private Integer idCentro;

    // --- ESCRITO POR ---
    @Column(name = "EntOperario", length = 9, nullable = false)
    private String operarioEscritor; // Guarda el DNI

    @Transient // Nuevo campo para transportar el nombre del escritor
    private String nombreEscritorMostrar;

    @Column(name = "EntFescrito", length = 8, nullable = false)
    private String fechaEscrito;

    @Column(name = "EntHescrito", length = 6, nullable = false)
    private String horaEscrito;

    @Column(name = "EntFechaHoraEscrito_dt")
    private LocalDateTime fechaHoraEscrito;

    @Column(name = "EntTexto", length = 250, nullable = false)
    private String texto;

    // --- LEÍDO POR ---
    @Column(name = "EntUsuario", length = 9)
    private String usuarioLector; // Guarda el DNI

    @Transient // Campo para transportar el nombre del lector
    private String nombreCompletoMostrar;

    @Column(name = "EntFleido", length = 8)
    private String fechaLeido;

    @Column(name = "EntHleido", length = 6)
    private String horaLeido;

    @Column(name = "EntFechaHoraLeido_dt")
    private LocalDateTime fechaHoraLeido;

    public EntreTurno() {}

    // --- GETTERS Y SETTERS ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getIdCentro() { return idCentro; }
    public void setIdCentro(Integer idCentro) { this.idCentro = idCentro; }

    public String getOperarioEscritor() { return operarioEscritor; }
    public void setOperarioEscritor(String operarioEscritor) { this.operarioEscritor = operarioEscritor; }

    public String getNombreEscritorMostrar() { return nombreEscritorMostrar; }
    public void setNombreEscritorMostrar(String nombreEscritorMostrar) { this.nombreEscritorMostrar = nombreEscritorMostrar; }

    public String getFechaEscrito() { return fechaEscrito; }
    public void setFechaEscrito(String fechaEscrito) { this.fechaEscrito = fechaEscrito; }
    public String getHoraEscrito() { return horaEscrito; }
    public void setHoraEscrito(String horaEscrito) { this.horaEscrito = horaEscrito; }
    public LocalDateTime getFechaHoraEscrito() { return fechaHoraEscrito; }
    public void setFechaHoraEscrito(LocalDateTime fechaHoraEscrito) { this.fechaHoraEscrito = fechaHoraEscrito; }
    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }
    public String getUsuarioLector() { return usuarioLector; }
    public void setUsuarioLector(String usuarioLector) { this.usuarioLector = usuarioLector; }

    public String getNombreCompletoMostrar() { return nombreCompletoMostrar; }
    public void setNombreCompletoMostrar(String nombreCompletoMostrar) { this.nombreCompletoMostrar = nombreCompletoMostrar; }

    public String getFechaLeido() { return fechaLeido; }
    public void setFechaLeido(String fechaLeido) { this.fechaLeido = fechaLeido; }
    public String getHoraLeido() { return horaLeido; }
    public void setHoraLeido(String horaLeido) { this.horaLeido = horaLeido; }
    public LocalDateTime getFechaHoraLeido() { return fechaHoraLeido; }
    public void setFechaHoraLeido(LocalDateTime fechaHoraLeido) { this.fechaHoraLeido = fechaHoraLeido; }
}