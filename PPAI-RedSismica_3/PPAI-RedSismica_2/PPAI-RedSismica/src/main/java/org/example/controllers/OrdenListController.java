package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrdenListController {

    @FXML
    private VBox ordenesContainer;

    @FXML
    private TextField txtNumeroOrden;

    @FXML
    private Button btnSeleccionarOrden;

    @FXML
    private Button btnVolver;

    private List<OrdenInspeccion> ordenes;

    @FXML
    private void initialize() {
        cargarOrdenes();
        mostrarOrdenes();

        // Configurar validación del campo de texto
        txtNumeroOrden.textProperty().addListener((observable, oldValue, newValue) -> {
            // Solo permitir números
            if (!newValue.matches("\\d*")) {
                txtNumeroOrden.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        System.out.println(">>> PANTALLA: Botones para cerrar orden habilitados");
        System.out.println(">>> PANTALLA: Sistema listo para recibir número de orden");
        System.out.println("Empleado logueado: Juan Pérez");
    }

    private void cargarOrdenes() {
        ordenes = new ArrayList<>();

        // Datos precargados según el flujo
        ordenes.add(new OrdenInspeccion("1001", "2025-05-15", "Mantenimiento preventivo",
                "Estación Central", "Finalizada", "Juan Pérez"));
        ordenes.add(new OrdenInspeccion("1002", "2025-05-16", "Inspección rutinaria",
                "Estación Norte", "Finalizada", "Juan Pérez"));
        ordenes.add(new OrdenInspeccion("1003", "2025-05-17", "Verificación de sensores",
                "Estación Sur", "Finalizada", "Juan Pérez"));

        // Marcar órdenes como finalizadas
        System.out.println("Orden #1001 marcada como finalizada");
        System.out.println("Orden #1002 marcada como finalizada");
        System.out.println("Orden #1003 marcada como finalizada");
    }

    private void mostrarOrdenes() {
        ordenesContainer.getChildren().clear();

        for (OrdenInspeccion orden : ordenes) {
            VBox ordenCard = crearTarjetaOrden(orden);
            ordenesContainer.getChildren().add(ordenCard);

            // Mostrar información en consola según el flujo
            System.out.println(">>> PANTALLA: ================================");
            System.out.println(">>> PANTALLA: DATOS DE LA ORDEN ENCONTRADAS");
            System.out.println(">>> PANTALLA: ================================");
            System.out.println(">>> PANTALLA: Número de Orden: #" + orden.getNumero());
            System.out.println(">>> PANTALLA: Fecha de Generación: " + orden.getFechaGeneracion());
            System.out.println(">>> PANTALLA: Tipo de Motivo: " + orden.getTipoMotivo());
            System.out.println(">>> PANTALLA: Estación: " + orden.getEstacion());
            System.out.println(">>> PANTALLA: Estado Actual: " + orden.getEstado());
            System.out.println(">>> PANTALLA: Responsable: " + orden.getResponsable());
            System.out.println(">>> PANTALLA: ================================");
        }
    }

    private VBox crearTarjetaOrden(OrdenInspeccion orden) {
        VBox card = new VBox(10);
        card.setStyle("-fx-background-color: white; -fx-border-color: #bdc3c7; " +
                "-fx-border-radius: 5; -fx-background-radius: 5; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 2);");
        card.setPadding(new Insets(15));

        // Título de la orden
        Label titulo = new Label("Orden #" + orden.getNumero());
        titulo.setFont(Font.font("System", FontWeight.BOLD, 16));
        titulo.setStyle("-fx-text-fill: #2c3e50;");

        // Grid con información
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(5);

        grid.add(new Label("Fecha:"), 0, 0);
        grid.add(new Label(orden.getFechaGeneracion()), 1, 0);
        grid.add(new Label("Motivo:"), 0, 1);
        grid.add(new Label(orden.getTipoMotivo()), 1, 1);
        grid.add(new Label("Estación:"), 0, 2);
        grid.add(new Label(orden.getEstacion()), 1, 2);
        grid.add(new Label("Estado:"), 0, 3);

        Label estadoLabel = new Label(orden.getEstado());
        estadoLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
        grid.add(estadoLabel, 1, 3);

        grid.add(new Label("Responsable:"), 0, 4);
        grid.add(new Label(orden.getResponsable()), 1, 4);

        // Botón para seleccionar
        Button btnSeleccionar = new Button("Seleccionar esta orden");
        btnSeleccionar.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;");
        btnSeleccionar.setOnAction(e -> seleccionarOrden(orden.getNumero()));

        card.getChildren().addAll(titulo, new Separator(), grid, btnSeleccionar);

        return card;
    }

    @FXML
    private void handleSeleccionarOrden(ActionEvent event) {
        String numeroOrden = txtNumeroOrden.getText().trim();

        if (numeroOrden.isEmpty()) {
            mostrarError("Error de Validación", "Debe ingresar un número de orden");
            return;
        }

        // Validar que sea solo números
        try {
            Integer.parseInt(numeroOrden);
        } catch (NumberFormatException e) {
            System.out.println(">>> ERROR: Entrada no válida. Debe ingresar un número.");
            mostrarError("Error de Validación", "Entrada no válida. Debe ingresar un número.");
            return;
        }

        seleccionarOrden(numeroOrden);
    }

    private void seleccionarOrden(String numeroOrden) {
        // Buscar la orden
        OrdenInspeccion ordenSeleccionada = null;
        for (OrdenInspeccion orden : ordenes) {
            if (orden.getNumero().equals(numeroOrden)) {
                ordenSeleccionada = orden;
                break;
            }
        }

        if (ordenSeleccionada == null) {
            mostrarError("Orden no encontrada", "No se encontró la orden #" + numeroOrden);
            return;
        }

        System.out.println("Orden #" + numeroOrden + " seleccionada correctamente");
        System.out.println("Estación: " + ordenSeleccionada.getEstacion());
        System.out.println("Fecha de generación: " + ordenSeleccionada.getFechaGeneracion());

        // Navegar a la pantalla de cerrar orden
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CerrarOrden.fxml"));
            Parent root = loader.load();

            // Pasar los datos a la siguiente pantalla
            CerrarOrdenController controller = loader.getController();
            controller.setOrdenSeleccionada(ordenSeleccionada);

            Stage stage = (Stage) btnSeleccionarOrden.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Gestión de Órdenes - Cerrar Orden #" + numeroOrden);

        } catch (IOException e) {
            e.printStackTrace();
            mostrarError("Error de Sistema", "No se pudo cargar la pantalla de cierre de orden");
        }
    }

    @FXML
    private void handleVolver(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PantallaOrden.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) btnVolver.getScene().getWindow();
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

    // Clase interna para representar una orden de inspección
    public static class OrdenInspeccion {
        private String numero;
        private String fechaGeneracion;
        private String tipoMotivo;
        private String estacion;
        private String estado;
        private String responsable;

        public OrdenInspeccion(String numero, String fechaGeneracion, String tipoMotivo,
                               String estacion, String estado, String responsable) {
            this.numero = numero;
            this.fechaGeneracion = fechaGeneracion;
            this.tipoMotivo = tipoMotivo;
            this.estacion = estacion;
            this.estado = estado;
            this.responsable = responsable;
        }

        // Getters
        public String getNumero() { return numero; }
        public String getFechaGeneracion() { return fechaGeneracion; }
        public String getTipoMotivo() { return tipoMotivo; }
        public String getEstacion() { return estacion; }
        public String getEstado() { return estado; }
        public String getResponsable() { return responsable; }
    }
}