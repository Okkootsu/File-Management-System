package org.example.view;

import com.mysql.cj.log.Log;
import org.example.managers.BaseUser;
import org.example.managers.Customer;
import org.example.managers.Login;

import javax.swing.*;
import java.awt.*;

public class MainLoginPanel extends JPanel implements IPanel {

    MainLoginPanel(JPanel mainCardPanel, CardLayout cardLayout) {
        initializePanel(mainCardPanel, cardLayout);
    }

    @Override
    public void initializePanel(JPanel mainCardPanel, CardLayout cardLayout) {
        this.setBackground(new Color(232, 232, 232));
        refreshContent(mainCardPanel, cardLayout);
    }

    @Override
    public void refreshContent(JPanel mainCardPanel, CardLayout cardLayout) {
        this.removeAll();

        this.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.weightx = 1.0; // Yatayda boş alan paylaşımı
        gbc.weighty = 1.0; // Dikeyde boş alan paylaşımı
        gbc.fill = GridBagConstraints.BOTH; // Hem yatayda hem dikeyde genişle
        gbc.insets = new Insets(10, 10, 10, 10); // Boşlukları sıfırla



        JLabel welcomeLabel = new JLabel("Hoş Geldiniz!");
        welcomeLabel.setFont(new Font("Times New Roman",Font.PLAIN,30));

        gbc.gridx = 0; gbc.gridy= 0; gbc.gridwidth = 2;
        this.add(welcomeLabel, gbc);



        JLabel usernameLabel = new JLabel("Kullanıcı Adı:");
        usernameLabel.setFont(new Font("Times New Roman",Font.PLAIN,15));

        gbc.gridx = 0; gbc.gridy= 1; gbc.gridwidth = 1;
        this.add(usernameLabel, gbc);

        JTextField usernameTxtField = new JTextField();

        gbc.gridx = 1; gbc.gridy= 1; gbc.gridwidth = 1;
        this.add(usernameTxtField, gbc);



        JLabel passwordLabel = new JLabel("Şifre:");
        passwordLabel.setFont(new Font("Times New Roman",Font.PLAIN,15));

        gbc.gridx = 0; gbc.gridy= 2; gbc.gridwidth = 1;
        this.add(passwordLabel, gbc);

        JTextField passwordTxtField = new JTextField();

        gbc.gridx = 1; gbc.gridy= 2; gbc.gridwidth = 1;
        this.add(passwordTxtField, gbc);



        JButton registerBtn = new JButton("Kayıt Ol");
        registerBtn.setFocusable(false);
        registerBtn.addActionListener(e -> cardLayout.show(mainCardPanel, "Register"));

        gbc.gridx = 0; gbc.gridy= 3; gbc.gridwidth = 1;
        this.add(registerBtn, gbc);


        JButton loginBtn = new JButton("Giriş Yap");
        loginBtn.setFocusable(false);
        loginBtn.addActionListener(e -> {
            String username = usernameTxtField.getText();
            String password = passwordTxtField.getText();

            new Login(username, password);
        });

        gbc.gridx = 1; gbc.gridy= 3; gbc.gridwidth = 1;
        this.add(loginBtn, gbc);



        this.revalidate();
        this.repaint();
    }
}
