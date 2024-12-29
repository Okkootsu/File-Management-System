package org.example.managers;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.example.managers.FileManager.fileCopy;

public class TeamCopyProcess extends Thread {

    private Customer customer;
    private List<String> friends;

    public TeamCopyProcess(Customer customer, List<String> friends) {
        this.customer = customer;
        this.friends = friends;
    }

    @Override
    public void run() {
        Path source = Paths.get("src/SystemFolders/TeamFolders/" + customer.getUsername() + "/");

        for(int i = 0; i < friends.size(); i++) {
            Path target = Paths.get("src/SystemFolders/folders/OriginalFolders/" + friends.get(i) + "/");

            fileCopy(source, target, customer.getMaxFileCount());
        }

    }
}
