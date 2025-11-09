package org.example.models;

import java.util.ArrayList;
import java.util.List;

public class Empleado {
    private String nombre;
    private String apellido;
    private int telefono;
    private String email;
    private List<Rol> rol;

    public Empleado(String nombre, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
        this.rol = new ArrayList();
    }

    //Métodos GET y SET
    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return this.apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getTelefono() {
        return this.telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String obtenerEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Rol> getRoles() {
        return this.rol;
    }

    public void setRol(Rol rol) {
        if (!this.rol.contains(rol)) {
            this.rol.add(rol);
        }
    }

    //Otros métodos de Empleado
    public boolean esResponsable() {
        for (Rol rol : this.rol) {
            if (rol.esResponsable()) {
                return true;
            }
        }
        return false;
    }
}
