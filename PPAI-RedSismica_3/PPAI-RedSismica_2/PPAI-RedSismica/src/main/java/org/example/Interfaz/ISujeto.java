package org.example.Interfaz;

public interface ISujeto {
    void suscribir(IObservador observador);
    void quitar(IObservador observador);
    void notificar();
}