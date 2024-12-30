package org.example.view;

import org.example.managers.BaseUser;
import org.example.managers.Log;
import org.example.utils.MysqlConnector;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

import static org.example.managers.AbnormalBehaviours.isRequestAbnormal;
import static org.example.utils.PasswordHashing.hashPassword;

public class AccountPanel extends JPanel implements IPanel {

    static BaseUser customer;

    public AccountPanel(JPanel mainCardPanel, CardLayout cardLayout, BaseUser customer) {
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

        JPanel eastContainer = new JPanel();
        JPanel westContainer = new JPanel();
        JPanel footer = new JPanel();

        eastContainer.setPreferredSize(new Dimension(150,100));
        westContainer.setPreferredSize(new Dimension(150,100));
        footer.setPreferredSize(new Dimension(100,150));
        eastContainer.setBackground(Color.green);
        westContainer.setBackground(Color.magenta);
        footer.setBackground(Color.YELLOW);
        eastContainer.setOpaque(false);
        westContainer.setOpaque(false);
        footer.setOpaque(false);

        this.add(eastContainer, BorderLayout.EAST);
        this.add(westContainer, BorderLayout.WEST);
        this.add(footer, BorderLayout.SOUTH);


        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setBackground(new Color(155, 164, 180));

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

                log.logger.info(customer.getUsername()+" adlı kullanıcı çıkış yaptı");

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
            changes();
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

                log.logger.info(customer.getUsername()+" adlı kullanıcı sistemden silindi");

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

    private void changes() {

        Log log = Log.getInstance();

        JFrame frame = new JFrame("Bilgileri Güncelle");
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

        // Üst boşluk
        gbc.gridx = 0;  gbc.gridy = 0;
        frame.add(new JPanel(), gbc);

        gbc.gridx = 2;  gbc.gridy = 0;
        frame.add(new JPanel(), gbc);

        // içerik
        JButton usernameBtn = new JButton("Kullanıcı Adını Değiştir");
        usernameBtn.setFocusable(false);
        usernameBtn.addActionListener(e -> {
            String newName = JOptionPane.showInputDialog("Yeni kullanıcı adı:");

            if(isValid(newName)) {
                customer.renameTheFolders(newName);

                MysqlConnector mysqlConnector = new MysqlConnector();
                mysqlConnector.updateName(customer.getUsername(), newName);

                customer.changeUsername(newName);
                log.logger.info(customer.getUsername()+" kullanıcı adını "+newName+" olarak değiştirdi ");
                customer.setUsername(newName);

                JOptionPane.showMessageDialog(null,"Kullanıcı adı değiştirildi",
                        "Bilgilendirme",JOptionPane.INFORMATION_MESSAGE);

            } else {

                log.logger.warning(customer.getUsername() + " tarafından başarısız kullanıcı adı değiştirme talebi");

                JOptionPane.showMessageDialog(null,"Bu kullanıcı adı alınamaz",
                        "Uyarı",JOptionPane.INFORMATION_MESSAGE);
            }
        });
        gbc.gridx = 1;  gbc.gridy = 1;
        frame.add(usernameBtn, gbc);


        JButton passwordBtn = new JButton("Şifreyi Değiştir");
        passwordBtn.setFocusable(false);
        passwordBtn.addActionListener(e -> {
            String newPassword = JOptionPane.showInputDialog("Yeni şifre:");

            if(isValidPassword(newPassword)) {
                customer.createPassRequest(hashPassword(newPassword));

                log.logger.info(customer.getUsername() + " adlı kullanıcı şifre değiştirme talebinde bulundu");

                JOptionPane.showMessageDialog(null,"Şifre değiştirme talebiniz gönderilmiştir",
                        "Bilgilendirme",JOptionPane.INFORMATION_MESSAGE);

            } else {

                if(isRequestAbnormal(customer.getUsername())) {
                    log.logger.severe(customer.getUsername() + " tarafından anormal durum tespit edildi");

                    JOptionPane.showMessageDialog(null,"Uyarı! \n" +
                                    "Kısa süre içerisinde çok fazla hatalı şifre değiştirme talebi yaptınız",
                            "UYARI!",JOptionPane.ERROR_MESSAGE);
                } else {
                    log.logger.warning(customer.getUsername() + " tarafından başarısız şifre değiştirme talebi");

                    JOptionPane.showMessageDialog(null,"Bu şifre kullanılamaz",
                            "Uyarı",JOptionPane.INFORMATION_MESSAGE);
                }

            }
        });
        gbc.gridx = 1;  gbc.gridy = 2;
        frame.add(passwordBtn, gbc);


        // Alt boşluk
        gbc.gridx = 0;  gbc.gridy = 3;
        frame.add(new JPanel(), gbc);
    }

    private static boolean isValid(String username) {
        if(username.isEmpty()) { return false; }
        if(username.length() > 45) { return false; }

        MysqlConnector mysqlConnector = new MysqlConnector();
        ResultSet resultSet = mysqlConnector.getInfo(username);

        try {
            if(resultSet.next()) {
                resultSet.close();
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    private static boolean isValidPassword(String password) {
        if(password.isEmpty()) { return false; }
        if(password.length() > 45) { return false; }

        MysqlConnector mysqlConnector = new MysqlConnector();
        ResultSet resultSet = mysqlConnector.getPasswordRequests(customer.getUsername());

        try {
            if(resultSet.next()) {
                return false;
            }
        } catch (Exception exception) {
            return false;
        }

        return true;
    }
}
