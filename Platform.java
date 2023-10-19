import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;


/**
 * .
 */
public class Platform extends JPanel {
    int x;
    int y;
    int jumpHeight = 95;
    JFrame gameFrame;
    ArrayList<Platform> platforms = new ArrayList<Platform>();

    Random rand = new Random(1);

    Platform(int x, int y, JFrame gameFrame) {
        this.x = x;
        this.y = y;
        this.gameFrame = gameFrame;
    }
        
    public void generatePlatform(int boundY) {
        for (int i = boundY; i < 95; i += jumpHeight) {
            for (int j = 0; j < 1; j++) {
                int randomX = rand.nextInt(gameFrame.getWidth());
                int randomY = rand.nextInt(jumpHeight);

                //System.out.println(randomX + " " + randomY);

                Platform platform = new Platform(randomX, randomY, gameFrame);
                platforms.add(platform);
                //platform.setSize(30, 10);
                gameFrame.add(platform);
                platform.paintComponent(platform.getGraphics());
                System.out.println("working");
                platform.setVisible(false);
                platform.paintComponent(platform.getGraphics());
            }
        }
    }

    
    /*
     * Poep.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);        

        g.setColor(new Color(0, 0, 0));
        g.fillRect(x, y, 30, 10);
    }
}
