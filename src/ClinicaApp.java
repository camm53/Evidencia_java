import java.util.Scanner;
import java.util.InputMismatchException;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;

public class ClinicaApp {
    private static Connection conn;
    private static Statement stmt;
    private static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);

        try {
            // Establecer conexión a MySQL
            conn = DatabaseManager.connectToDatabase();
            stmt = conn.createStatement();
            boolean existe= DatabaseManager.checkDatabaseExists();
            
            // Crear la base de datos y las tablas si no existen
            DatabaseManager.createDatabaseAndTables(existe);

            
            // Iniciar sesión como administrador
            boolean isLoggedIn = AdminManager.loginAdmin();

            if (isLoggedIn) {
                // Menú principal
                int option = 0;
                do {
                    displayMenu();
                    try {
                        option = scanner.nextInt();
                        scanner.nextLine(); // Consumir el salto de línea después de la entrada numérica

                        switch (option) {
                            case 1:
                                PacienteManager.createPaciente();
                                break;
                            case 2:
                                PacienteManager.readPacientes();
                                break;
                            case 3:
                                PacienteManager.updatePaciente();
                                break;
                            case 4:
                                PacienteManager.deletePaciente();
                                break;
                            case 5:
                                DoctorManager.createDoctor();
                                break;
                            case 6:
                                DoctorManager.readDoctores();
                                break;
                            case 7:
                                DoctorManager.updateDoctor();
                                break;
                            case 8:
                                DoctorManager.deleteDoctor();
                                break;
                            case 9:
                                CitaManager.createCita();
                                break;
                            case 10:
                                CitaManager.readCitas();
                                break;
                            case 11:
                                CitaManager.updateCita();
                                break;
                            case 12:
                                CitaManager.deleteCita();
                                break;
                            case 13:
                                AdminManager.createAdmin();
                                break;
                            case 14:
                                AdminManager.readAdmins();
                                break;
                            case 15:
                                AdminManager.updateAdmin();
                                break;
                            case 16:
                                AdminManager.deleteAdmin();
                                break;
                            case 17:
                                System.out.println("¡Hasta luego!");
                                break;
                            default:
                                System.out.println("Opción inválida. Intente nuevamente.");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Error: Entrada inválida. Por favor, ingrese un número.");
                        scanner.nextLine(); // Limpiar el búfer de entrada
                    } catch (SQLException e) {
                        System.out.println("Error de SQL: " + e.getMessage());
                    }
                } while (option != 17);
            } else {
                System.out.println("Acceso denegado. Necesitas iniciar sesión como administrador.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseManager.closeResources();
        }
    }

    private static void displayMenu() {
        System.out.println("\nMenú Principal:");
        System.out.println("1. Crear Paciente");
        System.out.println("2. Leer Pacientes");
        System.out.println("3. Actualizar Paciente");
        System.out.println("4. Eliminar Paciente");
        System.out.println("5. Crear Doctor");
        System.out.println("6. Leer Doctores");
        System.out.println("7. Actualizar Doctor");
        System.out.println("8. Eliminar Doctor");
        System.out.println("9. Crear Cita");
        System.out.println("10. Leer Citas");
        System.out.println("11. Actualizar Cita");
        System.out.println("12. Eliminar Cita");
        System.out.println("13. Crear Administrador");
        System.out.println("14. Leer Administradores");
        System.out.println("15. Actualizar Administrador");
        System.out.println("16. Eliminar Administrador");
        System.out.println("17. Salir");
        System.out.print("Ingrese una opción: ");
    }
}