package org.example.util;

import org.example.models.*;
import org.hibernate.Session;

import java.util.List;

/**
 * Clase para cargar datos desde la base de datos
 */
public class DataLoader {

    /**
     * Carga todos los roles desde la base de datos
     */
    public static List<Rol> cargarRoles() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("FROM Rol", Rol.class).list();
        } finally {
            session.close();
        }
    }

    /**
     * Carga todos los empleados desde la base de datos
     */
    public static List<Empleado> cargarEmpleados() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("FROM Empleado", Empleado.class).list();
        } finally {
            session.close();
        }
    }

    /**
     * Carga todos los usuarios desde la base de datos
     */
    public static List<Usuario> cargarUsuarios() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("FROM Usuario", Usuario.class).list();
        } finally {
            session.close();
        }
    }

    /**
     * Carga el usuario admin (usuario por defecto)
     */
    public static Usuario cargarUsuarioAdmin() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("FROM Usuario WHERE usuario = :username", Usuario.class)
                    .setParameter("username", "admin")
                    .uniqueResult();
        } finally {
            session.close();
        }
    }

    /**
     * Carga todas las sesiones desde la base de datos
     */
    public static List<Sesion> cargarSesiones() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("FROM Sesion", Sesion.class).list();
        } finally {
            session.close();
        }
    }

    /**
     * Carga la sesión más reciente
     */
    public static Sesion cargarSesionActual() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            List<Sesion> sesiones = session.createQuery("FROM Sesion ORDER BY id DESC", Sesion.class)
                    .setMaxResults(1)
                    .list();
            return sesiones.isEmpty() ? null : sesiones.get(0);
        } finally {
            session.close();
        }
    }

    /**
     * Carga todas las estaciones sismológicas desde la base de datos
     */
    public static List<EstacionSismologica> cargarEstaciones() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("FROM EstacionSismologica", EstacionSismologica.class).list();
        } finally {
            session.close();
        }
    }

    /**
     * Carga todos los sismógrafos desde la base de datos
     */
    public static List<Sismografo> cargarSismografos() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("FROM Sismografo", Sismografo.class).list();
        } finally {
            session.close();
        }
    }

    /**
     * Carga todas las órdenes de inspección desde la base de datos
     */
    public static List<OrdenInspeccion> cargarOrdenes() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("FROM OrdenInspeccion  o WHERE o.estado != 'CERRADA'", OrdenInspeccion.class).list();
        } finally {
            session.close();
        }
    }

    /**
     * Carga todos los estados disponibles
     */
    public static List<Estado> cargarEstados() {
        return Estado.obtenerEstados();
    }
}

