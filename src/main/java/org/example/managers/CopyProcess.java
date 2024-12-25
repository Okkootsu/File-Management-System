package org.example.managers;

import javax.swing.*;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.example.managers.FileManager.fileCopy;

public class CopyProcess extends Thread {

    private BaseUser customer;

    public CopyProcess(BaseUser customer){
        this.customer = customer;
    }

    @Override
    public void run() {
        Path source = Paths.get("src/SystemFolders/folders/OriginalFolders/" + customer.getUsername() + "/");
        Path target = Paths.get("src/SystemFolders/folders/SavedFolders/" + customer.getUsername() + "/");

        fileCopy(source, target);

//        try {
//            Thread.sleep(3000);
//        } catch (Exception e){
//            JOptionPane.showMessageDialog(null,"Hata kodu: "+e.getMessage(),
//                    "Beklemede sorun oluştu",JOptionPane.ERROR_MESSAGE);
//        }
    }
}
