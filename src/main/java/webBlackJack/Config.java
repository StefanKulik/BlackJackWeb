package webBlackJack;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;


public class Config {

    private static Config INSTANCE = new Config();
    private int threads = 1;

    // "private" verhindert die Erzeugung des Objektes über andere Methoden
    private Config() {

    }

    // Eine nicht synchronisierte Zugriffsmethode auf Klassenebene.
    public static Config getInstance() {
        return INSTANCE;
    }

    private String configFilePath = Main.pathUser + "\\config.json";

//    private Locale language = Locale.getDefault();

//    public void setThreads(int threads) {
//        this.threads = threads;
//    }

//    public int getThreads() {
//        return threads;
//    }

    public enum Theme {LIGHT, DARK}

    private Theme theme = Theme.LIGHT;


    @JsonIgnore
    public String getThemeAsString() {
        switch (getInstance().theme) {
            case DARK:
                return "DARK";
            default:
                return "LIGHT";
        }
    }

//    @JsonIgnore
//    public String getLanguageCode() {
//        return Config.getInstance().getLanguage().toString().substring(0, 2).toUpperCase();
//    }

    public synchronized boolean loadConfig() {
        File config = new File(configFilePath);
        if (config.exists()) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                INSTANCE = mapper.readValue(config, Config.class);
                return true;
            } catch (IOException e) {
                System.out.println("Fehler beim Lesen der config-Datei");
                return false;
            }
        } else {
            saveConfig();
            return true;
        }
    }

    public synchronized boolean saveConfig() {
        File config = new File(configFilePath);
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            mapper.writeValue(config, INSTANCE);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

//    public void setLanguage(Locale myLanguage) {
//        this.language = myLanguage;
//    }

    public void setTheme(Theme myTheme) {
        this.theme = myTheme;
    }

//    public Locale getLanguage() {
//        return language;
//    }

    public Theme getTheme() {
        return theme;
    }
}
