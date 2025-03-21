package org.example.view;

import org.example.managers.*;
import org.example.utils.MysqlConnector;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import static org.example.managers.FileManager.*;
import static org.example.managers.Log.getInstance;
import static org.example.managers.Log.getLogs;


public class MainMenuPanel {


    // Dosya işlemlerinin yapıldığı kısım
    public static class MainCustomerPanel extends JPanel implements IPanel {

        Customer customer;
        private Timer timer;

        public MainCustomerPanel(JPanel mainCardPanel, CardLayout cardLayout, Customer customer) {
            this.customer = customer;
            initializePanel(mainCardPanel, cardLayout);
        }

        @Override
        public void initializePanel(JPanel mainCardPanel, CardLayout cardLayout) {
            this.setOpaque(true);
            this.setBackground(new Color(155, 164, 180));
            refreshContent(mainCardPanel, cardLayout);
        }

        @Override
        public void refreshContent(JPanel mainCardPanel, CardLayout cardLayout) {
            this.removeAll();

            Log log = Log.getInstance();

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

            // sayfa içeriği

            JButton uploadFolderBtn = new JButton("Dosya Yükle");
            uploadFolderBtn.setFocusable(false);
            uploadFolderBtn.addActionListener(e -> {
                JFileChooser upload = new JFileChooser();
                upload.setCurrentDirectory(new File("C:/Users/Volkan/Desktop/Proje"));
                int choice = upload.showOpenDialog(this);

                if (choice == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = upload.getSelectedFile();

                    customer.addToWorkPlace(selectedFile);

                    log.logger.info(customer.getUsername()+" adlı kullanıcı bireysel OriginalFolders dizinine" +
                            " "+selectedFile.getName()+" adlı dosyayı yükledi");
                }

            });

            gbc.gridx = 1; gbc.gridy = 1; gbc.gridwidth = 1;
            this.add(uploadFolderBtn, gbc);


            JPanel autoSavePanel = new JPanel();
            autoSavePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
            autoSavePanel.setOpaque(false);
            autoSavePanel.setPreferredSize(new Dimension(100,25));

            JCheckBox autoSave = new JCheckBox("Otomatik Kopyalamayı Aç");
            autoSave.setFocusable(false);
            autoSave.setOpaque(false);
            autoSave.setPreferredSize(new Dimension(225,75));
            autoSave.setFont(new Font("Times New Roman",Font.BOLD,15));
            autoSave.addActionListener(e -> {
                if(autoSave.isSelected()) {
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            CopyProcess autoCopyProcess = new CopyProcess(customer);
                            ProgressBarProcess autoProgress = new ProgressBarProcess(customer.getUsername());

                            autoCopyProcess.start();
                            autoProgress.start();


                            log.logger.info(customer.getUsername()+" adlı kullanıcı için otomatik yedekleme yapıldı");
                        }
                    }, 0, 15 * 1000);
                } else {
                    timer.cancel();
                    timer = null;
                }
            });
            autoSavePanel.add(autoSave);

            gbc.gridx = 2; gbc.gridy = 1; gbc.gridwidth = 1;
            this.add(autoSavePanel, gbc);


            JButton changeFolderBtn = new JButton("Dosyalarda Değişiklik Yap");
            changeFolderBtn.setFocusable(false);
            changeFolderBtn.addActionListener(e -> {

                JFileChooser open = new JFileChooser();
                open.setCurrentDirectory(new File("src/SystemFolders/folders/OriginalFolders/" + customer.getUsername()));
                int choice = open.showOpenDialog(this);

            if (choice == JFileChooser.APPROVE_OPTION) {
                File selectedFile = open.getSelectedFile();

                fileOpen(selectedFile);

                log.logger.info(customer.getUsername()+" adlı kullanıcı bireysel OriginalFolders dizininde dosya" +
                        " düzenlemesini açtı");
            }

            });

            gbc.gridx = 1; gbc.gridy = 2; gbc.gridwidth = 1;
            this.add(changeFolderBtn, gbc);


            JPanel copyBtnPanel = new JPanel();
            copyBtnPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 10,10));
            copyBtnPanel.setBackground(Color.BLACK);
            copyBtnPanel.setOpaque(false); // şeffaf
            copyBtnPanel.setPreferredSize(new Dimension(100,25));

            JButton copyBtn = new JButton("Yedekle");
            copyBtn.setFocusable(false);
            copyBtn.setPreferredSize(new Dimension(95,50));
            copyBtn.addActionListener(e -> {

                CopyProcess copyProcess = new CopyProcess(customer);
                ProgressBarProcess progress = new ProgressBarProcess(customer.getUsername());

                copyProcess.start();
                progress.start();


                log.logger.info(customer.getUsername()+" adlı kullanıcı bireysel SavedFolders dizinine yedekleme yaptı");
            });

            copyBtnPanel.add(copyBtn);


            JButton deleteBtn = new JButton("Dosya Sil");
            deleteBtn.setFocusable(false);
            deleteBtn.setPreferredSize(new Dimension(95,50));
            deleteBtn.addActionListener(e -> {
                JFileChooser del = new JFileChooser();
                del.setCurrentDirectory(new File("src/SystemFolders/folders/OriginalFolders/" + customer.getUsername()));
                int choice = del.showOpenDialog(this);

                if (choice == JFileChooser.APPROVE_OPTION) {

                    File selectedFile = del.getSelectedFile();
                    try {
                        Files.delete(selectedFile.toPath());

                        log.logger.info(customer.getUsername() + " adlı kullanıcı bireysel OriginalFolders" +
                                " dizininden "+selectedFile.getName()+" adlı dosyayı sildi");

                        JOptionPane.showMessageDialog(null,"Dosya kaldırıldı",
                                "Bilgilendirme",JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null,"Hata Kodu:"+ex.getMessage(),
                                    "Bir Hata Oluştu (Dosya Sil)",JOptionPane.ERROR_MESSAGE);
                    }

                }
            });

            copyBtnPanel.add(deleteBtn);

            gbc.gridx = 2; gbc.gridy = 2; gbc.gridwidth = 1;
            this.add(copyBtnPanel, gbc);



            JButton seeSparedFoldersBtn = new JButton("Yedekleri İndir");
            seeSparedFoldersBtn.setFocusable(false);
            seeSparedFoldersBtn.addActionListener(e -> {
                JFileChooser download = new JFileChooser();
                download.setCurrentDirectory(new File("src/SystemFolders/folders/SavedFolders/" + customer.getUsername()));
                int choice = download.showOpenDialog(this);

                if( choice == JFileChooser.APPROVE_OPTION ) {

                        File selectedFile = download.getSelectedFile();
                        Path source = selectedFile.toPath();

                        File downloads = new File(System.getProperty("user.home") + "\\Downloads\\" + selectedFile.getName());
                        Path target = downloads.toPath();

                        fileCopy(source, target, customer.getMaxFileCount());

                        JOptionPane.showMessageDialog(null,"Yedek İndirildi.",
                                "İndirme başarılı",JOptionPane.INFORMATION_MESSAGE);

                }
            });

            gbc.gridx = 1; gbc.gridy = 3; gbc.gridwidth = 1;
            this.add(seeSparedFoldersBtn, gbc);



            JButton deleteSavedFoldersBtn = new JButton("Yedek Klasörünü Boşalt");
            deleteSavedFoldersBtn.setFocusable(false);
            deleteSavedFoldersBtn.addActionListener(e -> {
                int choice = JOptionPane.showOptionDialog(this,"Yedek Klasörünü TAMAMEN silmek" +
                                " istediğinize emin misiniz?",
                        "Uyarı!",JOptionPane.YES_NO_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,null,null,0);
                if(choice == 0) {
                    Path savedFolders = Paths.get("src/SystemFolders/folders/SavedFolders/" + customer.getUsername());
                    fileDelete(savedFolders);
                    File remakeSaved = new File("src/SystemFolders/folders/SavedFolders/" + customer.getUsername());

                    if(remakeSaved.mkdir()) {
                        log.logger.info(customer.getUsername() + " adlı kullanıcı bireysel SavedFolders dizinini boşalttı");

                        JOptionPane.showMessageDialog(null,"Yedek klasötü tamamen temizlendi",
                                "İşlem tamamlandı",JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            });

            gbc.gridx = 1; gbc.gridy = 4; gbc.gridwidth = 1;
            this.add(deleteSavedFoldersBtn, gbc);

            // Alt boşluk

            gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 1;
            this.add(new JLabel(""), gbc);


            this.revalidate();
            this.repaint();
        }
    }

    // Admin işlemlerinin yapıldığı kısım
    public static class MainAdminPanel extends JPanel implements IPanel {

        private static Admin admin;

        public MainAdminPanel(JPanel mainCardPanel, CardLayout cardLayout, Admin admin) {
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


            header.setBackground(new Color(155, 164, 180));
            westContainer.setBackground(new Color(155, 164, 180));
            eastContainer.setBackground(new Color(155, 164, 180));
            footer.setBackground(new Color(155, 164, 180));


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
            table.setBackground(new Color(253, 250, 217));

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

            Log log = getInstance();

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

            // Boşluklar


            // İçerik

            Font titleFont = new Font("Comic Sans",Font.BOLD,12);
            Font basicFont = new Font("Comic Sans",Font.PLAIN,12);

            JLabel usernameLabel = new JLabel("Hesap Adı: ");
            usernameLabel.setFont(titleFont);

            gbc.gridx = 0;  gbc.gridy = 0; gbc.gridwidth = 1;
            frame.add(usernameLabel, gbc);

            JLabel user = new JLabel(username);
            user.setFont(basicFont);

            gbc.gridx = 1;  gbc.gridy = 0; gbc.gridwidth = 3;
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

                gbc.gridx = 1;  gbc.gridy = 1; gbc.gridwidth = 3;
                frame.add(pass, gbc);

            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null,"Hata Kodu:"+exception.getMessage(),
                        "Bir Hata Oluştu (getInfo)",JOptionPane.ERROR_MESSAGE);
            }

            JLabel maxFilesLabel = new JLabel("Max Depolama: ");
            maxFilesLabel.setFont(titleFont);

            gbc.gridx = 0;  gbc.gridy = 2; gbc.gridwidth = 1;
            frame.add(maxFilesLabel, gbc);


            JLabel maxFile = new JLabel(String.valueOf(admin.getMaxFileCount(username)));
            maxFile.setFont(basicFont);

            gbc.gridx = 1;  gbc.gridy = 2; gbc.gridwidth = 3;
            frame.add(maxFile, gbc);


            JButton documentsBtn = new JButton("Dökümanları");
            documentsBtn.setFocusable(false);
            documentsBtn.addActionListener(e -> {
                JFileChooser open = new JFileChooser();
                open.setCurrentDirectory(new File("src/SystemFolders/folders/OriginalFolders/" + username));
                int choice = open.showOpenDialog(null);

                if (choice == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = open.getSelectedFile();

                    fileOpen(selectedFile);

                }
            });

            gbc.gridx = 0;  gbc.gridy = 3; gbc.gridwidth = 1;
            frame.add(documentsBtn, gbc);

            JButton logsBtn = new JButton("Logları");
            logsBtn.setFocusable(false);
            logsBtn.addActionListener(e -> {
                logs(username);
            });

            gbc.gridx = 1;  gbc.gridy = 3; gbc.gridwidth = 1;
            frame.add(logsBtn, gbc);

            JButton requestsBtn = new JButton("İstekleri");
            requestsBtn.setFocusable(false);
            requestsBtn.addActionListener(e -> {
                requests(username);
            });

            gbc.gridx = 2;  gbc.gridy = 3; gbc.gridwidth = 1;
            frame.add(requestsBtn, gbc);

            JButton fileCountBtn = new JButton("Max Dosya Sayısını Ayarla");
            fileCountBtn.setFocusable(false);
            fileCountBtn.addActionListener(e -> {
                String count = JOptionPane.showInputDialog("Yeni max dosya sayısı:");
                admin.changeMaxFileCount(username, Integer.parseInt(count));

                log.logger.info(username+" adlı kullanıcının maksimum dosya kapasitesi "+count+" olarak değiştirildi ");

                JOptionPane.showMessageDialog(null,"Kullanıcının maksimum dosya kapasitesi değiştirildi",
                        "Bilgilendirme",JOptionPane.INFORMATION_MESSAGE);
            });

            gbc.gridx = 3;  gbc.gridy = 3; gbc.gridwidth = 1;
            frame.add(fileCountBtn, gbc);

        }

        private static void requests(String username) {

            Log log = Log.getInstance();

            JFrame frame = new JFrame();
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setTitle("Parola değiştirme talebi");
            frame.setBackground(new Color(203, 220, 235));
            frame.setSize(450,250);
            frame.setLocationRelativeTo(null);
            frame.setLayout(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();

            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.insets = new Insets(10,10,10,10);


            // boşluk
            gbc.gridx = 0;  gbc.gridy = 0; gbc.gridwidth = 1;
            frame.add(new JPanel(), gbc);

            // istek
            MysqlConnector mysqlConnector = new MysqlConnector();
            ResultSet resultSet = mysqlConnector.getPasswordRequests(username);

            try {
                if(resultSet.next()) {
                    JPanel panel = new JPanel();
                    panel.setLayout(new GridLayout(1,2));

                    JPanel leftPanel = new JPanel();
                    leftPanel.setLayout(new FlowLayout(FlowLayout.LEADING));

                    JLabel usernameLabel = new JLabel(username);
                    usernameLabel.setFont(new Font("Arial",Font.BOLD,20));
                    leftPanel.add(usernameLabel);

                    JPanel rightPanel = new JPanel();
                    rightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

                    JButton acceptBtn = new JButton("Kabul Et");
                    acceptBtn.setFocusable(false);
                    acceptBtn.addActionListener(e -> {
                        try {
                            mysqlConnector.updatePassword(username, resultSet.getString("new_password"));

                            log.logger.info(username+" adlı kullanıcının şifre değiştirme talebi kabul edildi");

                            JOptionPane.showMessageDialog(null,"Onay verildi",
                                    "Bilgilendirme",JOptionPane.INFORMATION_MESSAGE);

                            mysqlConnector.deletePasswordRequest(username);

                            Thread.sleep(1000);

                            frame.dispose();


                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null,"Hata Kodu:"+ex.getMessage(),
                                    "Bir Hata Oluştu (kabul et)",JOptionPane.ERROR_MESSAGE);
                        }
                    });
                    rightPanel.add(acceptBtn);

                    JButton denyBtn = new JButton("Reddet");
                    denyBtn.setFocusable(false);
                    denyBtn.addActionListener(e -> {
                        try {
                            mysqlConnector.deletePasswordRequest(username);

                            log.logger.info(username+" adlı kullanıcının şifre değiştirme talebi reddedildi");

                            JOptionPane.showMessageDialog(null,"İstek reddedildi",
                                    "Bilgilendirme",JOptionPane.INFORMATION_MESSAGE);

                            Thread.sleep(1000);

                            frame.dispose();


                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null,"Hata Kodu:"+ex.getMessage(),
                                    "Bir Hata Oluştu (reddet)",JOptionPane.ERROR_MESSAGE);
                        }
                    });
                    rightPanel.add(denyBtn);

                    panel.add(leftPanel);
                    panel.add(rightPanel);

                    gbc.gridx = 0;  gbc.gridy = 1; gbc.gridwidth = 1;
                    frame.add(panel, gbc);

                    gbc.gridx = 0;  gbc.gridy = 2; gbc.gridwidth = 1;
                    frame.add(new JPanel(), gbc);

                } else {
                    JLabel infoLabel = new JLabel("Bu kullanıcının herhangi bir talebi yok");
                    infoLabel.setFont(new Font("Arial",Font.BOLD,20));
                    gbc.gridx = 0;  gbc.gridy = 1; gbc.gridwidth = 1;
                    frame.add(infoLabel, gbc);

                    gbc.gridx = 0;  gbc.gridy = 2; gbc.gridwidth = 1;
                    frame.add(new JPanel(), gbc);
                }
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null,"Hata Kodu:"+exception.getMessage(),
                        "Bir Hata Oluştu (Requests)",JOptionPane.ERROR_MESSAGE);
            }
        }

        private static void logs(String username) {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setTitle(username+" logları");
            frame.setBackground(new Color(203, 220, 235));
            frame.setSize(450,250);
            frame.setLocationRelativeTo(null);

            JPanel labelPanel = new JPanel();
            labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS)); // Dikey düzen

            // içerik
            List<String> userLogs = getLogs(username);

            for(int i = 0; i < Objects.requireNonNull(userLogs).size(); i++) {
                JLabel label = new JLabel(userLogs.get(i));
                label.setFont(new Font("Times New Roman",Font.BOLD,15));
                labelPanel.add(label);
            }

            JScrollPane scrollPane = new JScrollPane(labelPanel);
            frame.add(scrollPane);
            frame.setVisible(true);
        }
    }
}
