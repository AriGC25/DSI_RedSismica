package org.example;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.example.Interfaz.PantallaOrden;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Cargar el archivo FXML del menú principal
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/PantallaOrden.fxml"));
        Parent root = loader.load();

        // Configurar la escena
        Scene scene = new Scene(root, 800, 600);

        // Configurar el stage
        primaryStage.setTitle("Sistema de Gestión de Órdenes de Inspección Sismográfica");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.setResizable(true);

        // Mostrar la ventana
        primaryStage.show();

        // Mensaje de inicio en consola
        System.out.println("=== SISTEMA INICIADO CORRECTAMENTE ===");
        System.out.println("Interfaz gráfica cargada exitosamente");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
