package org.example.view;

import org.example.managers.BaseUser;

import javax.swing.*;
import java.awt.*;

public class AccountPanel extends JPanel implements IPanel {

    BaseUser customer;

    public AccountPanel(JPanel mainCardPanel, CardLayout cardLayout, BaseUser customer) {
        this.customer = customer;
        initializePanel(mainCardPanel, cardLayout);
    }

    @Override
    public void initializePanel(JPanel mainCardPanel, CardLayout cardLayout) {

        refreshContent(mainCardPanel, cardLayout);
    }

    @Override
    public void refreshContent(JPanel mainCardPanel, CardLayout cardLayout) {
        this.removeAll();

        this.setLayout(new BorderLayout());

        JPanel eastContainer = new JPanel();
        JPanel westContainer = new JPanel();
        JPanel footer = new JPanel();

        eastContainer.setPreferredSize(new Dimension(150,100));
        westContainer.setPreferredSize(new Dimension(150,100));
        footer.setPreferredSize(new Dimension(100,150));
        eastContainer.setBackground(Color.green);
        westContainer.setBackground(Color.magenta);
        footer.setBackground(Color.YELLOW);

        this.add(eastContainer, BorderLayout.EAST);
        this.add(westContainer, BorderLayout.WEST);
        this.add(footer, BorderLayout.SOUTH);


        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.weightx = 1.0; // Yatayda boş alan paylaşımı
        gbc.weighty = 1.0; // Dikeyde boş alan paylaşımı
        gbc.fill = GridBagConstraints.BOTH; // Hem yatayda hem dikeyde genişle
        gbc.insets = new Insets(10, 10, 10, 10); // Boşlukları sıfırla

        Font font = new Font("Times New Roman",Font.PLAIN,25);


        // Sayfa içeriği

        JLabel infoLabel = new JLabel("Hesap Bilgileri");
        infoLabel.setFont(new Font("Times New Roman",Font.BOLD,35));

        gbc.gridx = 1; gbc.gridy = 0; gbc.gridwidth = 1;
        centerPanel.add(infoLabel, gbc);


        JLabel usernameLabel = new JLabel("Kullanıcı Adı:");
        usernameLabel.setFont(font);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        centerPanel.add(usernameLabel, gbc);

        JLabel username = new JLabel();
        username.setText(customer.getUsername());
        username.setFont(font);

        gbc.gridx = 1; gbc.gridy = 1; gbc.gridwidth = 1;
        centerPanel.add(username, gbc);


        JLabel passwordLabel = new JLabel("Şifre:");
        passwordLabel.setFont(font);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1;
        centerPanel.add(passwordLabel, gbc);

        JLabel password = new JLabel();
        password.setText(customer.getPassword());
        password.setFont(font);

        gbc.gridx = 1; gbc.gridy = 2; gbc.gridwidth = 1;
        centerPanel.add(password, gbc);


        JButton logOffBtn = new JButton("Çıkış Yap");
        logOffBtn.setFocusable(false);
        logOffBtn.addActionListener(e -> {
            int choice = JOptionPane.showOptionDialog(this,"Çıkış yapmak istediğinize emin misiniz?",
                    "Uyarı!",JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,null,null,0);

            if(choice == 0) {
                MainAppFrame.getInstance(customer).dispose();
                MainAppFrame.resetInstance();
                new LoginFrame();
            }
        });

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1;
        centerPanel.add(logOffBtn, gbc);

        JButton updateBtn = new JButton("Bilgileri Güncelle");
        updateBtn.setFocusable(false);
        updateBtn.addActionListener(e -> {

        });

        gbc.gridx = 1; gbc.gridy = 3; gbc.gridwidth = 1;
        centerPanel.add(updateBtn, gbc);

        JButton delAccBtn = new JButton("Hesabı Sil");
        delAccBtn.setFocusable(false);
        delAccBtn.addActionListener(e -> {
            int choice = JOptionPane.showOptionDialog(this,"Hesabınızı KALICI olarak silmek" +
                            " istediğinize emin misiniz?",
                    "Uyarı!",JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,null,null,0);

            if(choice == 0) {
                customer.delThisUser();

                MainAppFrame.getInstance(customer).dispose();
                MainAppFrame.resetInstance();
                new LoginFrame();
            }
        });

        gbc.gridx = 2; gbc.gridy = 3; gbc.gridwidth = 1;
        centerPanel.add(delAccBtn, gbc);



        this.add(centerPanel, BorderLayout.CENTER);

        this.revalidate();
        this.repaint();
    }
}
