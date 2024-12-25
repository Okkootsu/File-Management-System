package org.example.managers;

import org.example.utils.MysqlConnector;
import org.example.view.MainAppFrame;

import javax.swing.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.ResultSet;

public class Customer extends BaseUser{

    private String username;
    private String password;
    private final String role = "customer";
    private String team;

    public Customer (String username, String password) {
        setUsername(username);
        setPassword(password);
        setTeam(getTeamFromDB());
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

    private String getTeamFromDB() {
        MysqlConnector mysqlConnector = new MysqlConnector();
        ResultSet resultSet = mysqlConnector.getInfo(getUsername());

        try {
            resultSet.next();
            return resultSet.getString("team");
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null,"Hata Kodu:"+exception.getMessage(),
                    "Bir Hata Oluştu (getTeamFromDB)",JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public String getInv() {
        MysqlConnector mysqlConnector = new MysqlConnector();

        try {
            ResultSet resultSet = mysqlConnector.getInfo(getUsername());
            resultSet.next();

            return resultSet.getString("invite_from");

        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null,"Hata Kodu:"+exception.getMessage(),
                    "Bir Hata Oluştu (getTeamFromDB)",JOptionPane.ERROR_MESSAGE);
            return "";
        }
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

    public void sendInv(String sendTo) {
        MysqlConnector mysqlConnector = new MysqlConnector();
        mysqlConnector.sendInvite(getTeam(), sendTo);
    }

    @Override
    public void addToWorkPlace(File sourceFile) { // Dosyanın adı ile ilgili sıkıntı var
        try {
            Path source = sourceFile.toPath();
            String originalRoad = "src/SystemFolders/folders/OriginalFolders/" + getUsername() + "/";
            Path target = Paths.get(originalRoad + sourceFile.getName());
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);

            JOptionPane.showMessageDialog(null,"Dosyanız sisteme aktarıldı",
                    "İşlem başarılı",JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) { // sıkıntılı kısım
            JOptionPane.showMessageDialog(null,"Hata Kodu:"+e.getMessage(),
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

    // Yetkisiz erişim
    @Override
    public ResultSet getUsers() {
        return null;
    }
}
