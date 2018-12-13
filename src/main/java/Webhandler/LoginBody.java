package Webhandler;

public class LoginBody {

    private String type, email,password,userName;


    public String getType() {
        return type;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }



    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "LoginBody{" +
                "type='" + type + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
