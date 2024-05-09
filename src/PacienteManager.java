import java.sql.*;
import java.util.Scanner;

public class PacienteManager {
    private static Scanner scanner = new Scanner(System.in);

    public static void createPaciente() throws SQLException {
        System.out.print("Ingrese el nombre del paciente: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese la edad del paciente: ");
        int edad = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea después de la entrada numérica
        System.out.print("Ingrese la dirección del paciente: ");
        String direccion = scanner.nextLine();

        String sql = "INSERT INTO pacientes (nombre, edad, direccion) VALUES (?, ?, ?)";
        PreparedStatement pstmt = DatabaseManager.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, nombre);
        pstmt.setInt(2, edad);
        pstmt.setString(3, direccion);
        pstmt.executeUpdate();
        ResultSet rs = pstmt.getGeneratedKeys();
        if (rs.next()) {
            int pacienteId = rs.getInt(1);
            System.out.println("Paciente creado con ID: " + pacienteId);
        }
        rs.close();
        pstmt.close();
    }

    public static void readPacientes() throws SQLException {
        String sql = "SELECT * FROM pacientes";
        PreparedStatement pstmt = DatabaseManager.conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            String nombre = rs.getString("nombre");
            int edad = rs.getInt("edad");
            String direccion = rs.getString("direccion");
            System.out.println("ID: " + id + ", Nombre: " + nombre + ", Edad: " + edad + ", Dirección: " + direccion);
        }
        rs.close();
        pstmt.close();
    }

    public static void updatePaciente() throws SQLException {
        System.out.print("Ingrese el ID del paciente a actualizar: ");
        int pacienteId = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea después de la entrada numérica
        System.out.print("Ingrese el nuevo nombre del paciente: ");
        String nuevoNombre = scanner.nextLine();
        System.out.print("Ingrese la nueva edad del paciente: ");
        int nuevaEdad = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea después de la entrada numérica
        System.out.print("Ingrese la nueva dirección del paciente: ");
        String nuevaDireccion = scanner.nextLine();

        String sql = "UPDATE pacientes SET nombre = ?, edad = ?, direccion = ? WHERE id = ?";
        PreparedStatement pstmt = DatabaseManager.conn.prepareStatement(sql);
        pstmt.setString(1, nuevoNombre);
        pstmt.setInt(2, nuevaEdad);
        pstmt.setString(3, nuevaDireccion);
        pstmt.setInt(4, pacienteId);
        pstmt.executeUpdate();
        pstmt.close();
        System.out.println("Paciente actualizado correctamente.");
    }

    public static void deletePaciente() throws SQLException {
        System.out.print("Ingrese el ID del paciente a eliminar: ");
        int pacienteId = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea después de la entrada numérica

        // Eliminar citas relacionadas con el paciente
        String sql = "DELETE FROM citas WHERE paciente_id = ?";
        PreparedStatement pstmt = DatabaseManager.conn.prepareStatement(sql);
        pstmt.setInt(1, pacienteId);
        pstmt.executeUpdate();
        pstmt.close();

        // Eliminar paciente
        sql = "DELETE FROM pacientes WHERE id = ?";
        pstmt = DatabaseManager.conn.prepareStatement(sql);
        pstmt.setInt(1, pacienteId);
        pstmt.executeUpdate();
        pstmt.close();
        System.out.println("Paciente y sus citas eliminados correctamente.");
    }
}