package Engine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static Connection db;
    private static String user = "root";
    private static String password = "";
    private static String database = "db_command";
    public static Connection getInstance() {
        if (db != null) return db;

        try {
            String driver = "com.mysql.jdbc.Driver";
            Class.forName(driver); // instancie le driver

            db = DriverManager.getConnection("jdbc:mysql://localhost/"+database, user, password);

            return db;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
          return null;
    }

}
