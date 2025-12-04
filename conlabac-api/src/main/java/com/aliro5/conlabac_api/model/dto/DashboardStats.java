package com.aliro5.conlabac_api.model.dto;

public class DashboardStats {
    private long relevosPendientes;
    private long paquetesPendientes;
    private long llavesPrestadas;
    private long visitasDentro;
    private long contratasDentro;

    public DashboardStats() {}

    public DashboardStats(long relevos, long paquetes, long llaves, long visitas, long contratas) {
        this.relevosPendientes = relevos;
        this.paquetesPendientes = paquetes;
        this.llavesPrestadas = llaves;
        this.visitasDentro = visitas;
        this.contratasDentro = contratas;
    }

    // Getters y Setters
    public long getRelevosPendientes() { return relevosPendientes; }
    public void setRelevosPendientes(long relevosPendientes) { this.relevosPendientes = relevosPendientes; }
    public long getPaquetesPendientes() { return paquetesPendientes; }
    public void setPaquetesPendientes(long paquetesPendientes) { this.paquetesPendientes = paquetesPendientes; }
    public long getLlavesPrestadas() { return llavesPrestadas; }
    public void setLlavesPrestadas(long llavesPrestadas) { this.llavesPrestadas = llavesPrestadas; }
    public long getVisitasDentro() { return visitasDentro; }
    public void setVisitasDentro(long visitasDentro) { this.visitasDentro = visitasDentro; }
    public long getContratasDentro() { return contratasDentro; }
    public void setContratasDentro(long contratasDentro) { this.contratasDentro = contratasDentro; }
}