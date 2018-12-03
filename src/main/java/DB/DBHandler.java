package DB;

import java.sql.*;

public class DBHandler {

    private final String dbName;
    private final String dbUser;
    private final String dbPassword;
    private final String connectionString;

    private Connection conn;

    private static DBHandler instance;


    private DBHandler() {
        dbName = "Smarthouse";
        dbUser = "peter";
        dbPassword = "123limboMYSQL";
        connectionString = "jdbc:mysql://127.0.0.1/" + dbName + "?user=" + dbUser + "&password=" + dbPassword + "&useSSL=false";

        try {

            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        if (conn != null) {

            System.out.println("Connected");
        } else {
            System.out.println("Failed to connect");
        }
    }


    public static DBHandler getInstance() {

        if (instance == null) {

            instance = new DBHandler();
        }

        return instance;
    }

    public void updateDeviceStatus(String id, String value) {


        String query = ("UPDATE Device SET Value=? WHERE id =?;");

        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(query);

            ps.setString(2, id);
            ps.setString(1, value);

            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public String isSomethingOn(String id) {

        int idInt = Integer.parseInt(id);
        String query = ("Select Value from Device where id = ?;");

        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(query);

            ps.setInt(1, idInt);

            ResultSet rs = ps.executeQuery();

            rs.next();

            String foundResult = rs.getNString("Value");


            return foundResult;
        } catch (SQLException e) {
            return null;
        }
    }

    public String login(String e_mail, String passWord) {

        System.out.println("email "+e_mail);
        System.out.println("pass "+passWord);

        // int idInt = Integer.parseInt(id);
        String query = ("SELECT * FROM User WHERE User.email =? AND User.password =?;");

        String result ="";



        try {


            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM User WHERE User.email =? AND User.password =?;");


            ps.setString(1, e_mail);
            ps.setString(2, passWord);

            ResultSet rs = ps.executeQuery();



            //om användaren finns
            if (rs.next()) {
                result = rs.getString("userName");



                //om användaren inte finns
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("result String in DB: "+ result);


        if (result.isEmpty())
            return "doesn't exist";
        else

        return "exists";



    }
}

