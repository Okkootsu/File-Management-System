package org.example.view;

import org.example.managers.Login;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private static LoginFrame instance;

    public LoginFrame() {
        instance = this;
        initUI();
    }

    // Singleton tasarım deseni? -> uzaktan ana frame'e erişmek için
    public static LoginFrame getInstance() {
        if (instance == null) {
            instance = new LoginFrame();
        }
        return instance;
    }

    // Çıkış yapılınca paneli sıfırla
    public static void resetInstance() {
        instance = null;
    }

    private void initUI() {
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Login");
        this.setBackground(new Color(203, 220, 235));
        this.setSize(500,450);
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


        header.setPreferredSize(new Dimension(100,45));
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

        MainLoginPanel mainLoginPanel = new MainLoginPanel(mainCardPanel, cardLayout);
        RegisterPanel registerPanel = new RegisterPanel(mainCardPanel, cardLayout);

        mainCardPanel.add(mainLoginPanel, "Login");
        mainCardPanel.add(registerPanel, "Register");

        // Center

        cardLayout.show(mainCardPanel, "Login");


        this.add(mainCardPanel);
    }
}
