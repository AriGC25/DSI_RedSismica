package org.example.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import org.example.Interfaz.InterfazEmail;
import org.example.Interfaz.InterfazPantalla;
import org.example.controlador.GestorOrden;
import org.example.models.*;
import org.example.util.OrdenInspeccionVisualDTO;

import java.net.URL;
import java.util.*;

// CLASE QUE CONTIENE TODA LA LOGICA DE JAVA FX
public abstract class PantallaOrdenController implements Initializable {

    // === ELEMENTOS DE LA INTERFAZ GRÁFICA ===
    @FXML protected Label lblSistemaInfo;
    @FXML protected Label lblVersion;
    @FXML protected Button btnCerrarOrden;
    @FXML protected Button btnSalir;
    @FXML protected Button btnAgregarBloque;

    @FXML protected TableView<OrdenInspeccionVisualDTO> tableOrdenes;
    @FXML protected TableColumn<OrdenInspeccionVisualDTO, Integer> colNumero;
    @FXML protected TableColumn<OrdenInspeccionVisualDTO, String> colEstado;
    @FXML protected TableColumn<OrdenInspeccionVisualDTO, String> colFecha;
    @FXML protected TableColumn<OrdenInspeccionVisualDTO, Integer> colIdentificador;

    @FXML protected VBox panelCierre;
    @FXML protected TextField txtNumeroOrden;
    @FXML protected TextArea txtObservacion;
    @FXML protected TextArea txtComentarios;
    @FXML protected Button btnConfirmarCierre;
    @FXML protected Button btnCancelar;
    @FXML
    private VBox motivosContainer; // vinculado al VBox que agregaste en el FXML
    private Map<MotivoTipo, Pair<CheckBox, TextField>> motivoCheckboxMap = new HashMap<>();

    private Scanner scanner;
    private ObservableList<OrdenInspeccionVisualDTO> ordenesData = FXCollections.observableArrayList();
    private ObservableList<MotivoTipo> motivosData = FXCollections.observableArrayList();
    protected GestorOrden gestor;
    protected MotivoTipo motivoSeleccionado;
    protected String comentario;
    protected Map<MotivoTipo, String> motivosComentarios = new HashMap<>();

    protected PantallaOrdenController() {
        this.scanner = new Scanner(System.in);
        inicializarSistema();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        configurarInterfaz();
        mostrarInformacionSistema();

        if (panelCierre != null) {
            panelCierre.setVisible(false);
        }

        // Añadimos listeners para habilitar controles progresivamente
        if (txtNumeroOrden != null) {
            txtNumeroOrden.textProperty().addListener((obs, oldVal, newVal) -> validarPaso1());
        }
        if (txtObservacion != null) {
            txtObservacion.textProperty().addListener((obs, oldVal, newVal) -> validarPaso2());
        }

        // Inicialmente deshabilitar controles relacionados al cierre
        if (txtObservacion != null) txtObservacion.setDisable(true);
        if (motivosContainer != null) motivosContainer.setDisable(true);
        if (txtComentarios != null) txtComentarios.setDisable(true);
        if (btnConfirmarCierre != null) btnConfirmarCierre.setDisable(true);

        txtObservacion.focusedProperty().addListener((obs, oldVal, newVal) -> {
            tomarObservacion(txtObservacion.getText().trim());
        });

        generarMotivosContainer();
    }

    private void validarPaso1() {
        boolean valido = !txtNumeroOrden.getText().trim().isEmpty();
        txtObservacion.setDisable(!valido);

        if (!valido) {
            txtComentarios.setDisable(true);
            btnConfirmarCierre.setDisable(true);
            txtObservacion.clear();
            txtComentarios.clear();
        }
    }

    private void validarPaso2() {
        boolean valido = !txtObservacion.getText().trim().isEmpty();

        if (!valido) {
            btnConfirmarCierre.setDisable(true);
        }
        motivosContainer.setDisable(false);
    }

    private void validarPaso3() {
        boolean motivoSeleccionado = motivosComentarios.isEmpty();
        btnConfirmarCierre.setDisable(motivoSeleccionado);
    }

    private void inicializarSistema() {

        //Rol
        Rol rolResponsable = new Rol("Responsable de Inspecciones", "Encargado de gestionar inspecciones");
        Rol rolTecnico = new Rol("Técnico", "Técnico de campo");

        //Empleado
        Empleado empleado = new Empleado("Juan Pérez", "juanperez@email.com");
        empleado.setRol(rolResponsable);
        Empleado empleado2 = new Empleado("María García", "maria.garcia@empresa.com");
        empleado2.setRol(rolTecnico);
        List<Empleado> empleados = List.of(empleado, empleado2);

        //Usuario logeado
        Usuario usuario = new Usuario("admin", "admin123", empleado);

        //Sesión actual
        Sesion sesion = new Sesion("2025-01-01 8:00", "2025-01-01 17:00", usuario);

        //Estación sismológica
        EstacionSismologica est1 = new EstacionSismologica(101, "01CD", "2020-02-01", "-34.6118", "-58.3960", "Estación Central", 21458);
        Sismografo sismo1 = new Sismografo(101, "2020-10-21", 2000);
        est1.setSismografo(sismo1);
        EstacionSismologica est2 = new EstacionSismologica(101, "01CD", "2020-05-10", "35.6118", "-55.3960", "Estación Norte", 15874);
        Sismografo sismo2 = new Sismografo(102, "2021-08-20", 2001);
        est2.setSismografo(sismo2);
        EstacionSismologica est3 = new EstacionSismologica(101, "01CD", "2018-06-10", "33.6118", "-58.3960", "Estación Sur", 78541);
        Sismografo sismo3 = new Sismografo(103, "2023-01-10", 2010);
        est3.setSismografo(sismo3);

        //Estado
        Estado estado = Estado.REALIZADA;
        List<Estado> estados = Estado.obtenerEstados();

        //Ordenes
        OrdenInspeccion orden1 = new OrdenInspeccion(1, "2025-01-01 10:00", "2025-01-01 11:00", "2025-01-01", "Observacion ciere 1", est1, empleado,estado);
        OrdenInspeccion orden2 = new OrdenInspeccion(2, "2025-01-01 10:00", "2025-02-01 14:00", "2025-01-01", "Observacion ciere 2", est2, empleado, estado);
        OrdenInspeccion orden3 = new OrdenInspeccion(3, "2025-01-01 10:00", "2025-05-01 10:00", "2025-01-01", "Observacion ciere 3", est3, empleado, estado);
        List<OrdenInspeccion> ordenes = List.of(orden1, orden2, orden3);

        //Interfaz email
        InterfazEmail interfazEmail = new InterfazEmail("");

        //Interfaz pantalla
        InterfazPantalla interfazPantalla = new InterfazPantalla("");

        //Gestor
        gestor = new GestorOrden(sesion, ordenes, estados, empleados, interfazEmail, interfazPantalla);
    }

    private void mostrarInformacionSistema() {
        if (lblSistemaInfo != null) {
            lblSistemaInfo.setText("Sistema: Gestión de Órdenes de Inspección Sismográfica");
        }
        if (lblVersion != null) {
            lblVersion.setText("Versión: 2.0 - Con datos precargados | Funcionalidad: Cerrar Orden de Inspección");
        }

        System.out.println("\n=== INFORMACIÓN DEL SISTEMA ===");
        System.out.println("Sistema: Gestión de Órdenes de Inspección Sismográfica");
        System.out.println("Versión: 2.0 - Con datos precargados");
        System.out.println("Funcionalidad: Cerrar Orden de Inspección");
        System.out.println("================================");
    }

    private void configurarInterfaz() {
        tableOrdenes.setItems(ordenesData);
        colNumero.setCellValueFactory(new PropertyValueFactory<>("numeroDeOrden"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("EstadoActual"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaGeneracion"));
        colIdentificador.setCellValueFactory(new PropertyValueFactory<>("identificador"));
    }

    @FXML
    private void onCerrarOrdenClick() {
        System.out.println("boton apretado");
        opsCerrarOrden();
    }

    protected abstract void opsCerrarOrden();

    protected void mostrarDatoEnTabla(int numeroOrden, String fecha, String nombre, int identificador) {
        ordenesData.add(new OrdenInspeccionVisualDTO(numeroOrden, fecha, nombre, identificador));
        mostrarPanelSeleccionOrden();
    }

    private void mostrarPanelSeleccionOrden() {
        if (panelCierre != null) {
            panelCierre.setVisible(true);
        }

        if (txtNumeroOrden != null) txtNumeroOrden.clear();

        if (txtObservacion != null) {
            txtObservacion.clear();
            txtObservacion.setDisable(true);
        }

        if (txtComentarios != null) {
            txtComentarios.clear();
            txtComentarios.setDisable(true);
        }

        if (btnConfirmarCierre != null) btnConfirmarCierre.setDisable(true);

        for (Pair<CheckBox, TextField> pair : motivoCheckboxMap.values()) {
            pair.getKey().setSelected(false);
            pair.getValue().clear();
            pair.getValue().setDisable(true);
        }

        motivosComentarios.clear();
    }

    protected void mostrarMotivosEnPantalla(List<MotivoTipo> motivos) {
        motivosData.clear();
        motivosData.addAll(motivos);
    }

    @FXML
    private void onConfirmarCierreClick() {
        boolean valido = validarCamposCierreOrden();
        if (!valido) {
            System.out.println(">>> Campos de cierre de orden inválidos.");
            return;
        }

        int numeroOrden = Integer.parseInt(txtNumeroOrden.getText().trim());
        String observacion = txtObservacion.getText().trim();


        tomarNumeroOrden(numeroOrden);
        tomarObservacion(observacion);
        obtenerMotivosSeleccionados();
        tomarMotivosFS(motivoSeleccionado);
        tomarComentariosFS(comentario);
    }

    protected abstract void tomarNumeroOrden(int numeroOrden);
    protected abstract void tomarObservacion(String observacion);
    protected abstract void tomarMotivosFS(MotivoTipo motivoTipoSeleccionado);
    protected abstract void tomarComentariosFS(String comentario);

    private boolean validarCamposCierreOrden() {
        // Validacion numero de orden
        String nroOrdenText = txtNumeroOrden.getText().trim();
        if (nroOrdenText.isEmpty()) {
            mostrarAdvertencia("Campo requerido", "Debe ingresar el número de orden.");
            return false;
        }

        int nroOrden;
        try {
            nroOrden = Integer.parseInt(nroOrdenText);
        } catch (NumberFormatException e) {
            mostrarError("Número inválido", "El número de orden debe ser un número entero.");
            return false;
        }

        if (nroOrden <= 0) {
            mostrarError("Número inválido", "El número de orden debe ser positivo.");
            return false;
        }

        // Validacion observacion
        String observacion = txtObservacion.getText().trim();
        if (observacion.isEmpty()) {
            mostrarAdvertencia("Campo requerido", "Debe ingresar una observación.");
            txtObservacion.requestFocus();
            txtObservacion.setStyle("-fx-border-color: red;");
            return false;
        }

        return true;
    }

    protected void pedirConfirmacionCierreOrden(int nroOrden) {
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar Cierre");
        confirmacion.setHeaderText("Cierre de Orden de Inspección");
        confirmacion.setContentText("¿Confirma el cierre de la orden #" + nroOrden + "?");

        Optional<ButtonType> resultado = confirmacion.showAndWait();
        boolean confirmado = resultado.isPresent() && resultado.get() == ButtonType.OK;

        if (confirmado) {
            mostrarExito("Orden cerrada", "La orden #" + nroOrden + " ha sido cerrada exitosamente.");
            ocultarPanelCierre();
            tomarConfirmacion();

        } else {
            mostrarInformacion("Operación cancelada", "El cierre de la orden ha sido cancelado.");
            System.out.println(">>> Cierre de orden cancelado por el usuario.");
        }
    }

    protected abstract void tomarConfirmacion();

    @FXML
    private void onCancelarClick() {
        ocultarPanelCierre();
        ordenesData.clear();
        System.out.println(">>> Proceso de cierre cancelado por el usuario.");
    }

    private void ocultarPanelCierre() {
        if (panelCierre != null) {
            panelCierre.setVisible(false);
        }
    }

    @FXML
    private void onSalirClick() {
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Salir");
        confirmacion.setHeaderText("Salir del Sistema");
        confirmacion.setContentText("¿Está seguro que desea salir del sistema?");

        Optional<ButtonType> resultado = confirmacion.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            cerrarSistema();
            System.exit(0);
        }
    }

    private void cerrarSistema() {
        try {
            this.scanner.close();
            System.out.println("\n=== SISTEMA FINALIZADO ===");
        } catch (Exception e) {
            System.err.println("Error al cerrar sistema: " + e.getMessage());
        }
    }

    protected void mostrarError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarAdvertencia(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setHeaderText(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarInformacion(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarExito(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    protected void limpiarPantalla() {
        // Limpiar tabla de ordenes
        ordenesData.clear();
    }
    private void generarMotivosContainer() {
        for (MotivoTipo motivo : MotivoTipo.values()) {
            CheckBox checkBox = new CheckBox(motivo.getDescripcion());
            TextField comentario = new TextField();
            comentario.setPromptText("Comentario sobre " + motivo.getDescripcion());
            comentario.setDisable(true);

            // Habilitar el campo solo si se selecciona
            checkBox.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
                comentario.setDisable(!isNowSelected);
                if (!isNowSelected) comentario.clear();
                actualizarEstadoBotonConfirmar();
            });

            VBox motivoBox = new VBox(5, checkBox, comentario);
            motivoBox.setPadding(new Insets(5));
            motivoBox.setStyle("-fx-border-color: #ced4da; -fx-border-radius: 4; -fx-background-radius: 4; -fx-padding: 5; -fx-background-color: #ffffff;");

            motivosContainer.getChildren().add(motivoBox);
            motivoCheckboxMap.put(motivo, new Pair<>(checkBox, comentario));
        }
    }

    public Map<MotivoTipo, String> obtenerMotivosSeleccionados() {
        Map<MotivoTipo, String> seleccionados = new HashMap<>();
        for (Map.Entry<MotivoTipo, Pair<CheckBox, TextField>> entry : motivoCheckboxMap.entrySet()) {
            if (entry.getValue().getKey().isSelected()) {
                seleccionados.put(entry.getKey(), entry.getValue().getValue().getText());
            }
        }
        motivosComentarios = seleccionados;
        return seleccionados;
    }

    private void actualizarEstadoBotonConfirmar() {
        boolean alMenosUnoSeleccionado = motivoCheckboxMap.values().stream()
                .anyMatch(pair -> pair.getKey().isSelected());
        btnConfirmarCierre.setDisable(!alMenosUnoSeleccionado);
    }

}