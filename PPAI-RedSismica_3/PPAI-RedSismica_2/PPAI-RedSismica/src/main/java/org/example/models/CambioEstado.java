package org.example.models;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cambios_estado")
public class CambioEstado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hora_fin", length = 50)
    private String horaFin;

    @Column(name = "fecha_fin", length = 50)
    private String fechaFin;

    @Column(name = "hora_inicio", nullable = false, length = 50)
    private String horaInicio;

    @Column(name = "fecha_inicio", nullable = false, length = 50)
    private String fechaInicio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Estado estado;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "cambio_estado_id") // FK en la tabla MotivoFueraServicio que referencia a CambioEstado
    private List<MotivoFueraServicio> motivoFueraServicio = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "empleado_id", nullable = false)
    private Empleado empleadoResponsable;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sismografo_id")
    private Sismografo sismografo;

    public CambioEstado() {
        // Constructor vacío requerido por JPA
    }

    public CambioEstado(String horaFin, String fechaFin, String horaInicio, String fechaInicio, Estado estado, Empleado empleadoResponsable, List<MotivoFueraServicio> motivoFueraServicio) {
        this.horaFin = horaFin;
        this.fechaFin = fechaFin;
        this.horaInicio = horaInicio;
        this.fechaInicio = fechaInicio;
        this.estado = estado;
        this.empleadoResponsable = empleadoResponsable;
        this.motivoFueraServicio = motivoFueraServicio != null ? motivoFueraServicio : new ArrayList<>();
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getHoraFin() { return this.horaFin; }
    public void setHoraFin(String horaFin) { this.horaFin = horaFin; }

    public String getFechaFin() { return this.fechaFin; }
    public void setFechaFin(String fechaFin) { this.fechaFin = fechaFin; }

    public String getHoraInicio() { return this.horaInicio; }
    public void setHoraInicio(String horaInicio) { this.horaInicio = horaInicio; }

    public String getFechaInicio() { return this.fechaInicio; }
    public void setFechaInicio(String fechaInicio) { this.fechaInicio = fechaInicio; }

    public Estado getEstado() { return this.estado; }
    public void setEstado(Estado estado) { this.estado = estado; }

    public List<MotivoFueraServicio> getMotivoFueraServicio() {
        return motivoFueraServicio;
    }

    public void setMotivoFueraServicio(List<MotivoFueraServicio> motivoFueraServicio) {
        this.motivoFueraServicio = motivoFueraServicio != null ? motivoFueraServicio : new ArrayList<>();
    }

    public Empleado getEmpleadoResponsable() { return empleadoResponsable; }
    public void setEmpleadoResponsable(Empleado empleadoResponsable) { this.empleadoResponsable = empleadoResponsable; }

    public Sismografo getSismografo() { return sismografo; }
    public void setSismografo(Sismografo sismografo) { this.sismografo = sismografo; }

    // Otros métodos
    public void setFechaHoraFin(String fecha, String hora) {
        this.fechaFin = fecha;
        this.horaFin = hora;
    }

    public void crearMotivoFS(List<MotivoFueraServicio> comentarioIngresado) {
        this.motivoFueraServicio = comentarioIngresado != null ? comentarioIngresado : new ArrayList<>();
    }

    public boolean esActual() {
        return this.horaFin == null || this.fechaFin == null;
    }
}
