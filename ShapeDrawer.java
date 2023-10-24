import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;


/**
 * Poep.
 */
public class ShapeDrawer extends JPanel {
    int x = 100;
    int y = 500;
    int playerHeight;
    int platformHeight;
    int platformWidth;
    int playerWidth;
    int gameDistance; //track how far the player has gotten
    int gameScore;
    JFrame gameFrame;
    //JPanel gamePanel;

    // dumb data structuring
    ArrayList<Platform> platformData;

    int jumpHeight = 95;

    /**
     * .
     */
    ShapeDrawer(JFrame gameFrame) {
        this.gameFrame = gameFrame;
        this.platformData = new ArrayList<Platform>(); 
        this.playerHeight = 55;
        this.playerWidth = 34;
        this.platformHeight = 15;
        this.gameDistance = -250;
        this.platformWidth = 60;
        this.gameScore = 0;
    }
    
    /**
     * .
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);        

        g.setColor(new Color(0, 0, 0));
        Font font = new Font("Monospaced", Font.BOLD, 24);
        g.setFont(font);
        g.drawString("" + gameScore, 100, 100);

        // loop over platforms and paint them
        for (Platform i : platformData) {
            switch (i.type) {
                case 0: // normal platform
                    g.setColor(new Color(0, 0, 0));
                    g.fillRect(i.x, i.y, platformWidth, platformHeight);
                    break;
                case 1: // horizontally moving platform
                    g.setColor(new Color(0, 0, 0));
                    g.fillRect(i.x, i.y, platformWidth, platformHeight);
                    break;
                case 2: // vertically moving platform
                    g.setColor(new Color(0, 0, 0));
                    g.fillRect(i.x, i.y, platformWidth, platformHeight);
                    break;
                case 3: // breakable platform
                    g.setColor(new Color(0, 100, 0));
                    g.fillRect(i.x, i.y, platformWidth, platformHeight);
                    break;
                case 4: // booster platform
                    g.setColor(new Color(0, 0, 255));
                    g.fillRect(i.x, i.y, platformWidth, platformHeight);
                    break;
                default:
                    g.setColor(new Color(0, 0, 0));
                    g.fillRect(i.x, i.y, platformWidth, platformHeight);
            }
            
        }

        //paints the player
        g.setColor(new Color(0, 0, 0));
        g.fillRect(x, y, playerWidth, playerHeight);
    
    }
}
