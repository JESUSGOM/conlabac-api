package com.aliro5.conlabac_api.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "MovimientosEmpleados")
public class MovimientoEmpleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MovId")
    private Integer id;

    @Column(name = "MovPrdCif", length = 9, nullable = false)
    private String cifProveedor; // Empresa a la que pertenece

    @Column(name = "MovCentro", nullable = false)
    private Integer idCentro;

    @Column(name = "MovEmpNif", length = 9, nullable = false)
    private String nifEmpleado; // El trabajador que entra

    @Column(name = "MovFechaHoraEntrada", nullable = false)
    private LocalDateTime fechaEntrada;

    @Column(name = "MovFechaHoraSalida")
    private LocalDateTime fechaSalida;

    public MovimientoEmpleado() {}

    // --- GETTERS Y SETTERS ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getCifProveedor() { return cifProveedor; }
    public void setCifProveedor(String cifProveedor) { this.cifProveedor = cifProveedor; }

    public Integer getIdCentro() { return idCentro; }
    public void setIdCentro(Integer idCentro) { this.idCentro = idCentro; }

    public String getNifEmpleado() { return nifEmpleado; }
    public void setNifEmpleado(String nifEmpleado) { this.nifEmpleado = nifEmpleado; }

    public LocalDateTime getFechaEntrada() { return fechaEntrada; }
    public void setFechaEntrada(LocalDateTime fechaEntrada) { this.fechaEntrada = fechaEntrada; }

    public LocalDateTime getFechaSalida() { return fechaSalida; }
    public void setFechaSalida(LocalDateTime fechaSalida) { this.fechaSalida = fechaSalida; }
}