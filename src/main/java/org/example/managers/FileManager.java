package org.example.managers;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class FileManager {

    /* Çalışmıyor gibi gözüküyor
    public static boolean isFileLocked(File file) {
        try (FileChannel channel = FileChannel.open(file.toPath(), StandardOpenOption.WRITE)) {
            return false; // Dosya açık değil
        } catch (Exception e) {
            return true; // Dosya açık olduğu için kilitli
        }
    } */

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

    public static void fileOpen(File selectedFile) {
        if(Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();

            try {
                desktop.open(selectedFile);
            } catch (Exception e){
                JOptionPane.showMessageDialog(null,"Hata Kodu:"+e.getMessage(),
                        "Bir Hata Oluştu (fileOpen)",JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null,"Bu özellik desteklenmiyor",
                    "Hata!(fileOpen)",JOptionPane.ERROR_MESSAGE);
        }
    }
}
