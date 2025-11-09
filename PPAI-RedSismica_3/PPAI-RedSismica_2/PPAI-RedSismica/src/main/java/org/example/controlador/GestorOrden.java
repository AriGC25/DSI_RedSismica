package org.example.controlador;

import org.example.Interfaz.InterfazEmail;
import org.example.Interfaz.InterfazPantalla;
import org.example.Interfaz.PantallaOrden;
import org.example.models.*;

import java.security.spec.RSAOtherPrimeInfo;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

//CLASE GESTOR
public class GestorOrden {
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

    public GestorOrden(Sesion sesion, List<OrdenInspeccion> ordenesInspeccion, List<Estado> estados, List<Empleado> empleados, InterfazEmail interfazEmail, InterfazPantalla interfazPantalla) {
        this.sesion = sesion;
        this.ordenesInspeccion = ordenesInspeccion;
        this.estados = estados;
        this.empleados = empleados;
        this.interfazEmail = interfazEmail;
        this.interfazPantalla = interfazPantalla;
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

        EstacionSismologica estacion = buscarEstacionFS();

        Estado estadoFueraServico = null;
        for (Estado estado: estados) {
            if (estado.esAmbitoSismografo() && estado.esFueraDeServicio()) {
                estadoFueraServico = estado;
                break;
            }
        }

        buscarEstadoCerrada();

        boolean estaCerrada = ordenSeleccionada.estaCerrada();
        if (!valido || estaCerrada) {
            pantalla.mostrarMensajeError("La observación o el motivo seleccionado no son válidos.","Por favor, verifique los datos ingresados.");
        }

        String horaActual = obtenerHoraActual();
        String fechaActual = obtenerFechaActual();

        cerrarOrden(horaActual, fechaActual);
        enviarAReparar(empleadoLogueado, motivoSeleccionado, comentarioIngresado, horaActual, fechaActual);

        if(valido){
            List<String> emailsResponsables = obtenerEmailsResponsables();
            enviarNotificacionesEmail(emailsResponsables);
            publicarMonitor(estacion.getNombre());
        }

        finCasoDeUso();
    }

    private EstacionSismologica buscarEstacionFS() {
        return ordenSeleccionada.getEstacion();
    }

    private void buscarEstadoCerrada() {
        Estado estadoCerrada = null;
        for (Estado estado: estados) {
            if (estado.esAmbitoOrden() && estado.esCerrada()) {
                estadoCerrada = estado;
                break;
            }
        }
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

    private void enviarNotificacionesEmail(List<String> emails) {
        interfazEmail.enviarEmail(emails);
    }

    private void publicarMonitor(String nombreEstacion) {
        interfazPantalla.publicar(nombreEstacion);
    }

    private void finCasoDeUso() {
        System.out.println("----------- FIN DE CASO DE USO --------------");
    }
}
