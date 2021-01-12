package webBlackJack;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Random;

class SplashScreen extends JWindow {
    private int duration;

    public SplashScreen(int d) {
        duration = d;

        Color background = new Color(18,18,18,255);
        Color c = new Color(18, 52, 86, 255);

        if (Config.getInstance().getTheme() == Config.Theme.LIGHT){
            background = new Color(255,255,255,255);
            c = new Color(173,173,173,255);
        }

        JPanel content = (JPanel) getContentPane();
        content.setBackground(background);
        int width = 400;
        int height = 115;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;
        setBounds(x, y, width, height);

        JLabel label1 = new JLabel("\nBlackJack");
        label1.setSize(width, height / 2);
        label1.setVerticalAlignment(JLabel.CENTER);
        label1.setHorizontalAlignment(JLabel.CENTER);
        label1.setFont(new Font("Helvetica Neue", Font.PLAIN, 36));
        label1.setForeground(c);
        content.add(label1, BorderLayout.NORTH);

        JLabel label2 = new JLabel("   loading . ");
        label2.setSize(width, height / 2);
        label2.setVerticalAlignment(JLabel.CENTER);
        label2.setHorizontalAlignment(JLabel.CENTER);
        label2.setForeground(c);
        content.add(label2, BorderLayout.WEST);

        JLabel label3 = new JLabel("(C) S. Kulik 2020");
        label3.setSize(width, height / 2);
        label3.setVerticalAlignment(JLabel.BOTTOM);
        label3.setHorizontalAlignment(JLabel.CENTER);
        label3.setForeground(c);
        content.add(label3, BorderLayout.EAST);

        content.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(c, 1), new EmptyBorder(16, 16, 8, 16)));

        String[] messages = {
                "load applikation ...",
                "initialise spring framework ...",
                "spring framework is starting ...",
                "starting tomcat ...",
                "webserver is running ...",
//                "database is starting ... ",
//                "connecting to database ...",
//                "database connection is established ...",
                "create main window ...",
                "show main window ...",
                "ready!",
                "ready!",
                "ready!",
                "ready!",
                "ready!",
                "ready!",
                "ready!",
                "ready!",
                "ready!"
        };

        setVisible(true);
        try {
            int pointinterval = 200; // Zeit bis zu einem neuen Punkt
            int i = 0;
            Random r = new Random();
            while (duration > 0) {
                for (int p = 1; p < 5 + r.nextInt(15); p++) {
                    label2.setText(messages[i % messages.length] + " " + ".".repeat(p));
                    int realpointinterval = r.nextInt(pointinterval);// + pointinterval / 2;
                    Thread.sleep(realpointinterval);
                    duration -= realpointinterval;
                }
                i++;
            }
        } catch (Exception e) {
        }
        this.dispose();
    }
}
