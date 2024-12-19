package org.example.view;

import org.example.managers.BaseUser;

import javax.swing.*;
import java.awt.*;

public class MainMenuPanel extends JPanel implements IPanel {

    BaseUser customer;

    public MainMenuPanel (JPanel mainCardPanel, CardLayout cardLayout, BaseUser customer) {
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

        this.setLayout(new BorderLayout());

        this.add(new JLabel("Hoşgeldiniz " + customer.getUsername()), BorderLayout.CENTER);

        this.revalidate();
        this.repaint();
    }
}
