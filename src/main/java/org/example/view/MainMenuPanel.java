package org.example.view;

import org.example.managers.BaseUser;
import org.example.utils.MysqlConnector;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.sql.ResultSet;


public class MainMenuPanel {


    // Dosya işlemlerinin yapıldığı kısım
    public static class MainCustomerPanel extends JPanel implements IPanel {

        BaseUser customer;

        public MainCustomerPanel(JPanel mainCardPanel, CardLayout cardLayout, BaseUser customer) {
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

            this.setLayout(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();

            gbc.weightx = 1.0; // Yatayda boş alan paylaşımı
            gbc.weighty = 1.0; // Dikeyde boş alan paylaşımı
            gbc.fill = GridBagConstraints.BOTH; // Hem yatayda hem dikeyde genişle
            gbc.insets = new Insets(10, 10, 10, 10); // Boşlukları sıfırla


            // üst boşluk

            gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1;
            this.add(new JLabel(""), gbc);

            gbc.gridx = 2; gbc.gridy = 0; gbc.gridwidth = 1;
            this.add(new JLabel(""), gbc);

            gbc.gridx = 2; gbc.gridy = 1; gbc.gridwidth = 1;
            this.add(new JLabel(""), gbc);

            // sayfa içeriği

            JButton uploadFolderBtn = new JButton("Dosya Yükle");
            uploadFolderBtn.setFocusable(false);
            uploadFolderBtn.addActionListener(e -> {
                JFileChooser upload = new JFileChooser();
                upload.setCurrentDirectory(new File("C:/Users/Volkan/Desktop/Proje"));
                int choice = upload.showOpenDialog(this);

                if (choice == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = upload.getSelectedFile();
                    String fileName = selectedFile.getName();

                    customer.addToWorkPlace(fileName);
                }

            });

            gbc.gridx = 1; gbc.gridy = 2; gbc.gridwidth = 1;
            this.add(uploadFolderBtn, gbc);


            JButton changeFolderBtn = new JButton("Dosyalarda Değişiklik Yap");
            changeFolderBtn.setFocusable(false);
            changeFolderBtn.addActionListener(e -> {
                JFileChooser upload = new JFileChooser();
                upload.setCurrentDirectory(new File("src/SystemFolders/folders/OriginalFolders/" + customer.getUsername()));
                upload.showOpenDialog(this);
            });

            gbc.gridx = 1; gbc.gridy = 3; gbc.gridwidth = 1;
            this.add(changeFolderBtn, gbc);


            JButton seeSparedFoldersBtn = new JButton("Yedekleri Göster");
            seeSparedFoldersBtn.setFocusable(false);
            seeSparedFoldersBtn.addActionListener(e -> {
                JFileChooser upload = new JFileChooser();
                upload.setCurrentDirectory(new File("src/SystemFolders/folders/SavedFolders/" + customer.getUsername()));
                upload.showOpenDialog(this);
            });

            gbc.gridx = 1; gbc.gridy = 4; gbc.gridwidth = 1;
            this.add(seeSparedFoldersBtn, gbc);


            // Alt boşluk

            gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 1;
            this.add(new JLabel(""), gbc);

            gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 1;
            this.add(new JLabel(""), gbc);


            this.revalidate();
            this.repaint();
        }
    }

    // Admin işlemlerinin yapıldığı kısım
    public static class MainAdminPanel extends JPanel implements IPanel {

        private static BaseUser admin;

        public MainAdminPanel(JPanel mainCardPanel, CardLayout cardLayout, BaseUser admin) {
            this.admin = admin;
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

            JPanel header = new JPanel();
            JPanel westContainer = new JPanel();
            JPanel eastContainer = new JPanel();
            JPanel footer = new JPanel();


            header.setBackground(new Color(57, 62, 70));
            westContainer.setBackground(new Color(57, 62, 70));
            eastContainer.setBackground(new Color(57, 62, 70));
            footer.setBackground(new Color( 57, 62, 70));


            header.setOpaque(true);
            westContainer.setOpaque(true);
            eastContainer.setOpaque(true);
            footer.setOpaque(true);


            header.setPreferredSize(new Dimension(100,35));
            westContainer.setPreferredSize(new Dimension(375,100)); //100
            eastContainer.setPreferredSize(new Dimension(375,100)); //100->150(*)
            footer.setPreferredSize(new Dimension(100,45));


            this.add(header, BorderLayout.NORTH);
            this.add(eastContainer, BorderLayout.EAST);
            this.add(westContainer, BorderLayout.WEST);
            this.add(footer, BorderLayout.SOUTH);

            JPanel usersPanel = createTable();

            assert usersPanel != null;
            this.add(usersPanel, BorderLayout.CENTER);

            this.revalidate();
            this.repaint();
        }

        private static JPanel createTable() {
            JPanel table = new JPanel();
            table.setBackground(new Color(66, 69, 74));

            table.setLayout(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();

            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.insets = new Insets(10,10,10,10);

            gbc.gridx = 0;
            gbc.gridy = 0;

            // Tablo içeriği

            try {
                ResultSet resultSet = admin.getUsers();

                while (resultSet.next()) {
                    String username = resultSet.getString("user_name");
                    JPanel cell = createCell(username);
                    table.add(cell, gbc);

                    gbc.gridy++;
                }

                return table;

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,"Hata Kodu:"+e.getMessage(),
                        "Bir Hata Oluştu (resultSet)",JOptionPane.ERROR_MESSAGE);

                return null;
            }
        }

        private static JPanel createCell(String username) {
            JPanel cell = new JPanel();
            cell.setLayout(new GridLayout(1,2));
            cell.setOpaque(false);
            cell.setBackground(Color.black);

            Font font = new Font("Comic Sans",Font.PLAIN,30);

            JPanel leftPanel = new JPanel();
            leftPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
            leftPanel.setOpaque(false); // şeffaf

            JLabel nameLabel = new JLabel(username);
            nameLabel.setFont(font);
            leftPanel.add(nameLabel);


            JPanel rightPanel = new JPanel();
            rightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT,10,15));
            rightPanel.setOpaque(false); // şeffaf

            JButton inspectBtn = new JButton("İncele");
            inspectBtn.setFocusable(false);
            inspectBtn.addActionListener(e -> userFrame(username));

            rightPanel.add(inspectBtn);

            cell.add(leftPanel);
            cell.add(rightPanel);

            return cell;
        }

        private static void userFrame(String username) {
            JFrame frame = new JFrame();
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setTitle(username);
            frame.setBackground(new Color(203, 220, 235));
            frame.setSize(600,400);
            frame.setLocationRelativeTo(null);
            frame.setLayout(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();

            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.insets = new Insets(10,10,10,10);

            // İçerik

            Font titleFont = new Font("Comic Sans",Font.BOLD,12);
            Font basicFont = new Font("Comic Sans",Font.PLAIN,12);

            JLabel usernameLabel = new JLabel("Hesap Adı: ");
            usernameLabel.setFont(titleFont);

            gbc.gridx = 0;  gbc.gridy = 0; gbc.gridwidth = 1;
            frame.add(usernameLabel, gbc);

            JLabel user = new JLabel(username);
            user.setFont(basicFont);

            gbc.gridx = 1;  gbc.gridy = 0; gbc.gridwidth = 1;
            frame.add(user, gbc);


            JLabel passwordLabel = new JLabel("Şifresi: ");
            passwordLabel.setFont(titleFont);

            gbc.gridx = 0;  gbc.gridy = 1; gbc.gridwidth = 1;
            frame.add(passwordLabel, gbc);

            try {
                MysqlConnector mysqlConnector = new MysqlConnector();
                ResultSet resultSet = mysqlConnector.getInfo(username);
                resultSet.next();

                String hashedP = resultSet.getString("password");

                JLabel pass = new JLabel(hashedP);
                pass.setFont(basicFont);

                gbc.gridx = 1;  gbc.gridy = 1; gbc.gridwidth = 1;
                frame.add(pass, gbc);

            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null,"Hata Kodu:"+exception.getMessage(),
                        "Bir Hata Oluştu (getInfo)",JOptionPane.ERROR_MESSAGE);
            }

            JLabel maxFilesLabel = new JLabel("Max Depolama: ");
            maxFilesLabel.setFont(titleFont);

            gbc.gridx = 0;  gbc.gridy = 2; gbc.gridwidth = 1;
            frame.add(maxFilesLabel, gbc);


            JLabel maxFile = new JLabel("10");
            maxFile.setFont(basicFont);

            gbc.gridx = 1;  gbc.gridy = 2; gbc.gridwidth = 1;
            frame.add(maxFile, gbc);


            JButton documentsBtn = new JButton("Dökümanları");
            documentsBtn.setFocusable(false);
            documentsBtn.addActionListener(e -> {

            });

            gbc.gridx = 0;  gbc.gridy = 3; gbc.gridwidth = 1;
            frame.add(documentsBtn, gbc);

            JButton logsBtn = new JButton("Logları");
            logsBtn.setFocusable(false);
            logsBtn.addActionListener(e -> {

            });

            gbc.gridx = 1;  gbc.gridy = 3; gbc.gridwidth = 1;
            frame.add(logsBtn, gbc);

        }
    }
}
