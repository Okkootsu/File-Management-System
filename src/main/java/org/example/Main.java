package org.example;

import org.example.managers.Log;
import org.example.view.LoginFrame;

import javax.swing.*;


public class Main {
    public static void main(String[] args) {
        Log log = Log.getInstance();
        log.logger.info("Uygulama açıldı");
        SwingUtilities.invokeLater(LoginFrame::new);
    }
}