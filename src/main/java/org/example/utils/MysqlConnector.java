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
            String query = "INSERT INTO users(user_name, password, role)" +
                    "VALUES('"+username+"' , '"+password+"' , 'customer' )";

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

    public void changeName(String oldName, String newName) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(getSqlConnection(),getSqlUsername(),getSqlPassword());

            Statement statement = connection.createStatement();

            // Mysql'de çalışmasını istediğimiz kodu buraya yazıyoruz
            String query = "UPDATE users " +
                    "SET user_name = '"+newName+"' " +
                    "WHERE user_name = '"+oldName+"' ";

            statement.execute(query);

        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null,"Hata Kodu: "+exception.getMessage(),
                    "Bir Hata Oluştu",JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateFriends(String oldName, String newName) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(getSqlConnection(),getSqlUsername(),getSqlPassword());

            Statement statement = connection.createStatement();

            // Mysql'de çalışmasını istediğimiz kodu buraya yazıyoruz
            String query = "UPDATE friend_requests " +
                    "SET user_name = CASE WHEN user_name = '"+oldName+"' THEN '"+newName+"' ELSE user_name END, " +
                    "invite_from = CASE WHEN invite_from = '"+oldName+"' THEN '"+newName+"' ELSE invite_from END ";

            statement.execute(query);

            query = "UPDATE friends " +
                    "SET user_name = CASE WHEN user_name = '"+oldName+"' THEN '"+newName+"' ELSE user_name END, " +
                    "friend = CASE WHEN friend = '"+oldName+"' THEN '"+newName+"' ELSE friend END ";

            statement.execute(query);

        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null,"Hata Kodu: "+exception.getMessage(),
                    "Bir Hata Oluştu(updateFriends)",JOptionPane.ERROR_MESSAGE);
        }
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

    public void delFromFriends(String username) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(getSqlConnection(),getSqlUsername(),getSqlPassword());

            Statement statement = connection.createStatement();

            // Mysql'de çalışmasını istediğimiz kodu buraya yazıyoruz
            // Friend request'te sil
            String query = "DELETE FROM friend_requests " +
                    "WHERE user_name = '"+username+"' " +
                    "OR invite_from = '"+username+"' ";

            statement.execute(query);

            query = "DELETE FROM friends " +
                    "WHERE user_name = '"+username+"' " +
                    "OR friend = '"+username+"' ";

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

//    public ResultSet getAllTeams() {
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//
//            Connection connection = DriverManager.getConnection(getSqlConnection(),getSqlUsername(),getSqlPassword());
//
//            Statement statement = connection.createStatement();
//
//            // Mysql'de çalışmasını istediğimiz kodu buraya yazıyoruz
//            String query = "SELECT team FROM users " ;
//
//            return statement.executeQuery(query);
//
//        } catch (Exception exception) {
//            JOptionPane.showMessageDialog(null,"Hata Kodu:"+exception.getMessage(),
//                    "Bir Hata Oluştu (getUsers)",JOptionPane.ERROR_MESSAGE);
//
//            return null;
//        }
//    }

//    public void setTeamName(String username ,String teamName){
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//
//            Connection connection = DriverManager.getConnection(getSqlConnection(),getSqlUsername(),getSqlPassword());
//
//            Statement statement = connection.createStatement();
//
//            // Mysql'de çalışmasını istediğimiz kodu buraya yazıyoruz
//            String query = "UPDATE users " +
//                    "SET team = '"+teamName+"' " +
//                    "WHERE user_name = '"+username+"' " ;
//
//            statement.execute(query);
//
//        } catch (Exception exception) {
//            JOptionPane.showMessageDialog(null,"Hata Kodu:"+exception.getMessage(),
//                    "Bir Hata Oluştu (setTeamName)",JOptionPane.ERROR_MESSAGE);
//        }
//    }

    public void sendInvite(String inviter, String invited) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(getSqlConnection(),getSqlUsername(),getSqlPassword());

            Statement statement = connection.createStatement();

            // Mysql'de çalışmasını istediğimiz kodu buraya yazıyoruz
            String query = "INSERT INTO friend_requests(user_name, invite_from)" +
                    "VALUES('"+invited+"' , '"+inviter+"' )";

            statement.execute(query);

        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null,"Hata Kodu:"+exception.getMessage(),
                    "Bir Hata Oluştu (sendInvite)",JOptionPane.ERROR_MESSAGE);
        }
    }

    public void addFriend(String username, String friendName, int inviteId) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(getSqlConnection(),getSqlUsername(),getSqlPassword());

            Statement statement = connection.createStatement();

            // Mysql'de çalışmasını istediğimiz kodu buraya yazıyoruz
            String query = "INSERT INTO friends(user_name, friend) " +
                    "VALUES('"+username+"' , '"+friendName+"' )";

            statement.execute(query);

            // Daveti gönderen içinde aynı şeyi yapıyoruz
            query = "INSERT INTO friends(user_name, friend) " +
                    "VALUES('"+friendName+"' , '"+username+"' )";

            statement.execute(query);

            // Ve o daveti siliyoruz
            query = "DELETE FROM friend_requests " +
                    "WHERE inv_id = "+inviteId;

            statement.execute(query);

        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null,"Hata Kodu:"+exception.getMessage(),
                    "Bir Hata Oluştu (addFriend)",JOptionPane.ERROR_MESSAGE);
        }
    }

    public ResultSet getFriendRequests(String from) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(getSqlConnection(),getSqlUsername(),getSqlPassword());

            Statement statement = connection.createStatement();

            // Mysql'de çalışmasını istediğimiz kodu buraya yazıyoruz
            String query = "SELECT * FROM friend_requests " +
                    "WHERE user_name = '"+from+"' ";


            return statement.executeQuery(query);

        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null,"Hata Kodu:"+exception.getMessage(),
                    "Bir Hata Oluştu (getFriendRequests)",JOptionPane.ERROR_MESSAGE);

            return null;
        }
    }

    public ResultSet getFriends(String from) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(getSqlConnection(),getSqlUsername(),getSqlPassword());

            Statement statement = connection.createStatement();

            // Mysql'de çalışmasını istediğimiz kodu buraya yazıyoruz
            String query = "SELECT friend FROM friends " +
                    "WHERE user_name = '"+from+"' ";


            return statement.executeQuery(query);

        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null,"Hata Kodu:"+exception.getMessage(),
                    "Bir Hata Oluştu (getFriends)",JOptionPane.ERROR_MESSAGE);

            return null;
        }
    }

    public void createPasswordRequest(String username, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(getSqlConnection(),getSqlUsername(),getSqlPassword());

            Statement statement = connection.createStatement();

            // Mysql'de çalışmasını istediğimiz kodu buraya yazıyoruz
            String query = "INSERT INTO password_requests(user_name, new_password)" +
                    "VALUES('"+username+"' , '"+password+"' )";

            statement.execute(query);

        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null,"Hata Kodu:"+exception.getMessage(),
                    "Bir Hata Oluştu (createPasswordRequest)",JOptionPane.ERROR_MESSAGE);
        }
    }

    public ResultSet getPasswordRequests(String username) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(getSqlConnection(),getSqlUsername(),getSqlPassword());

            Statement statement = connection.createStatement();

            // Mysql'de çalışmasını istediğimiz kodu buraya yazıyoruz
            String query = " SELECT * FROM password_requests " +
                    " WHERE user_name = '"+username+"' ";

            return statement.executeQuery(query);

        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null,"Hata Kodu:"+exception.getMessage(),
                    "Bir Hata Oluştu (createPasswordRequest)",JOptionPane.ERROR_MESSAGE);

            return null;
        }
    }

    public void updatePassword(String username, String newPassword) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(getSqlConnection(),getSqlUsername(),getSqlPassword());

            Statement statement = connection.createStatement();

            // Mysql'de çalışmasını istediğimiz kodu buraya yazıyoruz
            String query = "UPDATE users " +
                    "SET password = '"+newPassword+"' " +
                    "WHERE user_name = '"+username+"' ";

            statement.execute(query);

        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null,"Hata Kodu:"+exception.getMessage(),
                    "Bir Hata Oluştu (createPasswordRequest)",JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deletePasswordRequest(String username) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(getSqlConnection(),getSqlUsername(),getSqlPassword());

            Statement statement = connection.createStatement();

            // Mysql'de çalışmasını istediğimiz kodu buraya yazıyoruz
            String query = "DELETE FROM password_requests " +
                    "WHERE user_name = '"+username+"' ";

            statement.execute(query);

        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null,"Hata Kodu:"+exception.getMessage(),
                    "Bir Hata Oluştu (createPasswordRequest)",JOptionPane.ERROR_MESSAGE);
        }
    }

}
