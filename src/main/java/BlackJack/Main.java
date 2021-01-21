package BlackJack;


import BlackJack.GuiWindow.SplashScreen;
import BlackJack.gameLogic.Application;

public class Main {

    public static String pathUser = System.getProperty("user.dir") + "\\userdata\\";
    public static String pathTemp = pathUser + "\\temp\\";
    public static boolean development = true;

    public static void main(String[] args) {


        if (Main.class.getResource("Main.class").toString().startsWith("jar:")
                || Main.class.getResource("Main.class").toString().startsWith("rsrc:")) {
            development = false;
        }

        Config.getInstance().loadConfig();

//        new Thread(() -> {
//            SplashScreen splash = new SplashScreen(12000);
//            splash.dispose();
//        }).start();

        System.setProperty("sun.java2d.uiScale", "1");



        Application.main(args);

    }
}
