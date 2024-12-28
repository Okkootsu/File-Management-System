package org.example;

import org.example.managers.Log;
import org.example.view.LoginFrame;

import javax.swing.*;


// Proje adı -> Dosya Depolama/Yedekleme Sistemi, Yapılacaklar;
// Kullanıcı giriş sistemi +
// Kullanıcı işlemleri
// Depolama ve Yedekleme +
// Dosya İşlemleri +
// Admin işlemleri
// Loglama +
// Anormal davranışları gözlemleme ve durdurma
// Kullanıcı şifresini şifreleme +
// GUI
// Takımlaşma +


public class Main {
    public static void main(String[] args) {
        Log log = Log.getInstance();
        log.logger.info("Uygulama açıldı");
        SwingUtilities.invokeLater(LoginFrame::new);
    }
}