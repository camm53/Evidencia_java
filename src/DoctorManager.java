import java.sql.*;
import java.util.Scanner;

public class DoctorManager {
    private static Scanner scanner = new Scanner(System.in);

    public static void createDoctor() throws SQLException {
        System.out.print("Ingrese el nombre del doctor: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese la especialidad del doctor: ");
        String especialidad = scanner.nextLine();

        String sql = "INSERT INTO doctores (nombre, especialidad) VALUES (?, ?)";
        PreparedStatement pstmt = DatabaseManager.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, nombre);
        pstmt.setString(2, especialidad);
        pstmt.executeUpdate();
        ResultSet rs = pstmt.getGeneratedKeys();
        if (rs.next()) {
            int doctorId = rs.getInt(1);
            System.out.println("Doctor creado con ID: " + doctorId);
        }
        rs.close();
        pstmt.close();
    }

    public static void readDoctores() throws SQLException {
        String sql = "SELECT * FROM doctores";
        PreparedStatement pstmt = DatabaseManager.conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            String nombre = rs.getString("nombre");
            String especialidad = rs.getString("especialidad");
            System.out.println("ID: " + id + ", Nombre: " + nombre + ", Especialidad: " + especialidad);
        }
        rs.close();
        pstmt.close();
    }

    public static void updateDoctor() throws SQLException {
        System.out.print("Ingrese el ID del doctor a actualizar: ");
        int doctorId = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea después de la entrada numérica
        System.out.print("Ingrese el nuevo nombre del doctor: ");
        String nuevoNombre = scanner.nextLine();
        System.out.print("Ingrese la nueva especialidad del doctor: ");
        String nuevaEspecialidad = scanner.nextLine();

        String sql = "UPDATE doctores SET nombre = ?, especialidad = ? WHERE id = ?";
        PreparedStatement pstmt = DatabaseManager.conn.prepareStatement(sql);
        pstmt.setString(1, nuevoNombre);
        pstmt.setString(2, nuevaEspecialidad);
        pstmt.setInt(3, doctorId);
        pstmt.executeUpdate();
        pstmt.close();
        System.out.println("Doctor actualizado correctamente.");
    }

    public static void deleteDoctor() throws SQLException {
        System.out.print("Ingrese el ID del doctor a eliminar: ");
        int doctorId = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea después de la entrada numérica

        // Eliminar citas relacionadas con el doctor
        String sql = "DELETE FROM citas WHERE doctor_id = ?";
        PreparedStatement pstmt = DatabaseManager.conn.prepareStatement(sql);
        pstmt.setInt(1, doctorId);
        pstmt.executeUpdate();
        pstmt.close();

        // Eliminar doctor
        sql = "DELETE FROM doctores WHERE id = ?";
        pstmt = DatabaseManager.conn.prepareStatement(sql);
        pstmt.setInt(1, doctorId);
        pstmt.executeUpdate();
        pstmt.close();
        System.out.println("Doctor y sus citas eliminados correctamente.");
        }
        }