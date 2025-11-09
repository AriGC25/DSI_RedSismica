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

    @FXML private RadioButton rbFallaHardware;
    @FXML private RadioButton rbFallaSoftware;
    @FXML private RadioButton rbMantenimientoPreventivo;
    @FXML private RadioButton rbCalibracionNecesaria;
    @FXML private RadioButton rbDanoExterno;
    @FXML private RadioButton rbInterferenciaElectromagnetica;
    @FXML private RadioButton rbProblemasConectividad;

    @FXML private TextArea txtComentario;
    @FXML private Label lblErrorMotivo;

    @FXML private VBox resumenContainer;
    @FXML private VBox resumenDetalles;

    @FXML private Button btnValidar;
    @FXML private Button btnConfirmar;
    @FXML private Button btnCancelar;

    private OrdenInspeccion ordenSeleccionada;
    private ToggleGroup motivoGroup;

    @FXML
    private void initialize() {
        // Configurar grupo de radio buttons
        motivoGroup = new ToggleGroup();
        rbFallaHardware.setToggleGroup(motivoGroup);
        rbFallaSoftware.setToggleGroup(motivoGroup);
        rbMantenimientoPreventivo.setToggleGroup(motivoGroup);
        rbCalibracionNecesaria.setToggleGroup(motivoGroup);
        rbDanoExterno.setToggleGroup(motivoGroup);
        rbInterferenciaElectromagnetica.setToggleGroup(motivoGroup);
        rbProblemasConectividad.setToggleGroup(motivoGroup);

        // Ocultar mensajes de error inicialmente
        lblErrorObservacion.setVisible(false);
        lblErrorMotivo.setVisible(false);
        resumenContainer.setVisible(false);
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

        // Validar motivo seleccionado
        RadioButton motivoSeleccionado = (RadioButton) motivoGroup.getSelectedToggle();
        if (motivoSeleccionado == null) {
            lblErrorMotivo.setVisible(true);
            lblErrorMotivo.setText("Debe seleccionar un motivo válido.");
            datosValidos = false;
            System.out.println(">>> ERROR: El motivo seleccionado no es válido");
        } else {
            lblErrorMotivo.setVisible(false);
            String tipoMotivo = obtenerTextoMotivo(motivoSeleccionado);
            String comentario = txtComentario.getText().trim();

            System.out.println("Motivo seleccionado: " + tipoMotivo);
            System.out.println("Comentario ingresado: " + (comentario.isEmpty() ? "" : comentario));
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

    private void mostrarResumen() {
        resumenDetalles.getChildren().clear();

        RadioButton motivoSeleccionado = (RadioButton) motivoGroup.getSelectedToggle();
        String tipoMotivo = obtenerTextoMotivo(motivoSeleccionado);
        String observacion = txtObservacion.getText().trim();
        String comentario = txtComentario.getText().trim();

        System.out.println(">>> PANTALLA: ================================");
        System.out.println(">>> PANTALLA: CONFIRMAR CIERRE DE ORDEN");
        System.out.println(">>> PANTALLA: ================================");
        System.out.println(">>> PANTALLA: RESUMEN DE LA OPERACIÓN:");
        System.out.println(">>> PANTALLA: - Orden: #" + ordenSeleccionada.getNumero());
        System.out.println(">>> PANTALLA: - Estación: " + ordenSeleccionada.getEstacion());
        System.out.println(">>> PANTALLA: - Observación: " + observacion);
        System.out.println(">>> PANTALLA: - Motivo: " + tipoMotivo);
        System.out.println(">>> PANTALLA: - Comentario: " + comentario);
        System.out.println(">>> PANTALLA: ================================");
        System.out.println(">>> PANTALLA: ¿Confirma el cierre de la orden? (S/N)");
        System.out.println(">>> PANTALLA: ATENCIÓN: Esta acción marcará el sismógrafo como Fuera de Servicio");

        // Agregar labels al resumen
        resumenDetalles.getChildren().addAll(
                new Label("- Orden: #" + ordenSeleccionada.getNumero()),
                new Label("- Estación: " + ordenSeleccionada.getEstacion()),
                new Label("- Observación: " + observacion),
                new Label("- Motivo: " + tipoMotivo),
                new Label("- Comentario: " + (comentario.isEmpty() ? "Sin comentarios" : comentario))
        );

        resumenContainer.setVisible(true);
    }

    @FXML
    private void handleConfirmar(ActionEvent event) {
        System.out.println("Usuario confirmó el cierre de la orden");

        RadioButton motivoSeleccionado = (RadioButton) motivoGroup.getSelectedToggle();
        String tipoMotivo = obtenerTextoMotivo(motivoSeleccionado);
        String observacion = txtObservacion.getText().trim();
        String comentario = txtComentario.getText().trim();

        // Simular el proceso de cierre
        procesarCierreOrden(tipoMotivo, observacion, comentario);

        // Mostrar diálogo de éxito
        mostrarDialogoExito(tipoMotivo, observacion, comentario);
    }

    private void procesarCierreOrden(String motivo, String observacion, String comentario) {
        String numeroOrden = ordenSeleccionada.getNumero();
        String estacion = ordenSeleccionada.getEstacion();

        System.out.println("Orden #" + numeroOrden + " cerrada exitosamente");
        System.out.println("Motivo: " + motivo);
        System.out.println("Observación: " + observacion);
        System.out.println("Comentario: " + comentario);
        System.out.println("Orden #" + numeroOrden + " cerrada exitosamente");

        // Actualizar estado del sismógrafo
        System.out.println("Actualizando estado del sismógrafo a Fuera de Servicio");
        System.out.println("Sismógrafo ID: " + numeroOrden.substring(1) + " enviado a reparación. Estado: Fuera de Servicio");
        System.out.println("Sismógrafo de " + estacion + " enviado a reparación");
        System.out.println("Sismógrafo de la estación " + estacion + " marcado como Fuera de Servicio");

        // Enviar notificaciones
        enviarNotificaciones(numeroOrden, estacion, motivo, observacion, comentario);
    }

    private void enviarNotificaciones(String numeroOrden, String estacion, String motivo, String observacion, String comentario) {
        System.out.println("Preparando notificaciones de cierre de orden");
        System.out.println("Se encontraron 3 destinatarios para notificación");
        System.out.println("Enviando notificación por correo a: supervisor@empresa.com, mantenimiento@empresa.com, control.calidad@empresa.com");
        System.out.println("Enviando correo a: supervisor@empresa.com, mantenimiento@empresa.com, control.calidad@empresa.com");
        System.out.println("Asunto: SISMÓGRAFO FUERA DE SERVICIO");
        System.out.println("Cuerpo del mensaje: SISMÓGRAFO FUERA DE SERVICIO");
        System.out.println("Estación: " + estacion);
        System.out.println("Motivo: " + motivo);
        System.out.println("Observación: " + observacion);
        System.out.println("Comentarios: " + comentario);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println("Fecha de cierre: " + now.format(formatter));
        System.out.println("Orden #" + numeroOrden);
        System.out.println("Publicando en monitor para estación: " + estacion);
    }

    private void mostrarDialogoExito(String motivo, String observacion, String comentario) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SuccessDialog.fxml"));
            Parent root = loader.load();

            SuccessDialogController controller = loader.getController();
            controller.configurarMensaje(ordenSeleccionada, motivo, observacion, comentario);

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

    private String obtenerTextoMotivo(RadioButton radioButton) {
        if (radioButton == rbFallaHardware) return "Falla de hardware";
        if (radioButton == rbFallaSoftware) return "Falla de software";
        if (radioButton == rbMantenimientoPreventivo) return "Mantenimiento preventivo";
        if (radioButton == rbCalibracionNecesaria) return "Calibración necesaria";
        if (radioButton == rbDanoExterno) return "Daño por factores externos";
        if (radioButton == rbInterferenciaElectromagnetica) return "Interferencia electromagnética";
        if (radioButton == rbProblemasConectividad) return "Problemas de conectividad";
        return "";
    }

    private void mostrarError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
