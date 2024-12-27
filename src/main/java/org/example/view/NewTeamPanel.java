package org.example.view;

import org.example.managers.*;
import org.example.utils.MysqlConnector;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static org.example.managers.FileManager.fileOpen;

public class NewTeamPanel extends JPanel implements IPanel {

    private static Customer customer;

    public NewTeamPanel(JPanel mainCardPanel, CardLayout cardLayout, Customer customer) {
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

        Log log = Log.getInstance();

        this.setLayout(new BorderLayout());

        // Üst boşluk
        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(100,35));
        topPanel.setBackground(Color.red);
        topPanel.setOpaque(false);
        this.add(topPanel, BorderLayout.NORTH);

        // Center
        JPanel center = new JPanel();
        center.setLayout(new GridLayout(1,2));
        center.setOpaque(false);

        // Center -> Left
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setOpaque(false);

        JPanel tempLeft = new JPanel();
        tempLeft.setPreferredSize(new Dimension(150,100));
        tempLeft.setBackground(Color.gray);
        tempLeft.setOpaque(false);
        leftPanel.add(tempLeft, BorderLayout.WEST);

        JPanel tempRight = new JPanel();
        tempRight.setPreferredSize(new Dimension(150,100));
        tempRight.setBackground(Color.magenta);
        tempRight.setOpaque(false);
        leftPanel.add(tempRight, BorderLayout.EAST);



        JPanel labelPanel = new JPanel(); // Takım bilgisi paneli
        labelPanel.setLayout(new FlowLayout());
        labelPanel.setBackground(Color.yellow);
        labelPanel.setOpaque(false);
        labelPanel.setPreferredSize(new Dimension(100,50));

        JLabel teamLabel = new JLabel("Takım Arkadaşları");
        teamLabel.setFont(new Font("Times New Roman",Font.BOLD, 30));
        labelPanel.add(teamLabel);

        leftPanel.add(labelPanel, BorderLayout.NORTH);

        JPanel friendsPanel = createTable();
        assert friendsPanel != null;
        leftPanel.add(friendsPanel, BorderLayout.CENTER);

        JPanel bottomLeftPanel = new JPanel();
        bottomLeftPanel.setPreferredSize(new Dimension(100,50));
        bottomLeftPanel.setBackground(Color.black);
        bottomLeftPanel.setOpaque(false);
        leftPanel.add(bottomLeftPanel, BorderLayout.SOUTH);

        center.add(leftPanel);

        // Center -> Right
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridBagLayout());
        rightPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.weightx = 1.0; // Yatayda boş alan paylaşımı
        gbc.weighty = 1.0; // Dikeyde boş alan paylaşımı
        gbc.fill = GridBagConstraints.BOTH; // Hem yatayda hem dikeyde genişle
        gbc.insets = new Insets(10, 10, 10, 10); // Boşlukları sıfırla


        // Right -> Top Blank Lines
        gbc.gridx = 0;  gbc.gridy = 0;  gbc.gridwidth = 1;
        rightPanel.add(new JLabel(""), gbc);

        gbc.gridx = 2;  gbc.gridy = 0;  gbc.gridwidth = 1;
        rightPanel.add(new JLabel(""), gbc);

        // Right -> Buttons
        JButton sendInvBtn = new JButton("Takım Arkadaşı Ekle");
        sendInvBtn.setFocusable(false);
        sendInvBtn.addActionListener(e -> {
            users();
        });
        gbc.gridx = 1;  gbc.gridy = 1;  gbc.gridwidth = 1;
        rightPanel.add(sendInvBtn, gbc);


        JButton addFileBtn = new JButton("Dosya Yükle");
        addFileBtn.setFocusable(false);
        addFileBtn.addActionListener(e -> {
            JFileChooser upload = new JFileChooser();
            upload.setCurrentDirectory(new File("C:/Users/Volkan/Desktop/Proje"));

            int choice = upload.showOpenDialog(this);

            if(choice == JFileChooser.APPROVE_OPTION) {

                File selectedFile = upload.getSelectedFile();

                customer.addToTeamWorkPlace(selectedFile);

                log.logger.info(customer.getUsername() + " adlı kullanıcı  "+selectedFile.getName()+" adlı dosyayı" +
                        " kendi takım klasörüne ekledi ");
            }
        });
        gbc.gridx = 1;  gbc.gridy = 2;  gbc.gridwidth = 1;
        rightPanel.add(addFileBtn, gbc);


        JButton openFileBtn = new JButton("Dosya Düzenle");
        openFileBtn.setFocusable(false);
        openFileBtn.addActionListener(e -> {
            JFileChooser open = new JFileChooser();
            open.setCurrentDirectory(new File("src/SystemFolders/TeamFolders/" + customer.getUsername() ));

            int choice = open.showOpenDialog(this);

            if(choice == JFileChooser.APPROVE_OPTION) {

                File selectedFile = open.getSelectedFile();

                fileOpen(selectedFile);

                log.logger.info(customer.getUsername() + " adlı kullanıcı "+selectedFile.getName()+" adlı" +
                        " dosyayı kendi takım klasöründe açtı ");
            }
        });
        gbc.gridx = 1;  gbc.gridy = 3;  gbc.gridwidth = 1;
        rightPanel.add(openFileBtn, gbc);


        JButton shareFileBtn = new JButton("Dosyaları Paylaş");
        shareFileBtn.setFocusable(false);
        shareFileBtn.addActionListener(e -> {
            share();
        });
        gbc.gridx = 1;  gbc.gridy = 4;  gbc.gridwidth = 1;
        rightPanel.add(shareFileBtn, gbc);


        JButton seeRequestsBtn = new JButton("Davetler");
        seeRequestsBtn.setFocusable(false);
        seeRequestsBtn.addActionListener(e -> {
            invites();
        });
        gbc.gridx = 1;  gbc.gridy = 5;  gbc.gridwidth = 1;
        rightPanel.add(seeRequestsBtn, gbc);

        // Right -> Bottom Blank Lines
        gbc.gridx = 2;  gbc.gridy = 6;  gbc.gridwidth = 1;
        rightPanel.add(new JLabel(""), gbc);

        center.add(rightPanel);

        this.add(center, BorderLayout.CENTER);

        // Alt boşluk
        JPanel bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(100,35));
        bottomPanel.setBackground(Color.blue);
        bottomPanel.setOpaque(false);
        this.add(bottomPanel, BorderLayout.SOUTH);


        this.revalidate();
        this.repaint();
    }

    private static JPanel createTable() {
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

        ResultSet resultSet = mysqlConnector.getFriends(customer.getUsername());

        try {
            while(resultSet.next()) {

                    JPanel cell = new JPanel();
                    cell.setLayout(new FlowLayout(FlowLayout.LEADING));
                    cell.setOpaque(false);


                    JLabel userLabel = new JLabel(resultSet.getString("friend"));
                    userLabel.setFont(new Font("Times New Roman",Font.BOLD,20));
                    cell.add(userLabel);

                    panel.add(cell, gbc);
                    gbc.gridy++;

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

        Log log = Log.getInstance();

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


        JButton sendBtn = new JButton("Ekle");
        sendBtn.setPreferredSize(new Dimension(135,50));
        sendBtn.setFocusable(false);
        sendBtn.addActionListener(e -> {

            customer.sendInv(username);

            log.logger.info(customer.getUsername()+" tarafından "+username+" adlı kişiye takım isteği gönderildi");

            JOptionPane.showMessageDialog(null,username+" 'e istek gönderildi",
                    "Bilgilendirme",JOptionPane.INFORMATION_MESSAGE);
        });
        buttonPanel.add(sendBtn);

        cellPanel.add(usernamePanel);
        cellPanel.add(buttonPanel);

        return cellPanel;
    }

    // İsteklerin görüntülenmesi
    private void invites() {

        JFrame frame = new JFrame("Davetler");
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
        ResultSet resultSet = mysqlConnector.getFriendRequests(customer.getUsername());

        try {
            while(resultSet.next()) {

                JPanel cellPanel = invitesCell(resultSet.getString("invite_from"),
                        resultSet.getInt("inv_id"));

                frame.add(cellPanel, gbc);
                gbc.gridy++;
            }

        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null,"Hata Kodu:"+exception.getMessage(),
                    "Bir Hata Oluştu (users)",JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel invitesCell(String username, int inviteId) {

        Log log = Log.getInstance();

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


        JButton sendBtn = new JButton("Arkadaş Ekle");
        sendBtn.setPreferredSize(new Dimension(135,50));
        sendBtn.setFocusable(false);
        sendBtn.addActionListener(e -> {

            customer.addFriend(username, inviteId);

            log.logger.info(customer.getUsername()+" tarafından "+username+" adlı kişi takım arkadaşı olarak eklendi");

            JOptionPane.showMessageDialog(null,username+" takım arkadaşı olarak eklendi",
                    "Bilgilendirme",JOptionPane.INFORMATION_MESSAGE);
        });
        buttonPanel.add(sendBtn);

        cellPanel.add(usernamePanel);
        cellPanel.add(buttonPanel);

        return cellPanel;
    }

    private void share() {

        Log log = Log.getInstance();

        JFrame frame = new JFrame("Takım Arkadaşları");
        frame.setLocation(500,250);
        frame.setSize(500,350);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Kullanıcılar kısmı
        JPanel friendsPanel = new JPanel();
        friendsPanel.setLayout(new GridBagLayout());

        List<String> chosenFriends = new ArrayList<>();

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.weightx = 1.0; // Yatayda boş alan paylaşımı
        gbc.weighty = 1.0; // Dikeyde boş alan paylaşımı
        gbc.fill = GridBagConstraints.BOTH; // Hem yatayda hem dikeyde genişle
        gbc.insets = new Insets(10, 10, 10, 10); // Boşlukları sıfırla

        gbc.gridx = 0;
        gbc.gridy = 0;

        MysqlConnector mysqlConnector = new MysqlConnector();
        ResultSet resultSet = mysqlConnector.getFriends(customer.getUsername());

        try {
            while(resultSet.next()) {

                JCheckBox checkBox = new JCheckBox(resultSet.getString("friend"));
                checkBox.setFocusable(false);
                checkBox.addActionListener(e -> {
                    if (checkBox.isSelected()) {
                        chosenFriends.add(checkBox.getText());
                    } else {
                        chosenFriends.remove(checkBox.getText());
                    }
                });

                friendsPanel.add(checkBox, gbc);
                gbc.gridy++;
            }

            frame.add(friendsPanel, BorderLayout.CENTER);

            // Buton kısmı
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 10,15));

            JButton shareBtn = new JButton("Paylaş");
            shareBtn.setFocusable(false);
            shareBtn.addActionListener(e -> {

                TeamCopyProcess copyProcess = new TeamCopyProcess(customer, chosenFriends);
                ProgressBarProcess progress = new ProgressBarProcess();

                copyProcess.start();
                progress.start();
                try {
                    copyProcess.join();
                    progress.join();
                } catch (InterruptedException ex) {

                }

                log.logger.info(customer.getUsername() + " adlı kullanıcı takım arkadaşlarıyla TeamFolders" +
                        " dizinindeki dosyalarını paylaştı ");

            });
            buttonPanel.add(shareBtn);

            frame.add(buttonPanel, BorderLayout.SOUTH);

        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null,"Hata Kodu:"+exception.getMessage(),
                    "Bir Hata Oluştu (share)",JOptionPane.ERROR_MESSAGE);
        }

    }
}
