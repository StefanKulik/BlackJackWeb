package BlackJack.GuiWindow;

import BlackJack.Config;
import BlackJack.Main;
import BlackJack.gameLogic.Application;
import org.cef.CefApp;
import org.cef.CefApp.CefAppState;
import org.cef.CefClient;
import org.cef.CefSettings;
import org.cef.browser.CefBrowser;
import org.cef.handler.CefAppHandlerAdapter;
import org.w3c.dom.css.RGBColor;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;

import static BlackJack.Main.pathTemp;


public class MainWindow extends JFrame {
    static Point compCoords;

    public MainWindow(String startURL, boolean useOSR, boolean isTransparent) {



        CefApp.addAppHandler(new CefAppHandlerAdapter(null) {
            @Override
            public void stateHasChanged(CefAppState state) {
                // Shutdown the app if the native CEF part is terminated
                if (state == CefAppState.TERMINATED) System.exit(0);
            }
        });
        CefSettings settings = new CefSettings();

        JPanel content = new JPanel();

        compCoords = null;
        content.addMouseListener(new MouseListener() {
            public void mouseReleased(MouseEvent e) {
                compCoords = null;
            }

            public void mousePressed(MouseEvent e) {
                compCoords = e.getPoint();
            }

            public void mouseExited(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseClicked(MouseEvent e) {
            }
        });
        content.addMouseMotionListener(new MouseMotionListener() {
            public void mouseMoved(MouseEvent e) {
            }

            public void mouseDragged(MouseEvent e) {
                Point currCoords = e.getLocationOnScreen();
                setLocation(currCoords.x - compCoords.x, currCoords.y - compCoords.y);
            }
        });




        setUndecorated(true);
        settings.windowless_rendering_enabled = useOSR;
        settings.cache_path = pathTemp;
        CefApp cefApp_ = CefApp.getInstance(settings);
        CefClient client_ = cefApp_.createClient();
        CefBrowser browser_ = client_.createBrowser(startURL, useOSR, isTransparent);
        Component browserUI_ = browser_.getUIComponent();
        getContentPane().add(browserUI_, BorderLayout.CENTER);


        getContentPane().add(content, BorderLayout.NORTH);
        pack();
        setResizable(true);
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setTitle("BlackJack");
        ImageIcon img = new ImageIcon(Main.class.getResource("/static/assets/png/inmed.png"));
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

