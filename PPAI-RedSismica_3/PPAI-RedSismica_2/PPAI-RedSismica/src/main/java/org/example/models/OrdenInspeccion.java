package org.example.models;

import java.util.Iterator;
import java.util.List;

public class OrdenInspeccion {
    private int numeroOrden;
    private String fechaHoraInicio;
    private String fechaHoraFinalizacion;
    private String fechaCierre;
    private String observacionCierre;
    private Estado estado;
    private EstacionSismologica estacion;
    private Empleado empleadoResponsable;

    public OrdenInspeccion(int numeroOrden, String fechaHoraInicio, String fechaHoraFinalizacion, String fechaCierre, String observacionCierre, EstacionSismologica estacionSismologica, Empleado empleadoResponsable, Estado estado) {
        this.numeroOrden = numeroOrden;
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFinalizacion = fechaHoraFinalizacion;
        this.fechaCierre = fechaCierre;
        this.observacionCierre = observacionCierre;
        this.estacion = estacionSismologica;
        this.empleadoResponsable = empleadoResponsable;
        this.estado = estado;
    }

    //Métodos GET y SET
    public int getNumeroOrden() {
        return numeroOrden;
    }

    public String getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public String getFechaHoraFinalizacion() {
        return fechaHoraFinalizacion;
    }

    public String getFechaCierre() {
        return fechaCierre;
    }

    public String getObservacionCierre() {
        return observacionCierre;
    }

    public void setNumeroOrden(int numeroOrden) {
        this.numeroOrden = numeroOrden;
    }

    public void setFechaHoraInicio(String fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public void setFechaHoraFinalizacion(String fechaHoraFinalizacion) {
        this.fechaHoraFinalizacion = fechaHoraFinalizacion;
    }

    public void setFechaCierre(String fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public void setObservacionCierre (String observacionCierre){
        this.observacionCierre = observacionCierre;
    }

    public void setEstado (Estado estado){
        this.estado = estado;
    }

    public Estado getEstado () {
        return this.estado;
    }

    public EstacionSismologica getEstacion () {
        return this.estacion;
    }

    public void setEstacion (EstacionSismologica estacion){
        this.estacion = estacion;
    }

    public Empleado getEmpleadoResponsable () {
        return this.empleadoResponsable;
    }

    public void setEmpleadoResponsable (Empleado empleadoResponsable){
        this.empleadoResponsable = empleadoResponsable;
    }

   //Otros métodos de Orden Inspección
    public boolean esDeEmpleado (Empleado empleado){
        return this.empleadoResponsable != null && empleado != null && this.empleadoResponsable.getNombre().equals(empleado.getNombre());
    }

    public boolean esRealizada () {
        return estado != null && "Realizada".equalsIgnoreCase(estado.esFinalizada());
    }

    public String getNombreEstacion () {
        return this.estacion != null ? this.estacion.getNombre() : "Estación no asignada";
    }

    public int getIndentificador() {
        return this.estacion.getIdentificador();
    }

    public boolean estaCerrada () {
       return estado != null && "Cerrada".equalsIgnoreCase(estado.getNombre());
    }

    public void cerrar(String observacion, List<MotivoTipo> motivo, List<String> comentario, String hora, String fecha) {
        this.observacionCierre = observacion;
        this.estado = Estado.CERRADA;
    }

    public void enviarSismografoAReparar (Empleado responsableInspeccion, List<MotivoTipo> motivoSeleccionado, List<String> comentarioIngresado, String horaActual, String fechaActual) {
        if (this.estacion != null) {
            this.estacion.enviarAReparar(responsableInspeccion, motivoSeleccionado, comentarioIngresado, horaActual, fechaActual);}
    }

    public boolean validarObservacionYMotivos (String observacion, List<MotivoTipo> motivo){
        return observacion != null && !observacion.trim().isEmpty() && motivo != null && motivo.stream().noneMatch(m -> m.getDescripcion().trim().isEmpty());
    }

}

