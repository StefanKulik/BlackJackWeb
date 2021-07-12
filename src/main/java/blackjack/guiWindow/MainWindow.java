package blackjack.guiWindow;

import blackjack.application.Config;
import blackjack.application.Application;
import org.cef.CefApp;
import org.cef.CefApp.CefAppState;
import org.cef.CefClient;
import org.cef.CefSettings;
import org.cef.browser.CefBrowser;
import org.cef.handler.CefAppHandlerAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

import static blackjack.application.Application.pathTemp;


public class MainWindow extends JFrame {

    public MainWindow(String startURL, boolean useOSR, boolean isTransparent) {


        CefApp.addAppHandler(new CefAppHandlerAdapter(null) {
            @Override
            public void stateHasChanged(CefAppState state) {
                // Shutdown the app if the native CEF part is terminated
                if (state == CefAppState.TERMINATED) System.exit(0);
            }
        });
        CefSettings settings = new CefSettings();

        setUndecorated(true);
        settings.windowless_rendering_enabled = useOSR;
        settings.cache_path = pathTemp;
        CefApp cefApp_ = CefApp.getInstance(settings);
        CefClient client_ = cefApp_.createClient();
        CefBrowser browser_ = client_.createBrowser(startURL, useOSR, isTransparent);
        Component browserUI_ = browser_.getUIComponent();
        getContentPane().add(browserUI_, BorderLayout.CENTER);
        pack();
        setResizable(true);
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setTitle("blackjack");
        ImageIcon img = new ImageIcon(Objects.requireNonNull(Application.class.getResource("/static/assets/png/inmed.png")));
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

