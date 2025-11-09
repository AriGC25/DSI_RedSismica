package org.example.util;

public record OrdenInspeccionVisualDTO(
        int numeroDeOrden,
        String EstadoActual,
        String fechaGeneracion,
        int identificador
) {

    public int getNumeroDeOrden() {
        return numeroDeOrden;
    }

    public String getEstadoActual() {
        return EstadoActual;
    }

    public String getFechaGeneracion() {
        return fechaGeneracion;
    }
    public int getIdentificador() {
        return identificador;
    }
}
