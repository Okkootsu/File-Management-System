package org.example.managers;

import javax.swing.*;
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
}
