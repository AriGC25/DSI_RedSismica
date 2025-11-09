package org.example.models;

import java.util.List;

public class EstacionSismologica {
    private int codigoEstacion;
    private String documentoCertificacionAdq;
    private String fechaSolicitudCertificacion;
    private String latitud;
    private String longitud;
    private String nombre;
    private int nroCeritifacionAdquisicion;
    private Sismografo sismografo;

    public EstacionSismologica(int codigoEstacion, String documentoCertificacionAdq, String fechaSolicitudCertificacion, String latitud,
                               String longitud, String nombre, int numeroCeritifacionAdquisicion) {
        this.codigoEstacion = codigoEstacion;
        this.documentoCertificacionAdq = documentoCertificacionAdq;
        this.fechaSolicitudCertificacion = fechaSolicitudCertificacion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.nombre = nombre;
        this.nroCeritifacionAdquisicion = numeroCeritifacionAdquisicion;
    }

    //Métodos GET y SET
    public int getCodigoEstacion() {
        return codigoEstacion;
    }

    public void setCodigoEstacion(int codigoEstacion) {
        this.codigoEstacion = codigoEstacion;
    }

    public String getDocumentoCertificacionAdq() {
        return documentoCertificacionAdq;
    }

    public void setDocumentoCertificacionAdq(String documentoCertificacionAdq) {
        this.documentoCertificacionAdq = documentoCertificacionAdq;
    }

    public String getFechaSolicitudCertificacion() {
        return fechaSolicitudCertificacion;
    }

    public void setFechaSolicitudCertificacion(String fechaSolicitudCertificacion) {
        this.fechaSolicitudCertificacion = fechaSolicitudCertificacion;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumeroCeritifacionAdquisicion() {
        return nroCeritifacionAdquisicion;
    }

    public void setNumeroCeritifacionAdquisicion(int numeroCeritifacionAdquisicion) {
        this.nroCeritifacionAdquisicion = nroCeritifacionAdquisicion;
    }

    public Sismografo getSismografo() {
        return this.sismografo;
    }

    public void setSismografo(Sismografo sismografo) {
        this.sismografo = sismografo;
    }

    //Otros métodos de Estación Sismológica
    public void enviarAReparar(Empleado responsableInspeccion, List<MotivoTipo> motivoSeleccionado, List<String> comentarioIngresado, String horaActual, String fechaActual) {
        if (this.sismografo != null) {
            this.sismografo.enviarAReparar(responsableInspeccion, motivoSeleccionado, comentarioIngresado, horaActual, fechaActual);
        }
    }

    public int getIdentificador(){
        return sismografo.getIdentificadorSismografo();
    }
}
