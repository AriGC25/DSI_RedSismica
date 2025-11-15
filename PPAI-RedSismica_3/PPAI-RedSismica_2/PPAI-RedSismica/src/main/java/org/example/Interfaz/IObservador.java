package org.example.Interfaz;

import java.util.List;

public interface IObservador {
    void actualizar(String identificadorSismografo, String nuevoEstado, String fechaHora, List<String> motivos, String comentarios, List<String> emailsResponsables);
}
