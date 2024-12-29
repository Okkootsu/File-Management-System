package org.example.managers;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.example.managers.FileManager.fileCopy;

public class CopyProcess extends Thread {

    private Customer customer;

    public CopyProcess(Customer customer){
        this.customer = customer;
    }

    @Override
    public void run() {
        Path source = Paths.get("src/SystemFolders/folders/OriginalFolders/" + customer.getUsername() + "/");
        Path target = Paths.get("src/SystemFolders/folders/SavedFolders/" + customer.getUsername() + "/");

        fileCopy(source, target, customer.getMaxFileCount());
    }
}
