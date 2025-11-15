package org.example.controlador;

import org.example.Interfaz.InterfazEmail;
import org.example.Interfaz.InterfazPantalla;
import org.example.Interfaz.PantallaOrden;
import org.example.models.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import org.example.Interfaz.IObservador;

import org.example.Interfaz.ISujeto;
import org.example.util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;


//CLASE GESTOR
public class GestorOrden implements ISujeto {
    private PantallaOrden pantalla;
    private Sesion sesion;
    private OrdenInspeccion ordenSeleccionada;
    private List<OrdenInspeccion> ordenesInspeccion;
    private Empleado empleadoLogueado;
    private Usuario usuario;
    private int numeroOrdenSeleccionado;
    private String observacionCierre;
    private List<MotivoTipo> motivoSeleccionado;
    private List<String> comentarioIngresado;
    private List<Estado> estados;
    private List<Empleado> empleados;
    private InterfazEmail interfazEmail;
    private InterfazPantalla interfazPantalla;
    private List<String> emailsResponsables;
    private List<IObservador> observadores;
    private String datosNotificacion_identificadorSismografo;
    private String datosNotificacion_nuevoEstado;
    private String datosNotificacion_fechaHora;
    private List<String> datosNotificacion_motivos;
    private String datosNotificacion_comentarios;

    public GestorOrden(Sesion sesion, List<OrdenInspeccion> ordenesInspeccion, List<Estado> estados, List<Empleado> empleados, InterfazEmail interfazEmail, InterfazPantalla interfazPantalla) {
        this.sesion = sesion;
        this.ordenesInspeccion = ordenesInspeccion;
        this.estados = estados;
        this.empleados = empleados;
        this.interfazEmail = interfazEmail;
        this.interfazPantalla = interfazPantalla;
        this.observadores = new ArrayList<>();
    }

    //Métodos GET y SET
    public void setPantalla(PantallaOrden pantalla) {
        this.pantalla = pantalla;
    }

    public PantallaOrden getPantalla() {
        return pantalla;
    }

    public Sesion getSesion() {
        return sesion;
    }

    public void setSesion(Sesion sesion) {
        this.sesion = sesion;
    }

    public OrdenInspeccion getOrdenSeleccionada() {
        return ordenSeleccionada;
    }

    public void setOrdenSeleccionada(OrdenInspeccion ordenSeleccionada) {
        this.ordenSeleccionada = ordenSeleccionada;
    }

    public List<OrdenInspeccion> getOrdenesInspeccion() {
        return ordenesInspeccion;
    }

    public void setOrdenesInspeccion(List<OrdenInspeccion> ordenesInspeccion) {
        this.ordenesInspeccion = ordenesInspeccion;
    }

    public Empleado getEmpleadoLogueado() {
        return empleadoLogueado;
    }

    public void setEmpleadoLogueado(Empleado empleadoLogueado) {
        this.empleadoLogueado = empleadoLogueado;
    }

    public int getNumeroOrdenSeleccionado() {
        return numeroOrdenSeleccionado;
    }

    public void setNumeroOrdenSeleccionado(int numeroOrdenSeleccionado) {
        this.numeroOrdenSeleccionado = numeroOrdenSeleccionado;
    }

    public String getObservacionCierre() {
        return observacionCierre;
    }

    public void setObservacionCierre(String observacionCierre) {
        this.observacionCierre = observacionCierre;
    }

    public List<MotivoTipo> getMotivoSeleccionado() {
        return motivoSeleccionado;
    }

    public void setMotivoSeleccionado(List<MotivoTipo> motivoSeleccionado) {
        this.motivoSeleccionado = motivoSeleccionado;
    }

    public List<String> getComentarioIngresado() {
        return comentarioIngresado;
    }

    public void setComentarioIngresado(List<String> comentarioIngresado) {
        this.comentarioIngresado = comentarioIngresado;
    }

    public List<Estado> getEstados() {
        return estados;
    }

    public void setEstados(List<Estado> estados) {
        this.estados = estados;
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }

    public InterfazEmail getInterfazEmail() {
        return interfazEmail;
    }

    public void setInterfazEmail(InterfazEmail interfazEmail) {
        this.interfazEmail = interfazEmail;
    }

    public InterfazPantalla getInterfazPantalla() {
        return interfazPantalla;
    }

    public void setInterfazPantalla(InterfazPantalla interfazPantalla) {
        this.interfazPantalla = interfazPantalla;
    }

    public List<String> getEmailsResponsables() {
        return emailsResponsables;
    }

    public void setEmailsResponsables(List<String> emailsResponsables) {
        this.emailsResponsables = emailsResponsables;
    }

    // --- Métodos del patrón Observer ---
    @Override
    public void suscribir(IObservador observador) {
        if (!observadores.contains(observador)) {
            observadores.add(observador);
        }
    }

    @Override
    public void quitar(IObservador observador) {
        observadores.remove(observador);
    }

    @Override
    public void notificar() {
        for (IObservador observador : observadores) {
            observador.actualizar(
                    datosNotificacion_identificadorSismografo,
                    datosNotificacion_nuevoEstado,
                    datosNotificacion_fechaHora,
                    datosNotificacion_motivos,
                    datosNotificacion_comentarios,
                    emailsResponsables
            );
        }
    }

    //Otros métodos del Gestor
    public void tomarOpcCerrarOrd() {
        empleadoLogueado = buscarEmpleadoRI();
        buscarOrdInspeccion();
    }

    private Empleado buscarEmpleadoRI() {
        usuario = sesion.obtenerUsuario();
        return usuario.obtenerEmpleado();
    }

    private void buscarOrdInspeccion() {
        List<OrdenInspeccion> ordenesFiltradas = new ArrayList<>();
        for (OrdenInspeccion orden : ordenesInspeccion) {

            if (orden.esDeEmpleado(empleadoLogueado) && orden.esRealizada()) {
                ordenesFiltradas.add(orden);
            }
        }

        for (OrdenInspeccion orden : ordenesFiltradas) {
            var numero = orden.getNumeroOrden();
            var fechaFin = orden.getFechaHoraFinalizacion();
            var nombreEstacion = orden.getNombreEstacion();
            int identificador = orden.getIndentificador();
            //ordenarOI ver de agregar
            pantalla.mostrarDatosOrden(numero, fechaFin, nombreEstacion, identificador);
        }
    }

    public boolean tomarOrdenPorNumero(int numeroOrden) {
        this.numeroOrdenSeleccionado = numeroOrden;
        //Busca orden seleccionada
        var OrdenSeleccionadaOptional = ordenesInspeccion.stream()
                .filter(orden -> orden.getNumeroOrden() == numeroOrdenSeleccionado)
                .findFirst();

        //Validación
        if(OrdenSeleccionadaOptional.isEmpty()) {
            pantalla.mostrarMensajeError("Orden no encontrada", "No se encontró una orden de inspección con el número: " + numeroOrdenSeleccionado);
            return false;
        }

        ordenSeleccionada = OrdenSeleccionadaOptional.get();
        return true;
    }

    public void tomarObservacion(String observacion) {
        this.observacionCierre = observacion;
        buscarMotivos();
    }

    public void buscarMotivos() {
        var motivos = MotivoTipo.obtenerMotivosFS();
        pantalla.mostrarMotivos(motivos);
    }

    public void tomarMotivosYComentarios(Map<MotivoTipo, String> motivosComentarios) {
        this.motivoSeleccionado = motivosComentarios.keySet().stream().toList();
        this.comentarioIngresado = motivosComentarios.values().stream().toList();
        pantalla.pedirConfirmacion(numeroOrdenSeleccionado);
    }

    public void tomarConfirmacion() {
        validarObservacionYMotivo();
    }

    public void validarObservacionYMotivo(){
        boolean valido = ordenSeleccionada.validarObservacionYMotivos(observacionCierre, motivoSeleccionado);

        // Si no es válido, mostrar error y salir
        if (!valido) {
            pantalla.mostrarMensajeError("Error en validación", "La observación o el motivo seleccionado no son válidos.");
            return;
        }

        EstacionSismologica estacion = buscarEstacionFS();

        Estado estadoFueraServicio = null;
        for (Estado estado: estados) {
            if (estado.esAmbitoSismografo() && estado.esFueraDeServicio()) {
                estadoFueraServicio = estado;
                break;
            }
        }

        Estado estadoCerrada = buscarEstadoCerrada();

        String horaActual = obtenerHoraActual();
        String fechaActual = obtenerFechaActual();

        cerrarOrden(horaActual, fechaActual);
        enviarAReparar(empleadoLogueado, motivoSeleccionado, comentarioIngresado, horaActual, fechaActual);

        // Obtener emails de responsables antes de preparar datos y notificar
        this.emailsResponsables = obtenerEmailsResponsables();

        this.observadores = new ArrayList<>();

        // El gestor crea los observadores
        IObservador notificadorEmail = new InterfazEmail();
        IObservador notificadorPantalla = new InterfazPantalla();

        this.suscribir(notificadorEmail);
        this.suscribir(notificadorPantalla);

        // Preparar datos y notificar
        this.enviarNotificaciones(fechaActual, horaActual, estadoCerrada);

        finCasoDeUso();
    }

    private EstacionSismologica buscarEstacionFS() {
        return ordenSeleccionada.getEstacion();
    }

    private Estado buscarEstadoCerrada() {
        Estado estadoCerrada = null;
        for (Estado estado: estados) {
            if (estado.esAmbitoOrden() && estado.esCerrada()) {
                estadoCerrada = estado;
                break;
            }
        }
        return estadoCerrada;
    }

    private String obtenerHoraActual() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    private String obtenerFechaActual() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    private void cerrarOrden(String horaActual, String fechaActual) {
        ordenSeleccionada.cerrar(observacionCierre, motivoSeleccionado, comentarioIngresado, horaActual, fechaActual);
        //Persistimos el cierre
        HibernateUtil.saveOrUpdate(ordenSeleccionada);

    }

    private void enviarAReparar(Empleado responsableInspeccion, List<MotivoTipo> motivoSeleccionado, List<String> comentarioIngresado, String horaActual, String fechaActual) {
        ordenSeleccionada.enviarSismografoAReparar(responsableInspeccion, motivoSeleccionado, comentarioIngresado, horaActual, fechaActual);
    }

    private List<String> obtenerEmailsResponsables() {
        List<String> emails = new ArrayList<>();
        for (Empleado empleado : empleados) {
            if (empleado.esResponsable()) {
                String email = empleado.obtenerEmail();
                if (email != null && !email.isEmpty()) {
                    emails.add(email);
                }
            }
        }
        return emails;
    }

    private void enviarNotificaciones(String fechaActual, String horaActual, Estado estadoCerrada) {
        // 1. Recopila los datos necesarios para la notificación desde las variables reales
        int identificadorSismografo = ordenSeleccionada.getEstacion().getIdentificador();
        this.datosNotificacion_identificadorSismografo = String.valueOf(identificadorSismografo);
        this.datosNotificacion_nuevoEstado = estadoCerrada != null ? estadoCerrada.getNombre() : "CERRADA";
        this.datosNotificacion_fechaHora = fechaActual + " " + horaActual;

        // Convertir todos los MotivoTipo a sus descripciones (String)
        this.datosNotificacion_motivos = new ArrayList<>();
        if (motivoSeleccionado != null && !motivoSeleccionado.isEmpty()) {
            for (MotivoTipo motivo : motivoSeleccionado) {
                this.datosNotificacion_motivos.add(motivo.getDescripcion());
            }
        }

        // Concatenar todos los comentarios si hay múltiples
        this.datosNotificacion_comentarios = "";
        if (comentarioIngresado != null && !comentarioIngresado.isEmpty()) {
            this.datosNotificacion_comentarios = String.join("; ", comentarioIngresado);
        }

        // 2. Llama al metodo para notificar a todos los suscriptores
        this.notificar();
    }


    private void finCasoDeUso() {
        System.out.println("----------- FIN DE CASO DE USO --------------");
    }
}
