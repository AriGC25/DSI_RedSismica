package org.example.controllers;


import javafx.stage.Window;
import org.example.controllers.OrdenListController.OrdenInspeccion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class SuccessDialogController {

    @FXML
    private Label lblMensajeExito;

    @FXML
    private VBox detallesContainer;

    @FXML
    private VBox detallesOperacion;

    @FXML
    private Button btnAceptar;

    @FXML
    private Button btnNuevaOperacion;

    private OrdenInspeccion ordenProcesada;

    @FXML
    private void initialize() {
        System.out.println(">>> PANTALLA: ================================");
        System.out.println(">>> PANTALLA: MENSAJE DEL SISTEMA");
        System.out.println(">>> PANTALLA: ================================");
    }

    public void configurarMensaje(OrdenInspeccion orden, String motivo, String observacion, String comentario) {
        this.ordenProcesada = orden;

        String mensaje = "La orden #" + orden.getNumero() + " ha sido cerrada exitosamente";
        lblMensajeExito.setText(mensaje);

        System.out.println(">>> PANTALLA: " + mensaje);
        System.out.println(">>> PANTALLA: ================================");

        // Agregar detalles de la operación
        detallesOperacion.getChildren().clear();
        detallesOperacion.getChildren().addAll(
                new Label("Orden: #" + orden.getNumero()),
                new Label("Estación: " + orden.getEstacion()),
                new Label("Motivo: " + motivo),
                new Label("Observación: " + observacion),
                new Label("Comentario: " + (comentario.isEmpty() ? "Sin comentarios" : comentario)),
                new Label("Estado del Sismógrafo: Fuera de Servicio"),
                new Label("Notificaciones: Enviadas por correo electrónico")
        );

        // Aplicar estilos a los labels
        detallesOperacion.getChildren().forEach(node -> {
            if (node instanceof Label) {
                ((Label) node).setStyle("-fx-font-size: 12px; -fx-text-fill: #2c3e50;");
            }
        });
    }

    public void configurarMensajeMultiple(OrdenInspeccion orden, List<String> motivos, String observacion, String comentario) {
        this.ordenProcesada = orden;

        String mensaje = "La orden #" + orden.getNumero() + " ha sido cerrada exitosamente";
        lblMensajeExito.setText(mensaje);

        System.out.println(">>> PANTALLA: " + mensaje);
        System.out.println(">>> PANTALLA: ================================");

        // Agregar detalles de la operación
        detallesOperacion.getChildren().clear();
        detallesOperacion.getChildren().add(new Label("Orden: #" + orden.getNumero()));
        detallesOperacion.getChildren().add(new Label("Estación: " + orden.getEstacion()));

        // Agregar motivos (uno o múltiples)
        if (motivos.size() == 1) {
            detallesOperacion.getChildren().add(new Label("Motivo: " + motivos.get(0)));
        } else {
            detallesOperacion.getChildren().add(new Label("Motivos (" + motivos.size() + "):"));
            for (String motivo : motivos) {
                Label lblMotivo = new Label("  • " + motivo);
                lblMotivo.setStyle("-fx-padding: 0 0 0 15;");
                detallesOperacion.getChildren().add(lblMotivo);
            }
        }

        detallesOperacion.getChildren().add(new Label("Observación: " + observacion));
        detallesOperacion.getChildren().add(new Label("Comentario: " + (comentario.isEmpty() ? "Sin comentarios" : comentario)));
        detallesOperacion.getChildren().add(new Label("Estado del Sismógrafo: Fuera de Servicio"));
        detallesOperacion.getChildren().add(new Label("Notificaciones: Enviadas por correo electrónico"));

        // Aplicar estilos a los labels
        detallesOperacion.getChildren().forEach(node -> {
            if (node instanceof Label) {
                Label label = (Label) node;
                if (!label.getStyle().contains("-fx-padding")) {
                    label.setStyle("-fx-font-size: 12px; -fx-text-fill: #2c3e50;");
                } else {
                    label.setStyle(label.getStyle() + "; -fx-font-size: 12px; -fx-text-fill: #2c3e50;");
                }
            }
        });
    }

    @FXML
    private void handleAceptar(ActionEvent event) {
        // Cerrar el diálogo
        Stage stage = (Stage) btnAceptar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleNuevaOperacion(ActionEvent event) {
        try {
            // Cargar la pantalla de lista de órdenes para una nueva operación
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/OrdenList.fxml"));
            Parent root = loader.load();

            // Obtener el stage de la ventana principal (no del diálogo)
            Stage dialogStage = (Stage) btnNuevaOperacion.getScene().getWindow();

            // Buscar la ventana principal
            Stage mainStage = null;
            for (Window window : Window.getWindows()) {
                if (window instanceof Stage && window != dialogStage && window.isShowing()) {
                    mainStage = (Stage) window;
                    break;
                }
            }


            if (mainStage != null) {
                Scene scene = new Scene(root);
                mainStage.setScene(scene);
                mainStage.setTitle("Gestión de Órdenes - Lista de Órdenes");
            }

            // Cerrar el diálogo
            dialogStage.close();

            System.out.println(">>> ¿Desea realizar otra operación? (S/N)");
            System.out.println("s");

        } catch (IOException e) {
            e.printStackTrace();
            // Si hay error, simplemente cerrar el diálogo
            Stage stage = (Stage) btnNuevaOperacion.getScene().getWindow();
            stage.close();
        }
    }
}
