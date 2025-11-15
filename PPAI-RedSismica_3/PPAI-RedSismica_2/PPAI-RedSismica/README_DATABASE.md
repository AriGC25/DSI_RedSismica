# Guía de Acceso a la Base de Datos H2

## Ubicación de la Base de Datos

La base de datos H2 se crea automáticamente cuando ejecutas la aplicación por primera vez. Se encuentra en:

```
PPAI-RedSismica/data/redsismica.mv.db
```

**Nota:** La carpeta `data` NO existirá hasta que ejecutes la aplicación por primera vez. Hibernate la creará automáticamente.

## Cómo Acceder a la Consola Web de H2

### Opción 1: Usando el Servidor Web de H2 (Recomendado)

1. **Agregar dependencia en pom.xml** (si no está ya):
```xml
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <version>2.2.224</version>
</dependency>
```

2. **Ejecutar la aplicación** para que cree la base de datos.

3. **Iniciar la consola H2** desde línea de comandos:
```cmd
cd "C:\Cursos y CVs\9. Ing. en Sistemas\3. Tercero\DSI\TPAI\Primer entrega\Versiones_de_PPAI\PPAI-RedSismica_3\PPAI-RedSismica_3\PPAI-RedSismica_2\PPAI-RedSismica"
java -cp target\classes;%USERPROFILE%\.m2\repository\com\h2database\h2\2.2.224\h2-2.2.224.jar org.h2.tools.Server -web -webAllowOthers -tcp -tcpAllowOthers -ifNotExists
```

4. **Acceder a la consola web**:
   - Abre tu navegador en: http://localhost:8082
   - Configuración de conexión:
     - **JDBC URL**: `jdbc:h2:./data/redsismica`
     - **Usuario**: `sa`
     - **Contraseña**: (dejar vacío)

### Opción 2: Usando una Herramienta Externa

Puedes usar herramientas como **DBeaver**, **IntelliJ IDEA Database Tools**, o **DbVisualizer**:

- **Tipo de Base de Datos**: H2 Embedded
- **URL de Conexión**: `jdbc:h2:./data/redsismica` (ruta relativa desde la carpeta del proyecto)
- **Usuario**: `sa`
- **Contraseña**: (vacía)

## Estructura de la Base de Datos

La aplicación crea automáticamente las siguientes tablas:

- `roles` - Roles de los empleados
- `empleados` - Información de empleados
- `usuarios` - Usuarios del sistema
- `sesion` - Sesiones activas
- `sismografo` - Sismógrafos
- `estaciones_sismologica` - Estaciones sismológicas
- `ordenes_inspeccion` - Órdenes de inspección
- `motivos_fuera_servicio` - Motivos de fuera de servicio
- `cambio_estado` - Historial de cambios de estado

## Datos Precargados

Al ejecutar la aplicación por primera vez, se cargan automáticamente los siguientes datos:

### Roles
- Responsable de Inspecciones
- Técnico

### Empleados
- Juan Pérez (juanperez@email.com) - Responsable de Inspecciones
- María García (maria.garcia@empresa.com) - Técnico

### Usuario
- **Usuario**: admin
- **Contraseña**: admin123

### Estaciones Sismológicas
- Estación Central (ID: 21458)
- Estación Norte (ID: 15874)
- Estación Sur (ID: 78541)

### Órdenes de Inspección
- Orden #1 - Estación Central
- Orden #2 - Estación Norte
- Orden #3 - Estación Sur

## Verificar que la Base de Datos se Creó Correctamente

Después de ejecutar la aplicación, verifica:

1. **Carpeta data creada**: Debe existir `PPAI-RedSismica/data/`
2. **Archivo de base de datos**: Debe existir `redsismica.mv.db` dentro de la carpeta `data`
3. **Logs en consola**: Deberías ver mensajes como:
   ```
   >>> Sistema inicializado con datos desde la base de datos.
   >>> Sesión: admin
   >>> Empleados cargados: 2
   >>> Órdenes cargadas: 3
   ```

## Problemas Comunes

### La carpeta data no se crea
- **Solución**: Ejecuta la aplicación al menos una vez. Hibernate creará la carpeta automáticamente.

### Error de conexión a la base de datos
- **Solución**: Verifica que la ruta en `hibernate.cfg.xml` sea correcta: `jdbc:h2:./data/redsismica`

### No se cargan los datos
- **Solución**: Revisa los logs de la consola. Si dice "La base de datos ya contiene datos", significa que ya están cargados.

## Limpiar la Base de Datos

Si necesitas reiniciar la base de datos desde cero:

1. **Opción 1**: Elimina la carpeta `data` completa y ejecuta la aplicación nuevamente.
2. **Opción 2**: Usa el método `DatabaseInitializer.clearDatabase()` en tu código (solo para desarrollo/pruebas).

## Consultas SQL Útiles

```sql
-- Ver todos los empleados
SELECT * FROM empleados;

-- Ver todas las órdenes
SELECT * FROM ordenes_inspeccion;

-- Ver órdenes con sus estaciones
SELECT o.numero_orden, o.fecha_hora_inicio, e.nombre 
FROM ordenes_inspeccion o 
JOIN estaciones_sismologica e ON o.estacion_id = e.id;

-- Ver usuarios y sus empleados
SELECT u.usuario, e.nombre, e.email 
FROM usuarios u 
JOIN empleados e ON u.empleado_id = e.id;
```

## Cambios Realizados

### Archivos Creados:
1. **DatabaseInitializer.java** - Inicializa la base de datos con datos de prueba
2. **DataLoader.java** - Carga datos desde la base de datos

### Archivos Modificados:
1. **PantallaOrdenController.java** - Ahora carga datos desde la base de datos en lugar de tenerlos hardcodeados

### Ventajas del Nuevo Enfoque:
- ✅ Datos persistentes entre ejecuciones
- ✅ Separación de responsabilidades
- ✅ Fácil de mantener y modificar datos
- ✅ Compatible con entornos de producción
- ✅ Posibilidad de usar herramientas de administración de BD

