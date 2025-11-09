package org.example.models;

public class MotivoFueraServicio {
    private String comentario;
    private MotivoTipo motivo;

    public MotivoFueraServicio(String comentario, MotivoTipo motivo) {
        this.comentario = comentario;
        this.motivo = motivo;
    }

    //Métodos GET y SET
    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public MotivoTipo getMotivo() {
        return motivo;
    }

    public void setMotivo(MotivoTipo motivo) {
        this.motivo = motivo;
    }
}