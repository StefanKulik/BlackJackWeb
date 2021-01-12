package BlackJack;

import org.cef.CefApp;
import org.cef.CefApp.CefAppState;
import org.cef.CefClient;
import org.cef.CefSettings;
import org.cef.browser.CefBrowser;
import org.cef.handler.CefAppHandlerAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static BlackJack.Main.pathTemp;

class MainWindow extends JFrame {

    MainWindow(String startURL, boolean useOSR, boolean isTransparent) {

        CefApp.addAppHandler(new CefAppHandlerAdapter(null) {
            @Override
            public void stateHasChanged(org.cef.CefApp.CefAppState state) {
                // Shutdown the app if the native CEF part is terminated
                if (state == CefAppState.TERMINATED) System.exit(0);
            }
        });
        CefSettings settings = new CefSettings();

        settings.windowless_rendering_enabled = useOSR;
        settings.cache_path = pathTemp;
        CefApp cefApp_ = CefApp.getInstance(settings);
        CefClient client_ = cefApp_.createClient();
        CefBrowser browser_ = client_.createBrowser(startURL, useOSR, isTransparent);
        Component browserUI_ = browser_.getUIComponent();
        getContentPane().add(browserUI_, BorderLayout.CENTER);
        pack();
        setSize(1920, 1080);
        setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setTitle("BlackJack");
        ImageIcon img = new ImageIcon(Main.class.getResource("/inmed.png"));
        setIconImage(img.getImage());
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Config.getInstance().saveConfig();
                CefApp.getInstance().dispose();
                dispose();
            }
        });
    }
}