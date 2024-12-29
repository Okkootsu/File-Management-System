package org.example.managers;

import java.util.List;

import static org.example.managers.Log.lastMinuteLogs;

public class AbnormalBehaviours {

    public static boolean isLoginAbnormal(String username) {
        List<String> lastMinuteLogs = lastMinuteLogs(username);
        int counter = 0;
        for(String line : lastMinuteLogs) {

            if(counter == 3){
                return true;
            }

            if(line.contains("başarısız giriş")) {
                counter++;
            }
        }

        return counter == 3;

    }

    public static boolean isRequestAbnormal(String username) {
        List<String> lastMinuteLogs = lastMinuteLogs(username);
        int counter = 0;
        for(String line : lastMinuteLogs) {

            if(counter == 3){
                return true;
            }

            if(line.contains("başarısız şifre")) {
                counter++;
            }
        }

        return counter == 3;
    }
}
