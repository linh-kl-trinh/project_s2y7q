package ui;

import javax.swing.*;
import java.net.MalformedURLException;
import java.net.URL;

public class Main {
    public static void main(String[] args) throws MalformedURLException, InterruptedException {
        JWindow window = new JWindow();
        final URL url = new URL("https://c.tenor.com/xTRIwCd1bKAAAAAC/cinema-minion.gif");
        ImageIcon splashImage = new ImageIcon(url);
        window.getContentPane().add(new JLabel("", splashImage, SwingConstants.CENTER));
        window.setBounds(0, 0, 500, 250);
        window.setVisible(true);
        window.setLocationRelativeTo(null);
        Thread.sleep(3250);
        window.setVisible(false);
        window.dispose();
        new MovieCollectionApp();
    }
}