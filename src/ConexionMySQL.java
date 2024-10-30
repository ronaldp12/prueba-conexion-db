import java.sql.*;
import java.util.Scanner;

public class ConexionMySQL {
    // Configuración de la conexión
    public static void main(String[] args) {
        Connection myConn = null;

        try {
            myConn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/project",
                    "root",
                    ""
            );
            System.out.println("Genial, nos conectamos");

                Scanner scanner= new Scanner(System.in);
                String msj, nombre, papellido, mapellido, email;
                double salario;
                int opcion, id;
                do{
                    msj= "Bienvenido, Elige una opcion \n\n";
                    msj +="1. Insertar Empleado\n";
                    msj +="2. Consultar Empleado\n";
                    msj +="3. Actualizar Empleado\n";
                    msj +="4. Eliminar Empleado\n";
                    System.out.println(msj);
                    opcion= scanner.nextInt();
                    switch (opcion){
                        case 1:
                            scanner.nextLine();
                            System.out.println("Ingrese el nombre del empleado");
                            nombre= scanner.nextLine();
                            System.out.println("Ingrese el primer apellido del empleado");
                            papellido= scanner.nextLine();
                            System.out.println("Ingrese el segundo apellido del empleado");
                            mapellido= scanner.nextLine();
                            System.out.println("Ingrese el email del empleado");
                            email= scanner.nextLine();
                            System.out.println("Ingrese el salario del empleado");
                            salario= scanner.nextDouble();
                            insertarEmpleado(myConn,nombre,papellido,mapellido,email,salario);
                        case 2: consultarEmpleados(myConn);
                        case 3:
                            System.out.println("Ingrese el id del empleado");
                            id= scanner.nextInt();
                            scanner.nextLine();
                            System.out.println("Ingrese el nombre del empleado");
                            nombre= scanner.nextLine();
                            System.out.println("Ingrese el primer apellido del empleado");
                            papellido= scanner.nextLine();
                            System.out.println("Ingrese el segundo apellido del empleado");
                            mapellido= scanner.nextLine();
                            System.out.println("Ingrese el email del empleado");
                            email= scanner.nextLine();
                            System.out.println("Ingrese el salario del empleado");
                            salario= scanner.nextDouble();
                            actualizarEmpleado(myConn,id,nombre,papellido,mapellido,email,salario);
                        case 4:
                            System.out.println("Ingrese el id del empleado");
                            id= scanner.nextInt();
                            eliminarEmpleado(myConn,id);
                    }
                }
                while (opcion==2);

        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Algo salio mal :(");
        }
    }

    // Método para insertar un empleado
    public static void insertarEmpleado(Connection conexion, String first_name, String pa_surname,String ma_surname, String email, double salary)
            throws SQLException {
        String sql = "INSERT INTO employees (first_name, pa_surname, ma_surname, email, salary) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, first_name);
            pstmt.setString(2, pa_surname);
            pstmt.setString(3, ma_surname);
            pstmt.setString(4, email);
            pstmt.setDouble(5, salary);
            pstmt.executeUpdate();
            System.out.println("Empleado insertado correctamente!");
        }
    }

    // Método para consultar empleados
    private static void consultarEmpleados(Connection conexion) throws SQLException {
        String sql = "SELECT * FROM employees";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.printf("ID: %d, Nombre: %s, Primer Apellido: %s, Segundo Apellido: %s,Email: %s, Salario: %.2f%n",
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("pa_surname"),
                        rs.getString("ma_surname"),
                        rs.getString("email"),
                        rs.getDouble("salary"));
            }
        }
    }

    // Método para actualizar un empleado
    private static void actualizarEmpleado(Connection conexion, int id, String first_name,String pa_surname,String ma_surname, String email, double salary)
            throws SQLException {
        String sql = "UPDATE employees SET first_name = ?,pa_surname = ?, ma_surname = ?, email = ?, salary = ? WHERE id = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, first_name);
            pstmt.setString(2, pa_surname);
            pstmt.setString(3, ma_surname);
            pstmt.setString(4, email);
            pstmt.setDouble(5, salary);
            pstmt.setInt(6, id);
            pstmt.executeUpdate();
            System.out.println("Empleado actualizado correctamente!");
        }
    }

    // Método para eliminar un empleado
    private static void eliminarEmpleado(Connection conexion, int id) throws SQLException {
        String sql = "DELETE FROM employees WHERE id = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Empleado eliminado correctamente!");
        }
    }
}
