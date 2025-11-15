package org.example.Interfaz;

import org.example.controllers.PantallaOrdenController;
import org.example.models.MotivoTipo;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

//CLASE BOUNDARY
public class PantallaOrden extends PantallaOrdenController {

    private String observacion;

    public PantallaOrden() {
        super();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Primero llamamos al initialize del padre para inicializar el sistema
        super.initialize(location, resources);

        // Configuramos la en el gestor
        if (this.gestor != null) {
            this.gestor.setPantalla(this);
        } else {
            System.err.println(">>> ERROR PantallaOrden: El gestor es NULL después de super.initialize()!");
        }
    }

    @Override
    protected void opsCerrarOrden() {
        habilitar();
    }

    private void habilitar() {
        if (gestor == null) {
            System.err.println(">>> ERROR CRÍTICO: gestor es NULL en habilitar()!");
            return;
        }
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
        pedirObservacion(observacion);
    }

    public void pedirObservacion(String observacion) {
        tomarObservacion(observacion);
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
    }

    public void mostrarMensajeError(String titulo, String mensaje) {
        mostrarError(titulo, mensaje);
    }
}