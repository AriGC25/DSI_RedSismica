package org.example.Interfaz;
import javafx.scene.control.Alert;

public class InterfazPantalla {
    private String notificacion;

    public InterfazPantalla(String notificacion) {
        this.notificacion = notificacion;
    }

    //Métodos GET y SET
    public String getNotificacion() {
        return this.notificacion;
    }

    public void setNotificacion(String notificacion) {
        this.notificacion = notificacion;
    }

    //Otros métodos de Interfaz Pantalla
    public void publicar(String nombreEstacion) {
        System.out.println("Publicando en Monitor: " + nombreEstacion);
        Alert alertaMonitor = new Alert(Alert.AlertType.INFORMATION);
        alertaMonitor.setTitle("Publicación en Monitor");
        alertaMonitor.setHeaderText("Publicación realizada");
        alertaMonitor.setContentText("Mensaje publicado en " + nombreEstacion);
        alertaMonitor.showAndWait();
    }
}
