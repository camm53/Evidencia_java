import java.sql.*;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "unadb";

    public static Connection conn;
    private static Statement stmt;

    public static Connection connectToDatabase() throws SQLException {
        conn = DriverManager.getConnection(DB_URL, "root", "");
        return conn;
    }
    public static boolean checkDatabaseExists() throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, "root", "")) {
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet resultSet = meta.getCatalogs();
            while (resultSet.next()) {
                String dbName = resultSet.getString(1);
                if (dbName.equals(DB_NAME)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static void createDatabaseAndTables(boolean existe) throws SQLException {
        stmt = conn.createStatement();

        // Crear la base de datos si no existe
        stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);

        // Cambiar a la base de datos clinica
        stmt.executeUpdate("USE " + DB_NAME);

        // Crear la tabla usuarios si no existe
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS usuarios (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "username VARCHAR(255) NOT NULL, " +
                "password VARCHAR(255) NOT NULL, " +
                "tipo VARCHAR(20) NOT NULL)");

        // Crear la tabla pacientes si no existe
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS pacientes (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "nombre VARCHAR(255) NOT NULL, " +
                "edad INT NOT NULL, " +
                "direccion VARCHAR(255) NOT NULL)");

        // Crear la tabla doctores si no existe
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS doctores (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "nombre VARCHAR(255) NOT NULL, " +
                "especialidad VARCHAR(255) NOT NULL)");

        // Crear la tabla citas si no existe
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS citas (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "paciente_id INT NOT NULL, " +
                "doctor_id INT NOT NULL, " +
                "fecha DATE NOT NULL, " +
                "hora TIME NOT NULL, " +
                "FOREIGN KEY (paciente_id) REFERENCES pacientes(id), " +
                "FOREIGN KEY (doctor_id) REFERENCES doctores(id))");

        // Insertar un administrador por defecto si la tabla está vacía
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM usuarios");
        rs.next();
        int count = rs.getInt(1);
        if (count == 0) {
            stmt.executeUpdate("INSERT INTO usuarios (username, password, tipo) VALUES ('admin', 'admin', 'administrador')");
            System.out.println("Se ha creado un administrador por defecto (usuario: admin, contraseña: admin)");
        }
        rs.close();
        if (!existe) {
         // Insertar datos predeterminados en la tabla pacientes
        stmt.executeUpdate("INSERT INTO pacientes (nombre, edad, direccion) VALUES ('Juan Pérez', 35, 'Calle Principal 123'), ('María García', 42, 'Avenida Central 456'), ('Pedro Ramírez', 28, 'Calle Secundaria 789')");

        // Insertar datos predeterminados en la tabla doctores
        stmt.executeUpdate("INSERT INTO doctores (nombre, especialidad) VALUES ('Dr. Carlos López', 'Pediatría'), ('Dra. Ana Rodríguez', 'Cardiología'), ('Dr. Luis Martínez', 'Dermatología')");

        // Insertar datos predeterminados en la tabla citas
        stmt.executeUpdate("INSERT INTO citas (paciente_id, doctor_id, fecha, hora) VALUES (1, 1, '2023-06-15', '10:00:00'), (2, 2, '2023-06-16', '14:30:00'), (3, 3, '2023-06-17', '16:45:00')");
    }
    }

    public static void closeResources() {
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}