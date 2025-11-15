package org.example.controllers;


import org.example.controllers.OrdenListController.OrdenInspeccion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CerrarOrdenController {

    @FXML private Label lblOrdenInfo;
    @FXML private Label lblNumeroOrden;
    @FXML private Label lblFechaGeneracion;
    @FXML private Label lblTipoMotivo;
    @FXML private Label lblEstacion;
    @FXML private Label lblEstadoActual;
    @FXML private Label lblResponsable;

    @FXML private TextArea txtObservacion;
    @FXML private Label lblErrorObservacion;

    @FXML private CheckBox rbFallaHardware;
    @FXML private CheckBox rbFallaSoftware;
    @FXML private CheckBox rbMantenimientoPreventivo;
    @FXML private CheckBox rbCalibracionNecesaria;
    @FXML private CheckBox rbDanoExterno;
    @FXML private CheckBox rbInterferenciaElectromagnetica;
    @FXML private CheckBox rbProblemasConectividad;

    @FXML private Label lblErrorMotivo;

    @FXML private VBox resumenContainer;
    @FXML private VBox resumenDetalles;

    @FXML private Button btnValidar;
    @FXML private Button btnConfirmar;
    @FXML private Button btnCancelar;

    private OrdenInspeccion ordenSeleccionada;

    @FXML
    private void initialize() {
        // Configurar TextArea de observación
        if (txtObservacion != null) {
            txtObservacion.setEditable(true);
            txtObservacion.setDisable(false);
            txtObservacion.setWrapText(true);
            txtObservacion.setStyle("-fx-opacity: 1.0;");
            System.out.println("Campo txtObservacion configurado correctamente");
        } else {
            System.err.println("ERROR: txtObservacion es null!");
        }


        // Ocultar mensajes de error inicialmente
        lblErrorObservacion.setVisible(false);
        lblErrorMotivo.setVisible(false);
        resumenContainer.setVisible(false);

        System.out.println("=== INICIALIZACIÓN COMPLETA ===");
    }

    public void setOrdenSeleccionada(OrdenInspeccion orden) {
        this.ordenSeleccionada = orden;
        cargarDatosOrden();
    }

    private void cargarDatosOrden() {
        if (ordenSeleccionada != null) {
            lblOrdenInfo.setText("Orden #" + ordenSeleccionada.getNumero() + " - Estación: " + ordenSeleccionada.getEstacion());
            lblNumeroOrden.setText("#" + ordenSeleccionada.getNumero());
            lblFechaGeneracion.setText(ordenSeleccionada.getFechaGeneracion());
            lblTipoMotivo.setText(ordenSeleccionada.getTipoMotivo());
            lblEstacion.setText(ordenSeleccionada.getEstacion());
            lblEstadoActual.setText(ordenSeleccionada.getEstado());
            lblResponsable.setText(ordenSeleccionada.getResponsable());
        }
    }

    @FXML
    private void handleValidar(ActionEvent event) {
        System.out.println(">>> PANTALLA: ================================");
        System.out.println(">>> PANTALLA: VALIDANDO DATOS DE CIERRE");
        System.out.println(">>> PANTALLA: ================================");

        boolean datosValidos = true;

        // Validar observación
        String observacion = txtObservacion.getText().trim();
        if (observacion.isEmpty()) {
            lblErrorObservacion.setVisible(true);
            lblErrorObservacion.setText("La observación no puede estar vacía.");
            datosValidos = false;
            System.out.println(">>> ERROR: La observación no puede estar vacía.");
            System.out.println("Observación ingresada: null");
        } else {
            lblErrorObservacion.setVisible(false);
            System.out.println("Observación ingresada: " + observacion);
        }

        // Validar motivos seleccionados (al menos uno)
        List<String> motivosSeleccionados = obtenerMotivosSeleccionados();
        if (motivosSeleccionados.isEmpty()) {
            lblErrorMotivo.setVisible(true);
            lblErrorMotivo.setText("Debe seleccionar al menos un motivo válido.");
            datosValidos = false;
            System.out.println(">>> ERROR: No se seleccionó ningún motivo");
        } else {
            lblErrorMotivo.setVisible(false);

            System.out.println("Motivos seleccionados (" + motivosSeleccionados.size() + "):");
            for (String motivo : motivosSeleccionados) {
                System.out.println("  - " + motivo);
            }
        }

        if (datosValidos) {
            System.out.println("Datos validados correctamente");
            mostrarResumen();
            btnConfirmar.setDisable(false);
        } else {
            System.out.println("Error: Los datos ingresados no son válidos");
            System.out.println(">>> PANTALLA: ================================");
            System.out.println(">>> PANTALLA: *** ERROR ***");
            System.out.println(">>> PANTALLA: ================================");
            System.out.println(">>> PANTALLA: Los datos ingresados no son válidos");
            System.out.println(">>> PANTALLA: ================================");

            resumenContainer.setVisible(false);
            btnConfirmar.setDisable(true);
        }
    }

    private List<String> obtenerMotivosSeleccionados() {
        List<String> motivos = new ArrayList<>();

        if (rbFallaHardware.isSelected()) motivos.add("Falla de hardware");
        if (rbFallaSoftware.isSelected()) motivos.add("Falla de software");
        if (rbMantenimientoPreventivo.isSelected()) motivos.add("Mantenimiento preventivo");
        if (rbCalibracionNecesaria.isSelected()) motivos.add("Calibración necesaria");
        if (rbDanoExterno.isSelected()) motivos.add("Daño por factores externos");
        if (rbInterferenciaElectromagnetica.isSelected()) motivos.add("Interferencia electromagnética");
        if (rbProblemasConectividad.isSelected()) motivos.add("Problemas de conectividad");

        return motivos;
    }

    private void mostrarResumen() {
        resumenDetalles.getChildren().clear();

        List<String> motivosSeleccionados = obtenerMotivosSeleccionados();
        String observacion = txtObservacion.getText().trim();

        System.out.println(">>> PANTALLA: ================================");
        System.out.println(">>> PANTALLA: CONFIRMAR CIERRE DE ORDEN");
        System.out.println(">>> PANTALLA: ================================");
        System.out.println(">>> PANTALLA: RESUMEN DE LA OPERACIÓN:");
        System.out.println(">>> PANTALLA: - Orden: #" + ordenSeleccionada.getNumero());
        System.out.println(">>> PANTALLA: - Estación: " + ordenSeleccionada.getEstacion());
        System.out.println(">>> PANTALLA: - Observación: " + observacion);
        System.out.println(">>> PANTALLA: - Motivos seleccionados (" + motivosSeleccionados.size() + "):");
        for (String motivo : motivosSeleccionados) {
            System.out.println(">>> PANTALLA:   * " + motivo);
        }
        System.out.println(">>> PANTALLA: ================================");
        System.out.println(">>> PANTALLA: ¿Confirma el cierre de la orden? (S/N)");
        System.out.println(">>> PANTALLA: ATENCIÓN: Esta acción marcará el sismógrafo como Fuera de Servicio");

        // Agregar labels al resumen
        resumenDetalles.getChildren().add(new Label("- Orden: #" + ordenSeleccionada.getNumero()));
        resumenDetalles.getChildren().add(new Label("- Estación: " + ordenSeleccionada.getEstacion()));
        resumenDetalles.getChildren().add(new Label("- Observación: " + observacion));
        resumenDetalles.getChildren().add(new Label("- Motivos seleccionados:"));
        for (String motivo : motivosSeleccionados) {
            Label lblMotivo = new Label("  * " + motivo);
            lblMotivo.setStyle("-fx-padding: 0 0 0 20;");
            resumenDetalles.getChildren().add(lblMotivo);
        }

        resumenContainer.setVisible(true);
    }

    @FXML
    private void handleConfirmar(ActionEvent event) {
        System.out.println("Usuario confirmó el cierre de la orden");

        List<String> motivosSeleccionados = obtenerMotivosSeleccionados();
        String observacion = txtObservacion.getText().trim();

        // Simular el proceso de cierre
        procesarCierreOrden(motivosSeleccionados, observacion);

        // Mostrar diálogo de éxito
        mostrarDialogoExito(motivosSeleccionados, observacion);
    }

    private void procesarCierreOrden(List<String> motivos, String observacion) {
        String numeroOrden = ordenSeleccionada.getNumero();
        String estacion = ordenSeleccionada.getEstacion();

        System.out.println("Orden #" + numeroOrden + " cerrada exitosamente");
        System.out.println("Motivos (" + motivos.size() + "):");
        for (String motivo : motivos) {
            System.out.println("  - " + motivo);
        }
        System.out.println("Observación: " + observacion);

        // Actualizar estado del sismógrafo
        System.out.println("Actualizando estado del sismógrafo a Fuera de Servicio");
        System.out.println("Sismógrafo ID: " + numeroOrden.substring(1) + " enviado a reparación. Estado: Fuera de Servicio");
        System.out.println("Sismógrafo de " + estacion + " enviado a reparación");
        System.out.println("Sismógrafo de la estación " + estacion + " marcado como Fuera de Servicio");

        // Enviar notificaciones
        enviarNotificaciones(numeroOrden, estacion, motivos, observacion);

        // Eliminar la orden de la lista
        OrdenListController.eliminarOrden(numeroOrden);
    }

    private void enviarNotificaciones(String numeroOrden, String estacion, List<String> motivos, String observacion) {
        System.out.println("Preparando notificaciones de cierre de orden");
        System.out.println("Se encontraron 3 destinatarios para notificación");
        System.out.println("Enviando notificación por correo a: supervisor@empresa.com, mantenimiento@empresa.com, control.calidad@empresa.com");
        System.out.println("Enviando correo a: supervisor@empresa.com, mantenimiento@empresa.com, control.calidad@empresa.com");
        System.out.println("Asunto: SISMÓGRAFO FUERA DE SERVICIO");
        System.out.println("Cuerpo del mensaje: SISMÓGRAFO FUERA DE SERVICIO");
        System.out.println("Estación: " + estacion);
        System.out.println("Motivos (" + motivos.size() + "):");
        for (String motivo : motivos) {
            System.out.println("  - " + motivo);
        }
        System.out.println("Observación: " + observacion);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println("Fecha de cierre: " + now.format(formatter));
        System.out.println("Orden #" + numeroOrden);
        System.out.println("Publicando en monitor para estación: " + estacion);
    }

    private void mostrarDialogoExito(List<String> motivos, String observacion) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SuccessDialog.fxml"));
            Parent root = loader.load();

            SuccessDialogController controller = loader.getController();
            controller.configurarMensajeMultiple(ordenSeleccionada, motivos, observacion, "");

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Operación Exitosa");
            dialogStage.setScene(new Scene(root));
            dialogStage.setResizable(false);
            dialogStage.showAndWait();

            // Después de cerrar el diálogo, volver al menú principal
            volverMenuPrincipal();

        } catch (IOException e) {
            e.printStackTrace();
            mostrarError("Error de Sistema", "No se pudo mostrar el diálogo de confirmación");
        }
    }

    @FXML
    private void handleCancelar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/OrdenList.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) btnCancelar.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Gestión de Órdenes - Lista de Órdenes");

        } catch (IOException e) {
            e.printStackTrace();
            mostrarError("Error de Sistema", "No se pudo cargar la lista de órdenes");
        }
    }

    private void volverMenuPrincipal() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PantallaOrden.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) btnCancelar.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Sistema de Gestión de Órdenes");

        } catch (IOException e) {
            e.printStackTrace();
            mostrarError("Error de Sistema", "No se pudo cargar el menú principal");
        }
    }


    private void mostrarError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
