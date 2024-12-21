package org.example.managers;

import org.example.utils.MysqlConnector;
import org.example.view.MainAppFrame;

import javax.swing.*;

public class Admin extends BaseUser{

    private String username;
    private String password;
    private final String role = "admin";

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getRole() {
        return role;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int getId() {
        MysqlConnector mysqlConnector = new MysqlConnector();
        return mysqlConnector.getUserId(getUsername());
    }

    @Override
    public JPanel getPanel() {
        return new MainAppFrame.MainAdminPanel(this);
    }

    @Override
    public void delThisUser() {
        MysqlConnector mysqlConnector = new MysqlConnector();
        mysqlConnector.delUser(getUsername());
    }

    // Yetkisiz erişim
    @Override
    public void createFolder() {

    }

    // Yetkisiz erişim
    @Override
    public void addToWorkPlace(String fileName) {

    }
}
