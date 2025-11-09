package org.example.models;

import java.util.ArrayList;
import java.util.List;

public class Rol {
    private String nombre;
    private String descripcion;

    public Rol(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    //Métodos GET y SET
    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    //Otros métodos de Rol
    public boolean esResponsable() {
        return "Responsable de Inspecciones".equals(this.nombre) || this.descripcion.contains("cerrar_orden_inspeccion");
    }
}
