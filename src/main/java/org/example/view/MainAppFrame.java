package org.example.view;

import org.example.managers.BaseUser;

import javax.swing.*;
import java.awt.*;

public class MainAppFrame extends JFrame {

    BaseUser user;

    public MainAppFrame (BaseUser user) {
        this.user = user;
        initUI();
    }

    private void initUI () {
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Main App");
        this.setBackground(new Color(203, 220, 235));
        this.setSize(1250,700);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        // Sayfa Containerları

        JPanel header = new JPanel();
        JPanel westContainer = new JPanel();
        JPanel eastContainer = new JPanel();
        JPanel footer = new JPanel();


        header.setBackground(new Color(31, 80, 154));
        westContainer.setBackground(Color.red);
        eastContainer.setBackground(Color.DARK_GRAY);
        footer.setBackground(Color.black);


        header.setPreferredSize(new Dimension(100,70));
        westContainer.setPreferredSize(new Dimension(50,100)); //100
        eastContainer.setPreferredSize(new Dimension(50,100)); //100->150(*)
        footer.setPreferredSize(new Dimension(100,45));


        this.add(header, BorderLayout.NORTH);
        this.add(eastContainer, BorderLayout.EAST);
        this.add(westContainer, BorderLayout.WEST);
        this.add(footer, BorderLayout.SOUTH);

        // Diğer sayfalara geçişler

        CardLayout cardLayout = new CardLayout();
        JPanel mainCardPanel = new JPanel(cardLayout);

        MainMenuPanel mainMenuPanel = new MainMenuPanel(mainCardPanel, cardLayout, user);
        AccountPanel accountPanel = new AccountPanel(mainCardPanel, cardLayout, user);
        TeamPanel teamPanel = new TeamPanel(mainCardPanel, cardLayout, user);

        mainCardPanel.add(mainMenuPanel, "Main Menu");
        mainCardPanel.add(accountPanel, "My Account");
        mainCardPanel.add(teamPanel, "Team");

        // Center


        cardLayout.show(mainCardPanel, "Main Menu");

        this.add(mainCardPanel, BorderLayout.CENTER);

        // Header

        header.setLayout(new GridLayout(1,2)); //LEADING 10,15

        Dimension buttonSize = new Dimension(125,45);

        JPanel leftPanel = new JPanel();
        leftPanel.setOpaque(false);
        leftPanel.setLayout(new FlowLayout(FlowLayout.LEADING,10,15));

        JButton mainMenuBtn = new JButton("Ana Sayfa");
        mainMenuBtn.setFocusable(false);
        mainMenuBtn.setPreferredSize(buttonSize);

        mainMenuBtn.addActionListener(e -> {
            mainMenuPanel.refreshContent(mainCardPanel, cardLayout);

            cardLayout.show(mainCardPanel, "Main Menu");
        });

        leftPanel.add(mainMenuBtn);


        JButton teamBtn = new JButton("Takım");
        teamBtn.setFocusable(false);
        teamBtn.setPreferredSize(buttonSize);

        teamBtn.addActionListener(e -> {
            teamPanel.refreshContent(mainCardPanel, cardLayout);

            cardLayout.show(mainCardPanel, "Team");
        });

        leftPanel.add(teamBtn);

        JPanel rightPanel = new JPanel();
        rightPanel.setOpaque(false);
        rightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT,10,15));

        //Hesap butonu
        JButton accountBtn = new JButton("Hesabım");
        accountBtn.setFocusable(false);
        accountBtn.setPreferredSize(new Dimension(90,45));

        accountBtn.addActionListener(e -> {
            accountPanel.refreshContent(mainCardPanel, cardLayout);

            cardLayout.show(mainCardPanel, "My Account");
        });

        rightPanel.add(accountBtn);

        header.add(leftPanel);
        header.add(rightPanel);
        //

    }
}
