package org.example.models;

import java.util.List;

public class CambioEstado {
    private String horaFin;
    private String fechaFin;
    private String horaInicio;
    private String fechaInicio;
    private Estado estado;
    private List<String> motivoFueraServicio;
    private Empleado empleadoResponsable;

    public CambioEstado(String horaFin, String fechaFin, String horaInicio, String fechaInicio, Estado estado, Empleado empleadoResponsable, List<String> motivoFueraServicio) {
        this.horaFin = horaFin;
        this.fechaFin = fechaFin;
        this.horaInicio = horaInicio;
        this.fechaInicio = fechaInicio;
        this.estado = estado;
        this.empleadoResponsable = empleadoResponsable;
        this.motivoFueraServicio = motivoFueraServicio;
    }

    //Métodos GET y SET
    public String getHoraFin() {
        return this.horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public String getFechaFin() {
        return this.fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getHoraInicio() {
        return this.horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getFechaInicio() {
        return this.fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Estado getEstado() {
        return this.estado;
    }

    public List<String> getMotivoFueraServicio() {
        return motivoFueraServicio;
    }

    public List<String> setMotivoFueraServicio(List<String> motivoFueraServicio) {
        return this.motivoFueraServicio = motivoFueraServicio;
    }

    public Empleado getEmpleadoResponsable() {
        return empleadoResponsable;
    }

    public void setEmpleadoResponsable(Empleado empleadoResponsable) {
        this.empleadoResponsable = empleadoResponsable;
    }

    //Otros métodos de Cambio de Estado
    public void setFechaHoraFin(String fecha, String hora) {
        this.fechaFin = fecha;
        this.horaFin = hora;
    }

    public void crearMotivoFS(List<String> comentarioIngresado) {
        this.motivoFueraServicio = comentarioIngresado;
    }

    public boolean esActual() {
        return this.horaFin == null || this.fechaFin == null;
    }
}
