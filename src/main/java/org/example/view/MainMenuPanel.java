package org.example.view;

import org.example.managers.BaseUser;

import javax.swing.*;
import java.awt.*;
import java.io.File;


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

        BaseUser admin;

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

            JPanel usersPanel = createTable();

            this.add(usersPanel, BorderLayout.CENTER);

            this.revalidate();
            this.repaint();
        }

        private static JPanel createTable() {
            return null;
        }

        private static JPanel createCell(String username) {
            JPanel cell = new JPanel();
            cell.setLayout(new GridLayout());

            Font font = new Font("Comic Sans",Font.PLAIN,15);

            JPanel leftPanel = new JPanel();
            leftPanel.setLayout(new FlowLayout(FlowLayout.LEADING));

            JLabel nameLabel = new JLabel(username);
            nameLabel.setFont(font);
            leftPanel.add(nameLabel);


            JPanel rightPanel = new JPanel();
            rightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

            JButton inspectBtn = new JButton("İncele");
            inspectBtn.setFocusable(false);
            inspectBtn.addActionListener(e -> {

            });

            return cell;
        }
    }
}
