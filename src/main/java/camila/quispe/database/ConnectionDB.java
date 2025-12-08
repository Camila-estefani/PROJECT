package camila.quispe.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://camiestefani.cd6cs4gow0xi.us-east-1.rds.amazonaws.com:3306/db_inventario_computadoras";
    private static final String USER = "admin"; // Tu usuario de RDS
    private static final String PASS = "camiestefani"; // Tu contraseña


    public static Connection getConnection() {
        Connection con = null;
        try {
            // Cargar el driver JDBC
            Class.forName(DRIVER);

            // Establecer la conexión
            con = DriverManager.getConnection(URL, USER, PASS);

            if (con != null) {
                System.out.println("Conexión exitosa a AWS RDS!");
            }

        } catch (ClassNotFoundException e) {
            System.err.println("Error: No se encontró el driver de MySQL.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error: Falló la conexión a la base de datos.");
            System.err.println("Revisa el endpoint, credenciales o el Security Group de AWS.");
            e.printStackTrace();
        }
        return con;
    }
}