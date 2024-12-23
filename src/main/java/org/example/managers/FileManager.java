package org.example.managers;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class FileManager {

    public static void fileCopy(Path source, Path target) {
        try {
            Files.walkFileTree(source, new SimpleFileVisitor<>(){
               @Override
               public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    Path targetPath = target.resolve(source.relativize(dir));

                    if ( !(Files.exists(targetPath)) ) {
                        Files.createDirectories(targetPath);
                    }
                    return FileVisitResult.CONTINUE;
               }

               @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Path targetPath = target.resolve(source.relativize(file));

                    Files.copy(file, targetPath, StandardCopyOption.REPLACE_EXISTING);
                    return FileVisitResult.CONTINUE;
               }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Hata Kodu:"+e.getMessage(),
                    "Bir Hata Oluştu (fileCopy)",JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void openFile() {

    }
}
