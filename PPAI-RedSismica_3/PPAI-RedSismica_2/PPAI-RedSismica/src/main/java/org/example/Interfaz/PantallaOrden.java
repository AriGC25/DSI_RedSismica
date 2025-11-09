package org.example.Interfaz;

import org.example.controllers.PantallaOrdenController;
import org.example.models.MotivoTipo;

import java.util.List;
import java.util.Map;

//CLASE BOUNDARY
public class PantallaOrden extends PantallaOrdenController {

    public PantallaOrden() {
        super();
        this.gestor.setPantalla(this);
    }

    @Override
    protected void opsCerrarOrden() {
        habilitar();
    }

    private void habilitar() {
        gestor.tomarOpcCerrarOrd();
    }

    public void mostrarDatosOrden(int numeroOrden, String fechaFin, String nombreEstacion, int identificador) {
        System.out.println("Orden de Inspección: " + numeroOrden);
        System.out.println("Fecha de Cierre: " + fechaFin);
        System.out.println("Estación Sismológica: " + nombreEstacion);
        System.out.println("Identificador Sismógrafo: " + identificador);

        // Metodo java fx
        mostrarDatoEnTabla(numeroOrden, fechaFin, nombreEstacion, identificador);
    }

    @Override
    public void tomarNumeroOrden(int numeroOrden) {
        gestor.tomarOrdenPorNumero(numeroOrden);
    }

    @Override
    public void tomarObservacion(String observacion) {
        gestor.tomarObservacion(observacion);
    }

    @Override
    public void tomarMotivosFS(MotivoTipo motivoSeleccionado) {
        this.motivoSeleccionado = motivoSeleccionado;
    }

    @Override
    public void tomarComentariosFS(String comentario) {
        gestor.tomarMotivosYComentarios(motivosComentarios);
    }

    public void mostrarMotivos(List<MotivoTipo> motivos) {
        //Metodo java fx
        mostrarMotivosEnPantalla(motivos);
    }

    public void pedirConfirmacion(int nroOrdenSeleccionado) {
        //Metodo java fx
        pedirConfirmacionCierreOrden(nroOrdenSeleccionado);
    }

    @Override
    protected void tomarConfirmacion() {
        gestor.tomarConfirmacion();

        //metodo javar fx
        limpiarPantalla();
    }

    public void mostrarMensajeError(String titulo, String mensaje) {
        mostrarError(titulo, mensaje);
    }
}