import java.sql.*;
import java.util.Scanner;

public class AdminManager {
    private static Scanner scanner = new Scanner(System.in);

    public static boolean loginAdmin() throws SQLException {
        System.out.print("Ingrese el nombre de usuario del administrador: ");
        String username = scanner.nextLine();
        System.out.print("Ingrese la contraseña del administrador: ");
        String password = scanner.nextLine();

        String sql = "SELECT COUNT(*) FROM usuarios WHERE username = ? AND password = ? AND tipo = 'administrador'";
        PreparedStatement pstmt = DatabaseManager.conn.prepareStatement(sql);
        pstmt.setString(1, username);
        pstmt.setString(2, password);
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        rs.close();
        pstmt.close();

        return count > 0;
    }

    public static void createAdmin() throws SQLException {
        System.out.print("Ingrese el nombre de usuario del nuevo administrador: ");
        String username = scanner.nextLine();
        System.out.print("Ingrese la contraseña del nuevo administrador: ");
        String password = scanner.nextLine();

        String sql = "INSERT INTO usuarios (username, password, tipo) VALUES (?, ?, 'administrador')";
        PreparedStatement pstmt = DatabaseManager.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, username);
        pstmt.setString(2, password);
        pstmt.executeUpdate();
        ResultSet rs = pstmt.getGeneratedKeys();
        if (rs.next()) {
            int adminId = rs.getInt(1);
            System.out.println("Administrador creado con ID: " + adminId);
        }
        rs.close();
        pstmt.close();
    }

    public static void readAdmins() throws SQLException {
        String sql = "SELECT id, username FROM usuarios WHERE tipo = 'administrador'";
        PreparedStatement pstmt = DatabaseManager.conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            String username = rs.getString("username");
            System.out.println("ID: " + id + ", Usuario: " + username);
        }
        rs.close();
        pstmt.close();
    }

    public static void updateAdmin() throws SQLException {
        System.out.print("Ingrese el ID del administrador a actualizar: ");
        int adminId = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea después de la entrada numérica
        System.out.print("Ingrese el nuevo nombre de usuario del administrador: ");
        String nuevoUsername = scanner.nextLine();
        System.out.print("Ingrese la nueva contraseña del administrador: ");
        String nuevaPassword = scanner.nextLine();

        String sql = "UPDATE usuarios SET username = ?, password = ? WHERE id = ? AND tipo = 'administrador'";
        PreparedStatement pstmt = DatabaseManager.conn.prepareStatement(sql);
        pstmt.setString(1, nuevoUsername);
        pstmt.setString(2, nuevaPassword);
        pstmt.setInt(3, adminId);
        pstmt.executeUpdate();
        pstmt.close();
        System.out.println("Administrador actualizado correctamente.");
    }

    public static void deleteAdmin() throws SQLException {
        System.out.print("Ingrese el ID del administrador a eliminar: ");
        int adminId = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea después de la entrada numérica

        String sql = "DELETE FROM usuarios WHERE id = ? AND tipo = 'administrador'";
        PreparedStatement pstmt = DatabaseManager.conn.prepareStatement(sql);
        pstmt.setInt(1, adminId);
        pstmt.executeUpdate();
        pstmt.close();
        System.out.println("Administrador eliminado correctamente.");
    }
}