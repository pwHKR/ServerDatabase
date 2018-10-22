package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHandler {

    private final String dbName;
    private final String dbUser;
    private final String dbPassword;
    private final String connectionString;

    private Connection conn;


    public DBHandler() {
        dbName = "Smarthouse";
        dbUser = "peter";
        dbPassword ="123limboMYSQL";
        connectionString =  "jdbc:mysql://127.0.0.1/" + dbName + "?user=" + dbUser + "&password=" + dbPassword + "&useSSL=false";

        try {

            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        if(conn != null){

            System.out.println("Connected");
        }
    }


}