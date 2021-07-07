package blackjack.application;

import blackjack.guiWindow.MainWindow;
import blackjack.gameLogic.Game;
import org.cef.CefApp;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


import java.lang.reflect.Field;
import java.util.Objects;


@SpringBootApplication
@ComponentScan(basePackages = {"blackjack.controller"})
@EnableJpaRepositories(basePackages = "blackjack.database.repositories")
@EntityScan(basePackages = {"BlackJack.Database.tables"})
public class Application {

    public static String pathUser = System.getProperty("user.dir") + "\\userdata\\";
    public static String pathTemp = pathUser + "\\temp\\";
    public static boolean development = true;

    public static Game spiel;
    public static MainWindow m;

    public static void main(String[] args) {

        if (Objects.requireNonNull(Application.class.getResource("Application.class")).toString().startsWith("jar:")
                || Objects.requireNonNull(Application.class.getResource("Application.class")).toString().startsWith("rsrc:")) {
            development = false;
        }

        Config.getInstance().loadConfig();

//        new Thread(() -> {
//            SplashScreen splash = new SplashScreen(12000);
//            splash.dispose();
//        }).start();

        System.setProperty("sun.java2d.uiScale", "1");



        ConfigurableApplicationContext context = new SpringApplicationBuilder(Application.class)
                .headless(false)
                .run(args);


        spiel = new Game();

        if (Application.development) {
            chromeElfFix();
        }

        if (!CefApp.startup(args)) {
            System.out.println("Startup initialization failed!");
            System.exit(0);
        }

//        m = new MainWindow("http://localhost:83/", false, false);

        Config.getInstance().loadConfig();
        System.out.println("gestartet!");
    }

    private static void chromeElfFix() {
        try {
            System.setProperty("java.library.path", "jcef/lib-win64");
            Field fieldSysPath = null;
            fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
            fieldSysPath.setAccessible(true);
            fieldSysPath.set(null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static MainWindow getWindow(){
        return m;
    }
}
