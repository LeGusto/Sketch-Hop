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

    Toolkit t = Toolkit.getDefaultToolkit();

    // dumb data structuring
    ArrayList<Platform> platformData;

    int jumpHeight = 95;

    /**
     * .
     */
    ShapeDrawer(JFrame gameFrame) {
        this.gameFrame = gameFrame;
        this.platformData = new ArrayList<Platform>(); 
        this.playerHeight = 89;
        this.playerWidth = 55;
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
                    Image platformImage = t.getImage("pixil-frame-0.png");
                    g.drawImage(platformImage, i.x, i.y, this);
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
        Image characterImage = t.getImage("character3.png");
        if ((PlayerMovement.counter >= 0 && PlayerMovement.counter <= 10) || PlayerMovement.counter > 30) {
            characterImage = t.getImage("character2.png");
        } else if (PlayerMovement.counter > 10 && PlayerMovement.counter <= 25) {
            characterImage = t.getImage("character.png");
        } else {
            characterImage = t.getImage("character3.png");
        }

        g.setColor(new Color(0, 0, 0));
        g.drawImage(characterImage, x, y, playerWidth, playerHeight, this);
    
    }
}
