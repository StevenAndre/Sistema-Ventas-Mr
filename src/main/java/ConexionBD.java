import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static Connection conn = null;

    public ConexionBD(){
        try {
            String url = "jdbc:mysql://localhost:3306/sisvenmaranon";
            String user = "root";
            String password = "steandsql03";
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConection(){
        return conn;
    }

}
