package org.example.managers;

import javax.swing.*;
import java.io.File;
import java.sql.ResultSet;

public abstract class BaseUser {

    // Genel kullanıcı işlemleri
    public abstract String getUsername();
    public abstract String getPassword();
    public abstract String getRole();
    public abstract void setUsername(String username);
    public abstract void setPassword(String password);
    public abstract int getId();
    public abstract void delThisUser();
    public abstract void changeUsername(String newName);
    public abstract void renameTheFolders(String newName);
    public abstract void renameTeamFolders(String newName);
    public abstract void createPassRequest(String newPassword);

    // Dosya işlemleri
    public abstract void createFolder();
    public abstract void addToWorkPlace(File sourceFile);

    // Uygulama ekranı çağırma
    public abstract JPanel getPanel();

    // Admin işlemleri
    public abstract ResultSet getUsers();
}
