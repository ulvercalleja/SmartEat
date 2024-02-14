package proyecto.smarteat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ManejadorBaseDatos {

    private static final String DB_URL = "jdbc:mysql://mysql-8001.dinaserver.com:3306/tesla";
    private static final String DB_USER = "sge";
    private static final String DB_PASSWORD = "InstaladorDAM2!";

    public static void insertarUsuario(String nombre, String pass, String email) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String query = "INSERT INTO SE_users (nombre, pass, email) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, nombre);
            statement.setString(2, pass);
            statement.setString(3, email);
            statement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
