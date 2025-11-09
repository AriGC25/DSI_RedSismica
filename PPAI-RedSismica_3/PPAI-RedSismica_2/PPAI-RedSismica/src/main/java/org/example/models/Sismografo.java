//CONSULTAR CHICAS LOS OTROS METODOS QUE NO USAMOS EN EL DIAGRAMA DE SECUENCIA
package org.example.models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Sismografo {
    private int identificadorSismografo;
    private String fechaAdquisicion;
    private int nroSerie;
    private EstacionSismologica estacion;
    private Estado estadoActual;
    private List<CambioEstado> cambiosEstado;

    public Sismografo(int identificadorSismografo, String fechaAdquisicion, int nroSerie) {
        this.identificadorSismografo = identificadorSismografo;
        this.nroSerie = nroSerie;
        this.fechaAdquisicion = fechaAdquisicion;
        this.cambiosEstado = new ArrayList();
        this.estadoActual = Estado.OPERATIVO;
        CambioEstado cambioInicial = new CambioEstado((String)null, (String)null, (String)null, (String)null, (Estado)null,(Empleado)null, List.of());
        cambioInicial.setEstado(this.estadoActual);
        this.cambiosEstado.add(cambioInicial);
    }

    //Métodos GET y SET
    public int getIdentificadorSismografo() {
        return this.identificadorSismografo;
    }

    public void setIdentificadorSismografo(int identificadorSismografo) {
        this.identificadorSismografo = identificadorSismografo;
    }

    public int getNroSerie() {
        return this.nroSerie;
    }

    public void setNroSerie(int nroSerie) {
        this.nroSerie = nroSerie;
    }

    public String getFechaAdquisicion() {
        return this.fechaAdquisicion;
    }

    public void setFechaAdquisicion(String fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }

    public Estado getEstadoActual() {
        return this.estadoActual;
    }

    public List<CambioEstado> getCambiosEstado() {
        return cambiosEstado;
    }

    public void setCambiosEstado(List<CambioEstado> cambiosEstado) {
        this.cambiosEstado = cambiosEstado;
    }


    //Otros métodos de Sismógrafo
    public void enviarAReparar(Empleado responsableInspeccion, List<MotivoTipo> motivoSeleccionado, List<String> comentarioIngresado, String horaActual, String fechaActual) {
        //1. Es estado actual
        for (CambioEstado cambio : this.cambiosEstado) {
            if (cambio.esActual()) {
                cambio.setFechaHoraFin(fechaActual, horaActual);
            }
        }

        crearCE(responsableInspeccion, motivoSeleccionado, comentarioIngresado, horaActual, fechaActual);
    }

    public void crearCE(Empleado responsableInspeccion, List<MotivoTipo> motivosSeleccionado, List<String> comentarioIngresado, String horaActual, String fechaActual) {
        //1. Creamos nuevo cambio de estado
        Estado estadoFS = Estado.FUERA_DE_SERVICIO;
        CambioEstado nuevoCambio = new CambioEstado(fechaActual, horaActual, (String)null, (String)null, estadoFS, responsableInspeccion, null);

        //2. Actualizamos el estado actual
        this.estadoActual = estadoFS;

        //3. Agregamos al historial
        nuevoCambio.setEstado(estadoFS);
        this.cambiosEstado.add(nuevoCambio);

        nuevoCambio.crearMotivoFS(comentarioIngresado);
        System.out.println("Motivos fueraseleeccionados: " + motivosSeleccionado);
    }
}
