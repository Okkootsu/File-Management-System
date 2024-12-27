package org.example.view;

import org.example.managers.Customer;
import org.example.managers.Log;
import org.example.utils.MysqlConnector;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

/*
public class MainTeamPanel extends JPanel implements IPanel {

    Customer customer;

    public MainTeamPanel(JPanel mainCardPanel, CardLayout cardLayout, Customer customer) {
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

        this.setLayout(new GridBagLayout());

        // Sayfa geçişleri
        TeamPanel teamPanel = new TeamPanel(mainCardPanel, cardLayout, customer);

        mainCardPanel.add(teamPanel, "Team");
        //
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

        JButton seeInvitesBtn = new JButton("Takım Davetleri");
        seeInvitesBtn.setFocusable(false);
        seeInvitesBtn.addActionListener(e -> {
            if( customer.getInv().equals("None") || !(customer.getTeam().equals("None")) ) {
                JOptionPane.showMessageDialog(null,"Hiç davetiniz yok",
                        "Bilgilendirme",JOptionPane.INFORMATION_MESSAGE);
            } else {

                int choice = JOptionPane.showOptionDialog(this,customer.getInv()+" adlı takıma" +
                                " katılmak ister misiniz?",
                        "Bilgilendirme",JOptionPane.YES_NO_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,null,null,0);

                if(choice == 0) {
                    MysqlConnector mysqlConnector = new MysqlConnector();
                    mysqlConnector.setTeamName(customer.getUsername(), customer.getInv());

                    customer.setTeam(customer.getInv());

                    log.logger.info(customer.getUsername() + " adlı kullanıcı '"+customer.getTeam()+"' adlı takıma katıldı ");
                }
            }
        });

        gbc.gridx = 1; gbc.gridy = 2; gbc.gridwidth = 1;
        this.add(seeInvitesBtn, gbc);


        JButton teamBtn = new JButton("Takımı Görüntüle");
        teamBtn.setFocusable(false);
        teamBtn.addActionListener(e -> {
            if(customer.getTeam().equals("None")){
                JOptionPane.showMessageDialog(null,"Herhangi bir takımda değilsiniz!",
                        "Uyarı!",JOptionPane.INFORMATION_MESSAGE);
            } else {
                teamPanel.refreshContent(mainCardPanel, cardLayout);

                cardLayout.show(mainCardPanel, "Team");
            }
        });

        gbc.gridx = 1; gbc.gridy = 3; gbc.gridwidth = 1;
        this.add(teamBtn, gbc);


        JButton createTeamBtn = new JButton("Takım Oluştur");
        createTeamBtn.setFocusable(false);
        createTeamBtn.addActionListener(e -> {
            if(customer.getTeam().equals("None")) {
                String teamName = JOptionPane.showInputDialog("Takım Adı:");

                if( isTeamExists(teamName) ) {
                    JOptionPane.showMessageDialog(null,"Bu takım adı zaten var",
                            "Uyarı!",JOptionPane.INFORMATION_MESSAGE);
                } else {

                    MysqlConnector mysqlConnector = new MysqlConnector();
                    mysqlConnector.setTeamName(customer.getUsername(), teamName);

                    customer.setTeam(teamName);

                    customer.createTeamFolder();

                    log.logger.info(customer.getUsername() + " adlı kullanıcı '"+customer.getTeam()+"' adlı takımı oluşturdu ");

                    JOptionPane.showMessageDialog(null,"Takım oluşturuldu",
                            "Bilgilendirme",JOptionPane.INFORMATION_MESSAGE);
                }

            } else {
                JOptionPane.showMessageDialog(null,"Zaten bir takımdasınız",
                        "Uyarı!",JOptionPane.INFORMATION_MESSAGE);
            }
        });

        gbc.gridx = 1; gbc.gridy = 4; gbc.gridwidth = 1;
        this.add(createTeamBtn, gbc);


        // Alt boşluk

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 1;
        this.add(new JLabel(""), gbc);

        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 1;
        this.add(new JLabel(""), gbc);


        this.revalidate();
        this.repaint();
    }

    private boolean isTeamExists(String teamName) {
        try {
            MysqlConnector mysqlConnector = new MysqlConnector();
            ResultSet resultSet = mysqlConnector.getAllTeams();

            while(resultSet.next()) {
                String team = resultSet.getString("team");

                if( team.equals(teamName) ){
                    return true;
                }
            }
            return false;

        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null,"Hata Kodu:"+exception.getMessage(),
                    "Bir Hata Oluştu (isTeamExists)",JOptionPane.ERROR_MESSAGE);

            return true;
        }
    }
} */
