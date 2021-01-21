package BlackJack.gameLogic;

import BlackJack.Config;
import BlackJack.GuiWindow.MainWindow;
import BlackJack.Main;
import org.cef.CefApp;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


import java.lang.reflect.Field;


@SpringBootApplication
@ComponentScan(basePackages = {"Controller"})
@EnableJpaRepositories(basePackages = "Database.repositories")
@EntityScan(basePackages = {"Database.tables"})
public class Application {

    public static Game spiel;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(Application.class)
                .headless(false)
                .run(args);


                spiel = new Game();

                if (Main.development) {
                    chromeElfFix();
                }


                if (!CefApp.startup(args)) {
                    System.out.println("Startup initialization failed!");
                    System.exit(0);
                }


                MainWindow m = new MainWindow("http://localhost:82/", false, false);

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
}
