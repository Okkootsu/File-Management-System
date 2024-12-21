package org.example.managers;

import org.example.utils.MysqlConnector;
import org.example.view.MainAppFrame;

import javax.swing.*;
import java.io.File;

public class Customer extends BaseUser{

    private String username;
    private String password;
    private final String role = "customer";
    private String team = "None";

    public Customer (String username, String password) {
        setUsername(username);
        setPassword(password);
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getTeam() {
        return team;
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

    public void setTeam(String team) {
        this.team = team;
    }

    @Override
    public int getId() {
        MysqlConnector mysqlConnector = new MysqlConnector();
        return mysqlConnector.getUserId(getUsername());
    }

    @Override
    public JPanel getPanel() {
        return new MainAppFrame.MainCustomerPanel(this);
    }

    @Override
    public void delThisUser() {
        MysqlConnector mysqlConnector = new MysqlConnector();
        mysqlConnector.delUser(getUsername());
    }

    @Override
    public void addToWorkPlace(String fileName) {
        try {
            File addedFile = new File("src/SystemFolders/folders/OriginalFolders/" + getUsername() + "/" + fileName);

            if (addedFile.createNewFile()) {

                JOptionPane.showMessageDialog(null,"Dosya sisteme yüklendi",
                        "İşlem Başarılı",JOptionPane.INFORMATION_MESSAGE);

            } else {
                JOptionPane.showMessageDialog(null,"Bir şeyler ters gitti",
                        "Bir Hata Oluştu (addToWorkPlace)",JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null,"Hata Kodu: " + exception.getMessage(),
                    "Bir Hata Oluştu (addToWorkPlace)",JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void createFolder() {
        try {

            // Orijinal Çalışma Alanı
            File original = new File("src/SystemFolders/folders/OriginalFolders/" + getUsername());

            if (original.mkdir()) {

            } else {
                JOptionPane.showMessageDialog(null,"Bir şeyler ters gitti",
                        "Bir Hata Oluştu (createFolder)",JOptionPane.ERROR_MESSAGE);
            }



            // Yedek Çalışma Alanı
            File saved  = new File("src/SystemFolders/folders/SavedFolders/" + getUsername());

            if (saved.mkdir()) {

            } else {
                JOptionPane.showMessageDialog(null,"Bir şeyler ters gitti",
                        "Bir Hata Oluştu (createFolder)",JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Hata Kodu:"+e.getMessage(),
                    "Bir Hata Oluştu (createFolder)",JOptionPane.ERROR_MESSAGE);
        }
    }
}
