package org.example.managers;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Log {

    private static Log instance;
    public final Logger logger;

    private Log() {
        logger = Logger.getLogger(Log.class.getName());
        try {
            // FileHandler oluştur ve dosya adını belirt
            FileHandler fileHandler = new FileHandler("src/SystemFolders/logs/logs.txt", true);
            fileHandler.setFormatter(new SimpleFormatter() {
                @Override
                public synchronized String format(LogRecord record) {
                    return String.format(
                            "%1$tF %1$tT %2$-7s %3$s %n",
                            record.getMillis(),                // Tarih ve zaman
                            record.getLevel().getName(),       // Log seviyesi
                            record.getMessage()                // Log mesajı
                    );
                }
            });

            // Logger yapılandırması
            logger.setUseParentHandlers(false); // Varsayılan handler'ı kaldır
            logger.addHandler(fileHandler);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Hata kodu: " +e.getMessage() ,
                    "Hata!",JOptionPane.ERROR_MESSAGE);
        }
    }

    // Singleton erişim metodu
    public static Log getInstance() {
        if (instance == null) {
            instance = new Log();
        }
        return instance;
    }

    public static List<String> getLogs(String username) {
        try {
            List<String> list = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader("src/SystemFolders/logs/logs.txt"));
            String line;
            while( (line = reader.readLine()) != null ) {
                if( line.contains(username) ) {
                    list.add(line);
                }
            }
            reader.close();
            return list;

        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null,"Hata kodu: " +exception.getMessage() ,
                    "Hata!",JOptionPane.ERROR_MESSAGE);

            return null;
        }
    }

    public static List<String> lastMinuteLogs(String username) {
        List<String> userLogs = getLogs(username);
        List<String> presentLogs = new ArrayList<>();

        // Şu anki zaman
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneMinuteAgo = now.minusMinutes(1);

        // Zaman formatı (log dosyasındaki formatla eşleşmeli)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        assert userLogs != null;
        for(String line : userLogs) {
            String[] timeParts = line.split(" ",3);
            String dateTimeString = timeParts[0] + " " + timeParts[1];
            LocalDateTime logTime = LocalDateTime.parse(dateTimeString, formatter);

            if (logTime.isAfter(oneMinuteAgo) && logTime.isBefore(now)) {
                presentLogs.add(line);
            }

        }

        return presentLogs;
    }
}
