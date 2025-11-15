package org.example.Interfaz;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import java.util.List;

public class InterfazPantalla implements IObservador {
    private String mensaje;

    @Override
    public void actualizar(String identificadorSismografo, String nuevoEstado, String fechaHora, List<String> motivos, String comentarios, List<String> emailsResponsables) {
        // InterfazPantalla ignora el parámetro emailsResponsables
        publicarMonitor(identificadorSismografo, nuevoEstado, fechaHora, motivos, comentarios);
    }

    public void publicarMonitor(String identificadorSismografo, String nuevoEstado, String fechaHora, List<String> motivos, String comentarios){
        StringBuilder motivosFormateados = new StringBuilder();
        if (motivos != null && !motivos.isEmpty()) {
            if (motivos.size() == 1) {
                motivosFormateados.append(motivos.get(0));
            } else {
                motivosFormateados.append("(").append(motivos.size()).append(" motivos):\n");
                for (String motivo : motivos) {
                    motivosFormateados.append("  • ").append(motivo).append("\n");
                }
            }
        } else {
            motivosFormateados.append("No especificado");
        }
        setMensaje(identificadorSismografo, nuevoEstado, fechaHora, motivosFormateados, comentarios);

    }

    public void setMensaje(String identificadorSismografo, String nuevoEstado, String fechaHora, StringBuilder motivosFormateados, String comentarios){
        String mensaje = "Sismógrafo: " + identificadorSismografo + "\n"
                + "Nuevo Estado: " + nuevoEstado + "\n"
                + "Fecha y Hora: " + fechaHora + "\n"
                + "Motivos: " + motivosFormateados + "\n"
                + "Comentarios: " + comentarios;

            // Usar Platform.runLater para asegurar que se ejecuta en el hilo de JavaFX
            Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Actualización de Sismógrafo");
            alert.setHeaderText("¡Notificación!");

            // Usar TextArea para el contenido para mejor visualización
            TextArea textArea = new TextArea(mensaje);
            textArea.setEditable(false);
            textArea.setWrapText(true);
            textArea.setPrefRowCount(10);

            alert.getDialogPane().setContent(textArea);
            alert.showAndWait();
        });
    }
}
