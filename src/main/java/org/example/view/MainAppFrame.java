package org.example.view;

import org.example.managers.BaseUser;
import org.example.managers.Customer;

import javax.swing.*;
import java.awt.*;

public class MainAppFrame extends JFrame {

    BaseUser user;
    private static MainAppFrame instance;

    public MainAppFrame (BaseUser user) {
        instance = this;
        this.user = user;
        initUI();
    }

    // Singleton tasarım deseni? -> uzaktan ana frame'e erişmek için
    public static MainAppFrame getInstance(BaseUser user) {
        if (instance == null) {
            instance = new MainAppFrame(user);
        }
        return instance;
    }

    // Çıkış yapılınca paneli sıfırla
    public static void resetInstance() {
        instance = null;
    }

    private void initUI () {
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Main App");
        this.setBackground(new Color(221, 230, 237));
        this.setSize(1250,700);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        JPanel appPanel = user.getPanel();

        this.add(appPanel, BorderLayout.CENTER);
    }

    public static class MainCustomerPanel extends JPanel{

        public MainCustomerPanel (Customer customer) {

            this.setLayout(new BorderLayout());

            // Sayfa Containerları

            JPanel header = new JPanel();
            JPanel westContainer = new JPanel();
            JPanel eastContainer = new JPanel();
            JPanel footer = new JPanel();


            header.setBackground(new Color(34, 40, 49));
            westContainer.setBackground(new Color(155, 164, 180));
            eastContainer.setBackground(new Color(155, 164, 180));
            footer.setBackground(new Color(155, 164, 180));


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

            MainMenuPanel.MainCustomerPanel mainMenuPanel = new MainMenuPanel.MainCustomerPanel(mainCardPanel, cardLayout, customer);
            AccountPanel accountPanel = new AccountPanel(mainCardPanel, cardLayout, customer);
            MainTeamPanel teamPanel = new MainTeamPanel(mainCardPanel, cardLayout, customer);

            mainCardPanel.add(mainMenuPanel, "Main Menu");
            mainCardPanel.add(accountPanel, "My Account");
            mainCardPanel.add(teamPanel, "Main Team");

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

                cardLayout.show(mainCardPanel, "Main Team");
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
        }
    }

    public static class MainAdminPanel extends JPanel {

        public MainAdminPanel (BaseUser admin) {

            this.setLayout(new BorderLayout());

            // Sayfa Containerları

            JPanel header = new JPanel();
            JPanel westContainer = new JPanel();
            JPanel eastContainer = new JPanel();
            JPanel footer = new JPanel();


            header.setBackground(new Color(34, 40, 49));
            westContainer.setBackground(new Color(57, 62, 70));
            eastContainer.setBackground(new Color(57, 62, 70));
            footer.setBackground(new Color( 57, 62, 70));


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

            MainMenuPanel.MainAdminPanel mainMenuPanel = new MainMenuPanel.MainAdminPanel(mainCardPanel, cardLayout, admin);
            AccountPanel accountPanel = new AccountPanel(mainCardPanel, cardLayout, admin);

            mainCardPanel.add(mainMenuPanel, "Main Menu");
            mainCardPanel.add(accountPanel, "My Account");


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
        }
    }
}
