package org.example.models;

public class Usuario {
    private String usuario;
    private String password;
    private Empleado empleado;

    public Usuario(String usuario, String password, Empleado empleado) {
        this.usuario = usuario;
        this.password = password;
        this.empleado = empleado;
    }

    //Métodos GET y SET
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Empleado obtenerEmpleado() {
        return this.empleado;
    }
}

