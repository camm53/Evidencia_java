import java.sql.*;
import java.util.Scanner;

public class CitaManager {
    private static Scanner scanner = new Scanner(System.in);

    public static void createCita() throws SQLException {
        System.out.print("Ingrese el ID del paciente: ");
        int pacienteId = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea después de la entrada numérica
        System.out.print("Ingrese el ID del doctor: ");
        int doctorId = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea después de la entrada numérica
        System.out.print("Ingrese la fecha de la cita (YYYY-MM-DD): ");
        String fechaStr = scanner.nextLine();
        System.out.print("Ingrese la hora de la cita (HH:MM:SS): ");
        String horaStr = scanner.nextLine();

        String sql = "INSERT INTO citas (paciente_id, doctor_id, fecha, hora) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = DatabaseManager.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pstmt.setInt(1, pacienteId);
        pstmt.setInt(2, doctorId);
        pstmt.setString(3, fechaStr);
        pstmt.setString(4, horaStr);
        pstmt.executeUpdate();
        ResultSet rs = pstmt.getGeneratedKeys();
        if (rs.next()) {
            int citaId = rs.getInt(1);
            System.out.println("Cita creada con ID: " + citaId);
        }
        rs.close();
        pstmt.close();
    }

    public static void readCitas() throws SQLException {
        String sql = "SELECT c.id, p.nombre AS paciente, d.nombre AS doctor, c.fecha, c.hora " +
                     "FROM citas c " +
                     "JOIN pacientes p ON c.paciente_id = p.id " +
                     "JOIN doctores d ON c.doctor_id = d.id";
        PreparedStatement pstmt = DatabaseManager.conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            String paciente = rs.getString("paciente");
            String doctor = rs.getString("doctor");
            String fecha = rs.getString("fecha");
            String hora = rs.getString("hora");
            System.out.println("ID: " + id + ", Paciente: " + paciente + ", Doctor: " + doctor + ", Fecha: " + fecha + ", Hora: " + hora);
        }
        rs.close();
        pstmt.close();
    }

    public static void updateCita() throws SQLException {
        System.out.print("Ingrese el ID de la cita a actualizar: ");
        int citaId = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea después de la entrada numérica
        System.out.print("Ingrese el nuevo ID del paciente: ");
        int nuevoPacienteId = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea después de la entrada numérica
        System.out.print("Ingrese el nuevo ID del doctor: ");
        int nuevoDoctorId = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea después de la entrada numérica
        System.out.print("Ingrese la nueva fecha de la cita (YYYY-MM-DD): ");
        String nuevaFechaStr = scanner.nextLine();
        System.out.print("Ingrese la nueva hora de la cita (HH:MM:SS): ");
        String nuevaHoraStr = scanner.nextLine();

        String sql = "UPDATE citas SET paciente_id = ?, doctor_id = ?, fecha = ?, hora = ? WHERE id = ?";
        PreparedStatement pstmt = DatabaseManager.conn.prepareStatement(sql);
        pstmt.setInt(1, nuevoPacienteId);
        pstmt.setInt(2, nuevoDoctorId);
        pstmt.setString(3, nuevaFechaStr);
        pstmt.setString(4, nuevaHoraStr);
        pstmt.setInt(5, citaId);
        pstmt.executeUpdate();
        pstmt.close();
        System.out.println("Cita actualizada correctamente.");
    }

    public static void deleteCita() throws SQLException {
        System.out.print("Ingrese el ID de la cita a eliminar: ");
        int citaId = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea después de la entrada numérica

        String sql = "DELETE FROM citas WHERE id = ?";
        PreparedStatement pstmt = DatabaseManager.conn.prepareStatement(sql);
        pstmt.setInt(1, citaId);
        pstmt.executeUpdate();
        pstmt.close();
        System.out.println("Cita eliminada correctamente.");
    }
}