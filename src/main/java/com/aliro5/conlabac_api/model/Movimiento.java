package com.aliro5.conlabac_api.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Movadoj") // Nombre exacto de la tabla en MySQL
public class Movimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MovOrden") // Clave primaria
    private Integer id;

    @Column(name = "MovCentro", nullable = false)
    private Integer idCentro;

    @Column(name = "MovNombre", length = 60, nullable = false)
    private String nombre;

    @Column(name = "MovApellidoUno", length = 60)
    private String apellido1;

    @Column(name = "MovApellidoDos", length = 60)
    private String apellido2;

    // Usamos LocalDateTime para mapear DATETIME de MySQL
    @Column(name = "MovFechaHoraEntrada_dt")
    private LocalDateTime fechaEntrada;

    @Column(name = "MovFechaHoraSalida_dt")
    private LocalDateTime fechaSalida;

    @Column(name = "MovMotivo", length = 250)
    private String motivo;

    @Column(name = "MovProcedencia", length = 100)
    private String procedencia;

    @Column(name = "MovDestino", length = 100) // ¿Se llama MovDestino en tu BD?
    private String destino;

    @Column(name = "MovPlanta", length = 15)
    private String planta;

    @Column(name = "MovVehiculo", length = 12)
    private String vehiculo;

    public Movimiento() {
    }

    // --- Getters y Setters ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getIdCentro() { return idCentro; }
    public void setIdCentro(Integer idCentro) { this.idCentro = idCentro; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido1() { return apellido1; }
    public void setApellido1(String apellido1) { this.apellido1 = apellido1; }

    public String getApellido2() { return apellido2; }
    public void setApellido2(String apellido2) { this.apellido2 = apellido2; }

    public LocalDateTime getFechaEntrada() { return fechaEntrada; }
    public void setFechaEntrada(LocalDateTime fechaEntrada) { this.fechaEntrada = fechaEntrada; }

    public LocalDateTime getFechaSalida() { return fechaSalida; }
    public void setFechaSalida(LocalDateTime fechaSalida) { this.fechaSalida = fechaSalida; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public String getProcedencia() {
        return procedencia;
    }

    public void setProcedencia(String procedencia) {
        this.procedencia = procedencia;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getPlanta() {
        return planta;
    }

    public void setPlanta(String planta) {
        this.planta = planta;
    }

    public String getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(String vehiculo) {
        this.vehiculo = vehiculo;
    }
}