package org.example.models;

import jakarta.persistence.*;

@Entity
@Table(name = "motivos_fuera_servicio")
public class MotivoFueraServicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comentario", length = 500)
    private String comentario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MotivoTipo motivo;

    // Constructores requeridos por JPA
    protected MotivoFueraServicio() { }

    public MotivoFueraServicio(String texto) {
    }

    public MotivoFueraServicio(String comentario, MotivoTipo motivo) {
        this.comentario = comentario;
        this.motivo = motivo;
    }

    //Métodos GET y SET
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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