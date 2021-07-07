package blackjack.application;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class Config {

    private static Config INSTANCE = new Config();

    // "private" verhindert die Erzeugung des Objektes über andere Methoden
    private Config() {}

    // Eine nicht synchronisierte Zugriffsmethode auf Klassenebene.
    public static Config getInstance() { return INSTANCE; }

    private final String configFilePath = Application.pathUser + "\\config.json";

    public enum Theme {LIGHT, DARK}

    private Theme theme = Theme.LIGHT;

    private int capital;

    private ArrayList<String> lastGames;


    @JsonIgnore
    public String getThemeAsString() { return getInstance().theme == Theme.DARK ? "DARK" : "LIGHT"; }

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

    public void setTheme(Theme myTheme) { this.theme = myTheme; }
    public Theme getTheme() { return theme; }


    public void setCapital(int capital) { this.capital = capital; }
    public int getCapital() { return this.capital; }


    public void setLastGames(ArrayList<String> lastgames) { this.lastGames = lastgames; }
    public ArrayList<String> getLastGames() { return this.lastGames; }
}
