package org.example.models;

import java.util.List;

public enum Estado {
    OPERATIVO("Operativo", "sismografo"),
    REALIZADA("Realizada", "sismografo"),
    FUERA_DE_SERVICIO("Fuera de servicio", "sismografo"),
    CERRADO("Cerrado", "sismografo"),
    CERRADA("Cerrada", "orden");

    private String nombre;
    private String ambito;

    Estado(String nombre, String ambito) {
        this.nombre = nombre;
        this.ambito = ambito;
    }

    public static List<Estado> obtenerEstados() {
        return List.of(values());
    }

    //Metodos GET y SET
    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAmbito() {
        return this.ambito;
    }

    public void setAmbito(String ambito) {
        this.ambito = ambito;
    }

    //Otros métodos de Estado
    public String esFinalizada(){ return this.nombre;}

    public boolean esAmbitoSismografo() {
        return "sismografo".equalsIgnoreCase(this.ambito);
    }

    public boolean esFueraDeServicio() {
        return "Fuera de servicio".equalsIgnoreCase(this.nombre);
    }

    public boolean esAmbitoOrden() {
        return "orden".equalsIgnoreCase(this.ambito);
    }

    public boolean esCerrada() {
        return "Cerrada".equalsIgnoreCase(this.nombre);
    }
}

