package org.example.models;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "empleados")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 100)
    private String apellido;

    private int telefono;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
        name = "empleado_rol",
        joinColumns = @JoinColumn(name = "empleado_id"),
        inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private List<Rol> rol;

    public Empleado() {
        // Constructor vacío requerido por JPA
        this.rol = new ArrayList<>();
    }

    public Empleado(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
        this.rol = new ArrayList<>();
    }

    // Métodos GET y SET
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
