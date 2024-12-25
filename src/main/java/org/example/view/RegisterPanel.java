package org.example.view;



import org.example.managers.Register;

import javax.swing.*;
import java.awt.*;

public class RegisterPanel extends JPanel implements IPanel{
    RegisterPanel (JPanel mainCardPanel, CardLayout cardLayout) {
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



        JLabel usernameLabel = new JLabel("Kullanıcı Adı:");
        usernameLabel.setFont(new Font("Times New Roman",Font.PLAIN,15));

        gbc.gridx = 0; gbc.gridy= 0; gbc.gridwidth = 1;
        this.add(usernameLabel, gbc);

        JTextField usernameTxtField = new JTextField();

        gbc.gridx = 1; gbc.gridy= 0; gbc.gridwidth = 1;
        this.add(usernameTxtField, gbc);



        JLabel passwordLabel = new JLabel("Şifre:");
        passwordLabel.setFont(new Font("Times New Roman",Font.PLAIN,15));

        gbc.gridx = 0; gbc.gridy= 1; gbc.gridwidth = 1;
        this.add(passwordLabel, gbc);

        JTextField passwordTxtField = new JTextField();

        gbc.gridx = 1; gbc.gridy= 1; gbc.gridwidth = 1;
        this.add(passwordTxtField, gbc);



        JButton loginBtn = new JButton("Geri Dön");
        loginBtn.setFocusable(false);
        loginBtn.addActionListener(e -> cardLayout.show(mainCardPanel, "Login"));

        gbc.gridx = 0; gbc.gridy= 2; gbc.gridwidth = 1;
        this.add(loginBtn, gbc);


        JButton registerBtn = new JButton("Kullanıcı Oluştur");
        registerBtn.setFocusable(false);
        registerBtn.addActionListener(e -> {
            String username = usernameTxtField.getText();
            String password = passwordTxtField.getText();

            new Register(username, password);
        });

        gbc.gridx = 1; gbc.gridy= 2; gbc.gridwidth = 1;
        this.add(registerBtn, gbc);



        this.revalidate();
        this.repaint();
    }
}
