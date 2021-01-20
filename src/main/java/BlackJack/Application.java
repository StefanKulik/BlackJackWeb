package BlackJack;

import BlackJack.gameLogic.Game;
import org.cef.CefApp;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


import java.lang.reflect.Field;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@ComponentScan(basePackages = {"Controller"})
//@EnableJpaRepositories(basePackages = "Test.database.repositories")
//@EntityScan(basePackages = {"Test.database.tables"})
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


                //MainWindow m = new MainWindow("http://localhost:84/", false, false);


                System.out.println("gestartet!");
    }

    private static void chromeElfFix() {
        try {
//             System.setProperty("java.library.path", "libs/jcef/;libs/java_getdrg");
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
