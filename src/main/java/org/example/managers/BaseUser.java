package org.example.managers;

import javax.swing.*;

public abstract class BaseUser {

    // Genel kullanıcı işlemleri
    public abstract String getUsername();
    public abstract String getPassword();
    public abstract String getRole();
    public abstract void setUsername(String username);
    public abstract void setPassword(String password);
    public abstract int getId();
    public abstract void delThisUser();

    // Dosya işlemleri
    public abstract void createFolder();
    public abstract void addToWorkPlace(String fileName);

    // Uygulama ekranı çağırma
    public abstract JPanel getPanel();
}
