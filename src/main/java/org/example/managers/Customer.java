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

import static org.example.managers.FileManager.fileDelete;
import static org.example.managers.FileManager.fileRename;

public class Customer extends BaseUser{

    private String username;
    private String password;
    private final String role = "customer";


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
        return new MainAppFrame.MainCustomerPanel(this);
    }

    public int getMaxFileCount() {
        MysqlConnector mysqlConnector = new MysqlConnector();
        ResultSet resultSet = mysqlConnector.getInfo(getUsername());
        try {
            resultSet.next();
            return resultSet.getInt("max_files");
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public void delThisUser() {
        Path deleteOriginalFiles = Paths.get("src/SystemFolders/folders/OriginalFolders/" + getUsername());
        Path deleteSavedFiles = Paths.get("src/SystemFolders/folders/SavedFolders/" + getUsername());
        Path deleteTeamFiles = Paths.get("src/SystemFolders/TeamFolders/" + getUsername());
        fileDelete(deleteOriginalFiles);
        fileDelete(deleteSavedFiles);
        fileDelete(deleteTeamFiles);

        MysqlConnector mysqlConnector = new MysqlConnector();
        mysqlConnector.delUser(getUsername());
        mysqlConnector.delFromFriends(getUsername());
    }

    @Override
    public void changeUsername(String newName) {
        MysqlConnector mysqlConnector = new MysqlConnector();
        mysqlConnector.changeName(getUsername(), newName);
    }

    @Override
    public void renameTheFolders(String newName) {
        File oldFile = new File("src/SystemFolders/folders/OriginalFolders/" + getUsername());
        File newFile = new File("src/SystemFolders/folders/OriginalFolders/" + newName);

        fileRename(oldFile, newFile);   // Original dizinini değiştir

        oldFile = new File("src/SystemFolders/folders/SavedFolders/" + getUsername());
        newFile = new File("src/SystemFolders/folders/SavedFolders/" + newName);

        fileRename(oldFile, newFile);   // Saved dizinini değiştir

        oldFile = new File("src/SystemFolders/TeamFolders/" + getUsername());
        newFile = new File("src/SystemFolders/TeamFolders/" + newName);

        fileRename(oldFile, newFile);   // Team dizinini değiştir
    }


    @Override
    public void renameTeamFolders(String newName) {

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

    public void sendInv(String invited) {
        MysqlConnector mysqlConnector = new MysqlConnector();
        mysqlConnector.sendInvite(getUsername(), invited);
    }

    public void addFriend(String friendName, int inviteId) {
        MysqlConnector mysqlConnector = new MysqlConnector();
        mysqlConnector.addFriend(getUsername(), friendName, inviteId);
    }


    public void createTeamFolder() {
        try {

            // Orijinal Çalışma Alanı
            File original = new File("src/SystemFolders/TeamFolders/" + getUsername());

            if (original.mkdir()) {

            } else {
                JOptionPane.showMessageDialog(null,"Bir şeyler ters gitti",
                        "Bir Hata Oluştu (createTeamFolder)",JOptionPane.ERROR_MESSAGE);
            }


        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Hata Kodu:"+e.getMessage(),
                    "Bir Hata Oluştu (createTeamFolder)",JOptionPane.ERROR_MESSAGE);
        }
    }

    public void addToTeamWorkPlace(File sourceFile) {
        try {
            Path source = sourceFile.toPath();
            String originalRoad = "src/SystemFolders/TeamFolders/" + getUsername() + "/";
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
    public void createPassRequest(String newPassword) {
        MysqlConnector mysqlConnector = new MysqlConnector();
        mysqlConnector.createPasswordRequest(getUsername(), newPassword);
    }


    // Yetkisiz erişim
    @Override
    public ResultSet getUsers() {
        return null;
    }
}
