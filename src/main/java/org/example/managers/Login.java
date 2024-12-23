package org.example.managers;

import org.example.utils.MysqlConnector;
import org.example.view.LoginFrame;
import org.example.view.MainAppFrame;

import javax.swing.*;
import java.sql.ResultSet;

import static org.example.utils.PasswordHashing.verifyPassword;

public class Login {

    public Login (String username, String password) {
        if(isIncorrect(username, password)){
            JOptionPane.showMessageDialog(null,"Girilen bilgiler hatalı \n" +
                            "Lütfen tekrar deneyiniz",
                    "Uyarı",JOptionPane.INFORMATION_MESSAGE);

        }
        else {
            JOptionPane.showMessageDialog(null,"Başarıyla giriş yapıldı!" ,
                    "Giriş Yapıldı",JOptionPane.PLAIN_MESSAGE);

            MysqlConnector mysqlConnector = new MysqlConnector();
            String role = mysqlConnector.getRole(username);

            if (role.equals("admin")) {
                BaseUser admin = new Admin(username, password);

                LoginFrame.getInstance().dispose();
                LoginFrame.resetInstance();

                new MainAppFrame(admin);

            } else {
                BaseUser user = new Customer(username, password);

                LoginFrame.getInstance().dispose();
                LoginFrame.resetInstance();

                new MainAppFrame(user);
            }

        }
    }

    // Kayıt olma için kontroller

    public boolean isIncorrect(String username, String password) {

        if(username.isEmpty()){
            return true;
        }

        if(password.isEmpty()){
            return true;
        }

        if(username.length() > 45){
            return true;
        }

        if(password.length() > 45){
            return true;
        }

        // Şifre Kontrol
        try {
            MysqlConnector mysqlConnector = new MysqlConnector();
            ResultSet resultSet = mysqlConnector.getInfo(username);

            if(resultSet.next()){
                String hPassword = resultSet.getString("password");

                return !(verifyPassword(password, hPassword));
            } else {
                resultSet.close();
                return true;
            }

        }catch (Exception e){

            return true;
        }
    }
}
