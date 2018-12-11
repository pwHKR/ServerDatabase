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
        //dbName = "Smarthouse";
        //dbUser = "peter";
        dbUser = "root";
        //dbPassword = "123limboMYSQL";
        dbPassword = "root";
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

    public String getDeviceValue(String id) {

        int idInt = Integer.parseInt(id);
        String query = "Select Value from Device where id = ?;";

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

        System.out.println("email " + e_mail);
        System.out.println("pass " + passWord);

        // int idInt = Integer.parseInt(id);
        String query = ("SELECT * FROM User WHERE User.email =? AND User.password =?;");

        String result = "";

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

        System.out.println("result String in DB: " + result);

        if (result.isEmpty())
            return "doesn't exist";
        else

            return "exists";
    }

    public void createUser(String userName, String password, String email) {

        String query = "Insert INTO User VALUES (?,?,?);";
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(query);

            ps.setString(1, userName);
            ps.setString(2, password);
            ps.setString(3, email);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        // När en ny användare skapats, får man... hen/hon... DEN en "egen" favorite-lista skapad.
        DBHandler.getInstance().createFavoriteDeviceList(userName);


    }

    public void createFavoriteDeviceList(String username) {

        for (int i = 97; i < 112; i++) {
            //Detta kommer skapa devices från 97 till 111 tillhörande inskickade username
            String query = "INSERT INTO user_has_device VALUES (?," + i + ", 0);";

            PreparedStatement ps = null;
            try {
                ps = conn.prepareStatement(query);
                ps.setString(1, username);

                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public int getUserFavorite(String userName, String deviceId){
        String query = "SELECT isFavourite from user_has_device where Device_id = ? and User_userName = ?;";
        PreparedStatement ps = null;
        int id = Integer.parseInt(deviceId);
        try {
            ps = conn.prepareStatement(query);

            ps.setInt(1,id);
            ps.setString(2,userName);

            ResultSet rs = ps.executeQuery();

            rs.next();

            int foundResult = rs.getInt("isFavourite");

            return foundResult;

        }catch (SQLException e){
            System.out.println(e);
        }

        return 0;
    }

    public void setUserFavorite(String value, String userName, String deviceId){
     String query = "UPDATE user_has_device SET isFavourite = ? WHERE (User_userName = ?) and (Device_id = ?);";

     PreparedStatement ps = null;

    try{
        ps = conn.prepareStatement(query);
        ps.setString(1,value);
        ps.setString(2,userName);
        ps.setString(3,deviceId);

        ps.executeUpdate();
    }catch (SQLException e){
        System.out.println(e);
    }

    }
}

