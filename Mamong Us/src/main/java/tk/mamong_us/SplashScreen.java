/*package tk.mamong_us;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SplashScreen extends JWindow {
    public static final BufferedImage background;

    static {
        BufferedImage background1;
        try {
            background1 = ImageIO.read(SplashScreen.class.getResourceAsStream("/splash.png"));
        } catch (IOException e) {
            background1 = null;
            e.printStackTrace();
        }
        background = background1;
    }

    public static final int MAX_PROGRESS = 36;

    public JProgressBar progressBar = new JProgressBar();

    public SplashScreen() {
        Container container = getContentPane();
        container.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBorder(new javax.swing.border.EtchedBorder());
        panel.setBackground(new Color(255, 255, 255));
        panel.setBounds(0, -14, 800, 450);
        panel.setLayout(null);
        container.add(panel);

        JLabel label = new JLabel("Hello World!");
        label.setFont(new Font("Verdana", Font.BOLD, 14));
        label.setBounds(0, 0, 800, 450);
        label.setIcon(new ImageIcon(background));
        panel.add(label);

        progressBar.setMaximum(MAX_PROGRESS);
        progressBar.setBounds(0, 435, 799, 14);
        progressBar.setStringPainted(true);
        progressBar.setForeground(Color.BLACK);
        container.add(progressBar);
        setSize(799, 450);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
*/