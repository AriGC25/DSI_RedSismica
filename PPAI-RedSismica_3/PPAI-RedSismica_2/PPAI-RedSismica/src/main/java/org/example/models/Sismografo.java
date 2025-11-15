package org.example.models;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "sismografos")
public class Sismografo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "identificador_sismografo", nullable = false, unique = true)
    private int identificadorSismografo;

    @Column(name = "fecha_adquisicion", length = 50)
    private String fechaAdquisicion;

    @Column(name = "nro_serie")
    private int nroSerie;

    @OneToOne(mappedBy = "sismografo")
    private EstacionSismologica estacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_actual")
    private Estado estadoActual;

    @OneToMany(mappedBy = "sismografo", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CambioEstado> cambiosEstado;

    public Sismografo() {
        // Constructor vacío requerido por JPA
        this.cambiosEstado = new ArrayList<>();
        this.estadoActual = Estado.OPERATIVO;
    }

    public Sismografo(int identificadorSismografo, String fechaAdquisicion, int nroSerie) {
        this.identificadorSismografo = identificadorSismografo;
        this.nroSerie = nroSerie;
        this.fechaAdquisicion = fechaAdquisicion;
        this.cambiosEstado = new ArrayList<>();
        this.estadoActual = Estado.OPERATIVO;
        CambioEstado cambioInicial = new CambioEstado(null, null, null, null, null, null, List.of());
        cambioInicial.setEstado(this.estadoActual);
        cambioInicial.setSismografo(this);
        this.cambiosEstado.add(cambioInicial);
    }

    // Métodos GET y SET
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getIdentificadorSismografo() {
        return this.identificadorSismografo;
    }

    public void setIdentificadorSismografo(int identificadorSismografo) {
        this.identificadorSismografo = identificadorSismografo;
    }

    public int getNroSerie() {
        return this.nroSerie;
    }

    public void setNroSerie(int nroSerie) {
        this.nroSerie = nroSerie;
    }

    public String getFechaAdquisicion() {
        return this.fechaAdquisicion;
    }

    public void setFechaAdquisicion(String fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }

    public Estado getEstadoActual() {
        return this.estadoActual;
    }

    public List<CambioEstado> getCambiosEstado() {
        return cambiosEstado;
    }

    public void setCambiosEstado(List<CambioEstado> cambiosEstado) {
        this.cambiosEstado = cambiosEstado;
    }


    //Otros métodos de Sismógrafo
    public void enviarAReparar(Empleado responsableInspeccion, List<MotivoTipo> motivoSeleccionado, List<String> comentarioIngresado, String horaActual, String fechaActual) {
        //1. Es estado actual
        for (CambioEstado cambio : this.cambiosEstado) {
            if (cambio.esActual()) {
                cambio.setFechaHoraFin(fechaActual, horaActual);
            }
        }

        crearCE(responsableInspeccion, motivoSeleccionado, comentarioIngresado, horaActual, fechaActual);
    }

    public void crearCE(Empleado responsableInspeccion, List<MotivoTipo> motivosSeleccionado, List<String> comentarioIngresado, String horaActual, String fechaActual) {
        //1. Creamos nuevo cambio de estado
        Estado estadoFS = Estado.FUERA_DE_SERVICIO;
        CambioEstado nuevoCambio = new CambioEstado(fechaActual, horaActual, (String)null, (String)null, estadoFS, responsableInspeccion, null);

        //2. Actualizamos el estado actual
        this.estadoActual = estadoFS;

        //3. Agregamos al historial
        nuevoCambio.setEstado(estadoFS);
        this.cambiosEstado.add(nuevoCambio);

        List<MotivoFueraServicio> comMotivos;
        if (comentarioIngresado == null) {
            comMotivos = new ArrayList<>();
        } else {
            comMotivos = comentarioIngresado.stream()
                    .map(texto -> new MotivoFueraServicio(texto))
                    .collect(Collectors.toList());
        }

        nuevoCambio.crearMotivoFS(comMotivos);
        System.out.println("Motivos fueraseleeccionados: " + motivosSeleccionado);
    }
}
