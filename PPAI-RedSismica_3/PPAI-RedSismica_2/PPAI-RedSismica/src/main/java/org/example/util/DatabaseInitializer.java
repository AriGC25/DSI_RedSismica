package org.example.util;

import org.example.models.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase para inicializar la base de datos con datos de prueba
 */
public class DatabaseInitializer {

    public static void initializeData() {
        System.out.println(">>> DEBUG DatabaseInitializer: Iniciando initializeData()");
        Session session = null;
        Transaction transaction = null;

        try {
            System.out.println(">>> DEBUG DatabaseInitializer: Obteniendo SessionFactory...");
            session = HibernateUtil.getSessionFactory().openSession();
            System.out.println(">>> DEBUG DatabaseInitializer: Session abierta correctamente");

            transaction = session.beginTransaction();
            System.out.println(">>> DEBUG DatabaseInitializer: Transacción iniciada");

            // Verificar si ya existen datos
            System.out.println(">>> DEBUG DatabaseInitializer: Verificando si existen datos...");
            Long countRoles = (Long) session.createQuery("SELECT COUNT(r) FROM Rol r").uniqueResult();
            System.out.println(">>> DEBUG DatabaseInitializer: Roles encontrados: " + countRoles);

            if (countRoles > 0) {
                System.out.println(">>> La base de datos ya contiene datos. Omitiendo inicialización.");
                transaction.commit();
                return;
            }

            System.out.println(">>> Inicializando base de datos con datos de prueba...");

            // 1. Crear y guardar Roles
            System.out.println(">>> DEBUG: Creando roles...");
            Rol rolResponsable = new Rol("Responsable de Inspecciones", "Encargado de gestionar inspecciones");
            Rol rolTecnico = new Rol("Técnico", "Técnico de campo");
            session.persist(rolResponsable);
            session.persist(rolTecnico);
            System.out.println(">>> DEBUG: Roles creados");

            // 2. Crear y guardar Empleados
            System.out.println(">>> DEBUG: Creando empleados...");
            Empleado empleado1 = new Empleado("Juan Pérez", "juanperez@email.com");
            empleado1.setRol(rolResponsable);
            session.persist(empleado1);

            Empleado empleado2 = new Empleado("María García", "maria.garcia@empresa.com");
            empleado2.setRol(rolTecnico);
            session.persist(empleado2);
            System.out.println(">>> DEBUG: Empleados creados");

            // 3. Crear y guardar Usuario
            System.out.println(">>> DEBUG: Creando usuario...");
            Usuario usuario = new Usuario("admin", "admin123", empleado1);
            session.persist(usuario);
            System.out.println(">>> DEBUG: Usuario creado");

            // 4. Crear y guardar Sesión
            System.out.println(">>> DEBUG: Creando sesión...");
            Sesion sesion = new Sesion("2025-01-01 8:00", "2025-01-01 17:00", usuario);
            session.persist(sesion);
            System.out.println(">>> DEBUG: Sesión creada");

            // 5. Crear y guardar Sismógrafos
            System.out.println(">>> DEBUG: Creando sismógrafos...");

            // Crear sismógrafos SIN usar el constructor que agrega CambioEstado inicial
            Sismografo sismo1 = new Sismografo();
            sismo1.setIdentificadorSismografo(101);
            sismo1.setFechaAdquisicion("2020-10-21");
            sismo1.setNroSerie(2000);
            // No agregamos cambio de estado inicial para evitar el error de empleado null
            sismo1.setCambiosEstado(new ArrayList<>());
            session.persist(sismo1);

            Sismografo sismo2 = new Sismografo();
            sismo2.setIdentificadorSismografo(102);
            sismo2.setFechaAdquisicion("2021-08-20");
            sismo2.setNroSerie(2001);
            sismo2.setCambiosEstado(new ArrayList<>());
            session.persist(sismo2);

            Sismografo sismo3 = new Sismografo();
            sismo3.setIdentificadorSismografo(103);
            sismo3.setFechaAdquisicion("2023-01-10");
            sismo3.setNroSerie(2010);
            sismo3.setCambiosEstado(new ArrayList<>());
            session.persist(sismo3);

            System.out.println(">>> DEBUG: Sismógrafos creados");

            // 6. Crear y guardar Estaciones Sismológicas
            System.out.println(">>> DEBUG: Creando estaciones sismológicas...");
            EstacionSismologica est1 = new EstacionSismologica(101, "01CD", "2020-02-01", "-34.6118", "-58.3960", "Estación Central", 21458);
            est1.setSismografo(sismo1);
            session.persist(est1);

            EstacionSismologica est2 = new EstacionSismologica(102, "02CD", "2020-05-10", "35.6118", "-55.3960", "Estación Norte", 15874);
            est2.setSismografo(sismo2);
            session.persist(est2);

            EstacionSismologica est3 = new EstacionSismologica(103, "03CD", "2018-06-10", "33.6118", "-58.3960", "Estación Sur", 78541);
            est3.setSismografo(sismo3);
            session.persist(est3);
            System.out.println(">>> DEBUG: Estaciones sismológicas creadas");

            // 7. Crear y guardar Órdenes de Inspección
            System.out.println(">>> DEBUG: Creando órdenes de inspección...");
            OrdenInspeccion orden1 = new OrdenInspeccion(1, "2025-01-01 10:00", "2025-01-01 11:00", "2025-01-01", "Observacion cierre 1", est1, empleado1, Estado.REALIZADA);
            session.persist(orden1);

            OrdenInspeccion orden2 = new OrdenInspeccion(2, "2025-01-01 10:00", "2025-02-01 14:00", "2025-01-01", "Observacion cierre 2", est2, empleado1, Estado.REALIZADA);
            session.persist(orden2);

            OrdenInspeccion orden3 = new OrdenInspeccion(3, "2025-01-01 10:00", "2025-05-01 10:00", "2025-01-01", "Observacion cierre 3", est3, empleado1, Estado.REALIZADA);
            session.persist(orden3);
            System.out.println(">>> DEBUG: Órdenes de inspección creadas");

            transaction.commit();
            System.out.println(">>> Base de datos inicializada correctamente con datos de prueba.");

        } catch (Exception e) {
            System.err.println(">>> ERROR CRÍTICO en DatabaseInitializer.initializeData(): " + e.getMessage());
            e.printStackTrace();
            if (transaction != null) {
                try {
                    transaction.rollback();
                    System.out.println(">>> DEBUG: Transacción revertida");
                } catch (Exception rollbackEx) {
                    System.err.println(">>> ERROR al hacer rollback: " + rollbackEx.getMessage());
                }
            }
        } finally {
            if (session != null) {
                try {
                    session.close();
                    System.out.println(">>> DEBUG DatabaseInitializer: Session cerrada");
                } catch (Exception closeEx) {
                    System.err.println(">>> ERROR al cerrar session: " + closeEx.getMessage());
                }
            }
        }
    }

    /**
     * Limpia toda la base de datos (útil para pruebas)
     */
    public static void clearDatabase() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Eliminar en orden inverso a las dependencias
            session.createQuery("DELETE FROM OrdenInspeccion").executeUpdate();
            session.createQuery("DELETE FROM EstacionSismologica").executeUpdate();
            session.createQuery("DELETE FROM Sismografo").executeUpdate();
            session.createQuery("DELETE FROM Sesion").executeUpdate();
            session.createQuery("DELETE FROM Usuario").executeUpdate();
            session.createQuery("DELETE FROM Empleado").executeUpdate();
            session.createQuery("DELETE FROM Rol").executeUpdate();

            transaction.commit();
            System.out.println(">>> Base de datos limpiada correctamente.");

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println(">>> Error al limpiar la base de datos: " + e.getMessage());
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
