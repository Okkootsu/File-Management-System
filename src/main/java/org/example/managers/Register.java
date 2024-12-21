package org.example.managers;

import org.example.utils.MysqlConnector;


import javax.swing.*;
import java.sql.ResultSet;

import static org.example.utils.PasswordHashing.hashPassword;

public class Register {

    public Register (String username, String password) {

        if (isIncorrect(username, password)) {
            JOptionPane.showMessageDialog(null,"Girilen bilgiler hatalı \n " +
                            "Lütfen tekrar deneyiniz",
                    "Bir Hata Oluştu",JOptionPane.ERROR_MESSAGE);
        } else {
            MysqlConnector mysqlConnector = new MysqlConnector();
            mysqlConnector.addUser(username, hashPassword(password));

            BaseUser customer = new Customer(username, password);
            customer.createFolder();

            JOptionPane.showMessageDialog(null,"Kullanıcı sisteme eklendi",
                    "Kullanıcı Oluşturuldu",JOptionPane.PLAIN_MESSAGE);
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

        // Username Kontrol
        try {
            MysqlConnector mysqlConnector = new MysqlConnector();
            ResultSet resultSet = mysqlConnector.getInfo(username);

            if(resultSet.next()){
                if( resultSet.getString("user_name").equals(username) ){

                    resultSet.close();

                    return true;
                }
            }

            resultSet.close();

        }catch (Exception e){

            return true;
        }

        return false;
    }
}
