package org.example.models;

import java.util.Arrays;
import java.util.List;

public enum MotivoTipo {
    FALLA_HARDWARE("Falla de hardware"),
    FALLA_SOFTWARE("Falla de software"),
    MANTENIMIENTO_PREVENTIVO("Mantenimiento preventivo"),
    CALIBRACION_NECESARIA("Calibración necesaria"),
    DANIO_FACTORES_EXTERNOS("Daño por factores externos");

    private String descripcion;

    MotivoTipo(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return this.descripcion;
    }

    //Metodo GET y SET
    public String getDescripcion() {
        this.descripcion = descripcion;
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    //Otros métodos de MotivoTipo
    public static List<MotivoTipo> obtenerMotivosFS() {
        return List.of(values());
    }
}
