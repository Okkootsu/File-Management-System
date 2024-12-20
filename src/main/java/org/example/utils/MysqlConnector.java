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
            String query = "INSERT INTO users(user_name, password)" +
                    "VALUES('"+username+"' , '"+password+"')";

            statement.execute(query);

        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null,"Hata Kodu: "+exception.getMessage(),
                    "Bir Hata Oluştu",JOptionPane.ERROR_MESSAGE);
        }
    }

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
}
