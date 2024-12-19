package org.example.view;

import org.example.managers.BaseUser;

import javax.swing.*;
import java.awt.*;

public class TeamPanel extends JPanel implements IPanel {

    BaseUser customer;

    public TeamPanel(JPanel mainCardPanel, CardLayout cardLayout, BaseUser customer) {
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

        this.add(new JLabel("Takım Bilgileri"), BorderLayout.CENTER);

        this.revalidate();
        this.repaint();
    }
}
