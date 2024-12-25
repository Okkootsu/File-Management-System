package org.example.view;

import org.example.managers.Customer;
import org.example.utils.MysqlConnector;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

public class TeamPanel extends JPanel implements IPanel {

    private Customer customer;

    public TeamPanel(JPanel mainCardPanel, CardLayout cardLayout, Customer customer) {
        this.customer = customer;
        initializePanel(mainCardPanel, cardLayout);
    }

    @Override
    public void initializePanel(JPanel mainCardPanel, CardLayout cardLayout) {
        this.setBackground(new Color(155, 164, 180));
        refreshContent(mainCardPanel, cardLayout);
    }

    @Override
    public void refreshContent(JPanel mainCardPanel, CardLayout cardLayout) {
        this.removeAll();

        this.setLayout(new BorderLayout());

        // Üst kısım
        JPanel header = new JPanel();
        header.setBackground(Color.red);
        header.setLayout(new FlowLayout(FlowLayout.CENTER));
        header.setOpaque(false);

        header.setPreferredSize(new Dimension(100,50));

        JLabel informationLabel = new JLabel(customer.getTeam()+" Üyeleri");
        informationLabel.setFont(new Font("Arial",Font.BOLD,35));
        header.add(informationLabel);

        this.add(header, BorderLayout.NORTH);

        // Orta kısım
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());

        JPanel rightPanel = new JPanel();
        JPanel leftPanel = new JPanel();

        rightPanel.setBackground(Color.BLACK);
        leftPanel.setBackground(Color.CYAN);

        rightPanel.setPreferredSize(new Dimension(375,100));
        leftPanel.setPreferredSize(new Dimension(375,100));

        rightPanel.setOpaque(false);
        leftPanel.setOpaque(false);

        JPanel table = userTable();

        centerPanel.add(leftPanel, BorderLayout.WEST);
        centerPanel.add(rightPanel, BorderLayout.EAST);

        centerPanel.setOpaque(false);

        assert table != null;
        centerPanel.add(table, BorderLayout.CENTER);

        this.add(centerPanel, BorderLayout.CENTER);
        // Alt kısım
        JPanel southPanel = new JPanel();
        southPanel.setBackground(Color.gray);
        southPanel.setOpaque(false);
        southPanel.setLayout(new GridBagLayout());

        southPanel.setPreferredSize(new Dimension(100,100));

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.weightx = 1.0; // Yatayda boş alan paylaşımı
        gbc.weighty = 1.0; // Dikeyde boş alan paylaşımı
        gbc.fill = GridBagConstraints.BOTH; // Hem yatayda hem dikeyde genişle
        gbc.insets = new Insets(10, 10, 10, 10); // Boşlukları sıfırla

        JButton sendInvBtn = new JButton("Davet Gönder");
        sendInvBtn.setFocusable(false);
        sendInvBtn.addActionListener(e -> {
            users();
        });
        gbc.gridx = 0;  gbc.gridy = 0;  gbc.gridwidth = 1;
        southPanel.add(sendInvBtn, gbc);


        JButton addFolderBtn = new JButton("Dosya Ekle");
        addFolderBtn.setFocusable(false);
        addFolderBtn.addActionListener(e -> {

        });
        gbc.gridx = 1;  gbc.gridy = 0;  gbc.gridwidth = 1;
        southPanel.add(addFolderBtn, gbc);


        JButton seeFoldersBtn = new JButton("Dosyaları Görüntüle");
        seeFoldersBtn.setFocusable(false);
        seeFoldersBtn.addActionListener(e -> {

        });
        gbc.gridx = 2;  gbc.gridy = 0;  gbc.gridwidth = 1;
        southPanel.add(seeFoldersBtn, gbc);


        JButton removeFolderBtn = new JButton("Dosya Sil");
        removeFolderBtn.setFocusable(false);
        removeFolderBtn.addActionListener(e -> {

        });
        gbc.gridx = 3;  gbc.gridy = 0;  gbc.gridwidth = 1;
        southPanel.add(removeFolderBtn, gbc);

        this.add(southPanel, BorderLayout.SOUTH);


        this.revalidate();
        this.repaint();
    }

    private JPanel userTable() {

        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 240, 220));
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.weightx = 1.0; // Yatayda boş alan paylaşımı
        gbc.weighty = 1.0; // Dikeyde boş alan paylaşımı
        gbc.fill = GridBagConstraints.BOTH; // Hem yatayda hem dikeyde genişle
        gbc.insets = new Insets(10, 10, 10, 10); // Boşlukları sıfırla

        gbc.gridx = 0;
        gbc.gridy = 0;

        MysqlConnector mysqlConnector = new MysqlConnector();

        ResultSet resultSet = mysqlConnector.getUsers();

        try {
            while(resultSet.next()) {

                if( resultSet.getString("team").equals(customer.getTeam()) ) {

                    JPanel cell = new JPanel();
                    cell.setLayout(new FlowLayout(FlowLayout.LEADING));
                    cell.setOpaque(false);


                    JLabel userLabel = new JLabel(resultSet.getString("user_name"));
                    userLabel.setFont(new Font("Times New Roman",Font.BOLD,20));
                    cell.add(userLabel);

                    panel.add(cell, gbc);
                    gbc.gridy++;
                }

            }

            return panel;

        } catch (Exception e){
            JOptionPane.showMessageDialog(null,"Hata Kodu:"+e.getMessage(),
                    "Bir Hata Oluştu (getUsers)",JOptionPane.ERROR_MESSAGE);

            return null;
        }
    }

    private void users() {

        JFrame frame = new JFrame("Kullanıcılar");
        frame.setLocation(500,250);
        frame.setSize(500,350);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.weightx = 1.0; // Yatayda boş alan paylaşımı
        gbc.weighty = 1.0; // Dikeyde boş alan paylaşımı
        gbc.fill = GridBagConstraints.BOTH; // Hem yatayda hem dikeyde genişle
        gbc.insets = new Insets(10, 10, 10, 10); // Boşlukları sıfırla

        gbc.gridx = 0;
        gbc.gridy = 0;

        MysqlConnector mysqlConnector = new MysqlConnector();
        ResultSet resultSet = mysqlConnector.getUsers();

        try {
            while(resultSet.next()) {

                JPanel cellPanel = cell(resultSet.getString("user_name"));

                frame.add(cellPanel, gbc);
                gbc.gridy++;
            }

        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null,"Hata Kodu:"+exception.getMessage(),
                    "Bir Hata Oluştu (users)",JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel cell(String username) {

        JPanel cellPanel = new JPanel();
        cellPanel.setLayout(new GridLayout(1,2));

        JPanel usernamePanel = new JPanel();
        usernamePanel.setLayout(new FlowLayout(FlowLayout.LEADING, 10, 15));
        usernamePanel.setOpaque(false);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 15));
        buttonPanel.setOpaque(false);


        JLabel usernameLabel = new JLabel(username);
        usernameLabel.setFont(new Font("Arial",Font.PLAIN,30));
        usernamePanel.add(usernameLabel);


        JButton sendBtn = new JButton("Davet Et");
        sendBtn.setPreferredSize(new Dimension(135,50));
        sendBtn.setFocusable(false);
        sendBtn.addActionListener(e -> {
            customer.sendInv(username);

            JOptionPane.showMessageDialog(null,"Davet Gönderildi",
                    "Bilgilendirme",JOptionPane.INFORMATION_MESSAGE);
        });
        buttonPanel.add(sendBtn);

        cellPanel.add(usernamePanel);
        cellPanel.add(buttonPanel);

        return cellPanel;
    }
}
