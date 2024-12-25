package org.example.managers;

import javax.swing.*;
import java.awt.*;

public class ProgressBarProcess extends Thread {

    @Override
    public void run() {
        progress();
    }

    JProgressBar bar;
    JLabel progressLabel;
    JFrame frame;

    private void progress() {
        frame = new JFrame("Kopyalanıyor...");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(500,250);
        frame.setSize(450,300);
        frame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10,10,10,10);


        // Üst boşluk
        gbc.gridx = 0; gbc.gridy = 0;
        frame.add(new JPanel(), gbc);

        gbc.gridx = 2; gbc.gridy = 0;
        frame.add(new JPanel(), gbc);


        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        progressLabel = new JLabel("Dosyalar Kopyalanıyor...");
        progressLabel.setFont(new Font("Arial",Font.PLAIN,20));

        topPanel.add(progressLabel);

        gbc.gridx = 1; gbc.gridy = 1;
        frame.add(topPanel, gbc);


        bar = new JProgressBar(0, 100);
        bar.setStringPainted(true);
        bar.setForeground(Color.GREEN);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 3;
        frame.add(bar, gbc);

        // Alt boşluk
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1;
        frame.add(new JPanel(), gbc);

        frame.setVisible(true);

        // SwingWorker başlatma
        new ProgressTask().execute();
    }

    private class ProgressTask extends SwingWorker<Void, Integer> {
        @Override
        protected Void doInBackground() throws Exception {
            // Progress bar değerlerini güncelle
            for (int i = 0; i <= 100; i++) {
                Thread.sleep(15); // Simüle edilen işlem süresi
                publish(i); // Arayüz güncelleme
            }
            return null;
        }

        @Override
        protected void process(java.util.List<Integer> chunks) {
            // Son güncellenen değeri al ve progress bar'ı güncelle
            int value = chunks.get(chunks.size() - 1);
            bar.setValue(value);
        }

        @Override
        protected void done() {
            // İşlem tamamlandığında mesaj güncelle
            progressLabel.setText("Kopyalama işlemi tamamlandı.");
            bar.setValue(100);
            try {
                Thread.sleep(1000); // Biraz bekle
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            frame.dispose(); // Frame'i kapat
        }
    }

    private void fill() {
        for(int i=0; i<=100; i++) {

            final int progress = i;
            SwingUtilities.invokeLater(() -> bar.setValue(progress));

            try {
                Thread.sleep(10);
            } catch (Exception e) {

            }
        }

        SwingUtilities.invokeLater(() -> {
            progressLabel.setText("Kopyalama işlemi tamamlandı.");

            try {
                Thread.sleep(2000);
            } catch (Exception e) {

            }

            frame.dispose();

        });
    }
}
