package org.example.view;

import javax.swing.*;
import java.awt.*;

public interface IPanel {

    //Singleton tasarım desenini panellere uygulamak için metodlar
    void initializePanel(JPanel mainCardPanel, CardLayout cardLayout);
    void refreshContent(JPanel mainCardPanel, CardLayout cardLayout);
}
