package com.aliro5.conlabac_api.model.dto;

import java.io.Serializable;

/**
 * Data Transfer Object (DTO) para la solicitud de envío de emails.
 * Transporta los datos desde conlabac-web hacia conlabac-api para procesar
 * envíos dinámicos según el centro (Tenerife o Gran Canaria).
 */
public class EmailRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String destinatario;
    private String asunto;
    private String mensaje;
    private Integer idCentro;
    private String dniEmisor;

    // --- Constructor Vacío (Obligatorio para JSON) ---
    public EmailRequestDTO() {
    }

    // --- Constructor Completo ---
    public EmailRequestDTO(String destinatario, String asunto, String mensaje, Integer idCentro, String dniEmisor) {
        this.destinatario = destinatario;
        this.asunto = asunto;
        this.setMensaje(mensaje); // Usamos el setter para aplicar el control de longitud
        this.idCentro = idCentro;
        this.dniEmisor = dniEmisor;
    }

    // --- Getters y Setters ---

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getMensaje() {
        return mensaje;
    }

    /**
     * Setea el mensaje limitándolo a una longitud segura para la columna EnEmTexto
     * de la tabla EnvioEmail, evitando errores de "Data truncation".
     */
    public void setMensaje(String mensaje) {
        if (mensaje != null && mensaje.length() > 2000) {
            this.mensaje = mensaje.substring(0, 2000);
        } else {
            this.mensaje = mensaje;
        }
    }

    public Integer getIdCentro() {
        return idCentro;
    }

    public void setIdCentro(Integer idCentro) {
        this.idCentro = idCentro;
    }

    public String getDniEmisor() {
        return dniEmisor;
    }

    public void setDniEmisor(String dniEmisor) {
        this.dniEmisor = dniEmisor;
    }

    @Override
    public String toString() {
        return "EmailRequestDTO{" +
                "destinatario='" + destinatario + '\'' +
                ", asunto='" + asunto + '\'' +
                ", idCentro=" + idCentro +
                ", dniEmisor='" + dniEmisor + '\'' +
                '}';
    }
}