package org.example.Interfaz;

import java.util.List;

public class InterfazEmail implements IObservador {

    @Override
    public void actualizar(String identificadorSismografo, String nuevoEstado, String fechaHora, List<String> motivos, String comentarios, List<String> emailsResponsables) {
        String asunto = "Actualización de Estado de Sismógrafo: " + identificadorSismografo;

        // Formatear los motivos
        StringBuilder motivosFormateados = new StringBuilder();
        if (motivos != null && !motivos.isEmpty()) {
            if (motivos.size() == 1) {
                motivosFormateados.append(motivos.get(0));
            } else {
                for (int i = 0; i < motivos.size(); i++) {
                    motivosFormateados.append("\n  - ").append(motivos.get(i));
                }
            }
        } else {
            motivosFormateados.append("No especificado");
        }

        String cuerpo = "El sismógrafo " + identificadorSismografo + " ha cambiado su estado a: "
                + nuevoEstado + ".\n"
                + "Fecha y Hora: " + fechaHora + "\n"
                + "Motivos: " + motivosFormateados + "\n"
                + "Comentarios: " + comentarios;

        // Enviar email a cada responsable
        if (emailsResponsables != null && !emailsResponsables.isEmpty()) {
            for (String email : emailsResponsables) {
                this.sendEmail(email, asunto, cuerpo);
            }
        } else {
            // Si no hay emails, enviar al email por defecto
            this.sendEmail("responsable.reparaciones@ccrs.com", asunto, cuerpo);
        }
    }

    public void sendEmail(String to, String subject, String body) {
        //Enviamos el email
        System.out.println("--- SIMULACIÓN DE ENVÍO DE EMAIL ---");
        System.out.println("Para: " + to);
        System.out.println("Asunto: " + subject);
        System.out.println("Cuerpo: " + body);
        System.out.println("------------------------------------");
    }
}
