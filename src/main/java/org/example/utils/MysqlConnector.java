package org.example.utils;


import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MysqlConnector {

    private final String sqlConnection = "jdbc:mysql://127.0.0.1:3306/foldersystemdb";
    private final String sqlUsername = "root";
    private final String sqlPassword = "volkancomp159357258A";

    public String getSqlConnection() {
        return sqlConnection;
    }

    public String getSqlUsername() {
        return sqlUsername;
    }

    public String getSqlPassword() {
        return sqlPassword;
    }

    public void addUser(String username, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(getSqlConnection(),getSqlUsername(),getSqlPassword());

            Statement statement = connection.createStatement();

            // Mysql'de çalışmasını istediğimiz kodu buraya yazıyoruz
            String query = "INSERT INTO users(user_name, password, role, team)" +
                    "VALUES('"+username+"' , '"+password+"' , 'customer' , 'None' )";

            statement.execute(query);

        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null,"Hata Kodu: "+exception.getMessage(),
                    "Bir Hata Oluştu",JOptionPane.ERROR_MESSAGE);
        }
    }

    // Tek kullanıcının info'sunu döndürür
    public ResultSet getInfo(String username) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(getSqlConnection(),getSqlUsername(),getSqlPassword());

            Statement statement = connection.createStatement();

            // Mysql'de çalışmasını istediğimiz kodu buraya yazıyoruz
            String query = "SELECT * FROM users " +
                    "WHERE user_name = '"+username+"' ";

            return statement.executeQuery(query);

        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Hata Kodu:"+e.getMessage(),
                    "Bir Hata Oluştu (getInfo)",JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public int getUserId(String username) {
        try {
            ResultSet resultSet = getInfo(username);

            if (resultSet.next()) {
                return resultSet.getInt("id");
            }

        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null,"Hata Kodu:"+exception.getMessage(),
                    "Bir Hata Oluştu (getInfo)",JOptionPane.ERROR_MESSAGE);
        }

        return -1;
    }

    public void delUser(String username) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(getSqlConnection(),getSqlUsername(),getSqlPassword());

            Statement statement = connection.createStatement();

            // Mysql'de çalışmasını istediğimiz kodu buraya yazıyoruz
            String query = "DELETE FROM users " +
                    "WHERE user_name = '"+username+"' ";

            statement.execute(query);

        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Hata Kodu:"+e.getMessage(),
                    "Bir Hata Oluştu (delThisUser)",JOptionPane.ERROR_MESSAGE);
        }
    }

    // Tüm normal kullanıcıları döndür
    public ResultSet getUsers() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(getSqlConnection(),getSqlUsername(),getSqlPassword());

            Statement statement = connection.createStatement();

            // Mysql'de çalışmasını istediğimiz kodu buraya yazıyoruz
            String query = "SELECT * FROM users " +
                    "WHERE role = 'customer' ";


            return statement.executeQuery(query);

        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null,"Hata Kodu:"+exception.getMessage(),
                    "Bir Hata Oluştu (getUsers)",JOptionPane.ERROR_MESSAGE);

            return null;
        }
    }

    public String getRole(String username) {
        try {
            ResultSet resultSet = getInfo(username);

            resultSet.next();

            return resultSet.getString("role");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Hata Kodu:"+e.getMessage(),
                    "Bir Hata Oluştu (getUsers)",JOptionPane.ERROR_MESSAGE);

            return "";
        }
    }

    public ResultSet getAllTeams() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(getSqlConnection(),getSqlUsername(),getSqlPassword());

            Statement statement = connection.createStatement();

            // Mysql'de çalışmasını istediğimiz kodu buraya yazıyoruz
            String query = "SELECT team FROM users " ;

            return statement.executeQuery(query);

        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null,"Hata Kodu:"+exception.getMessage(),
                    "Bir Hata Oluştu (getUsers)",JOptionPane.ERROR_MESSAGE);

            return null;
        }
    }

    public void setTeamName(String username ,String teamName){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(getSqlConnection(),getSqlUsername(),getSqlPassword());

            Statement statement = connection.createStatement();

            // Mysql'de çalışmasını istediğimiz kodu buraya yazıyoruz
            String query = "UPDATE users " +
                    "SET team = '"+teamName+"' " +
                    "WHERE user_name = '"+username+"' " ;

            statement.execute(query);

        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null,"Hata Kodu:"+exception.getMessage(),
                    "Bir Hata Oluştu (setTeamName)",JOptionPane.ERROR_MESSAGE);
        }
    }

    public void sendInvite(String inviter, String invited) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(getSqlConnection(),getSqlUsername(),getSqlPassword());

            Statement statement = connection.createStatement();

            // Mysql'de çalışmasını istediğimiz kodu buraya yazıyoruz
            String query = "UPDATE users " +
                    "SET invite_from = '"+inviter+"' " +
                    "WHERE user_name = '"+invited+"' " ;

            statement.execute(query);

        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null,"Hata Kodu:"+exception.getMessage(),
                    "Bir Hata Oluştu (sendInvite)",JOptionPane.ERROR_MESSAGE);
        }
    }

}
