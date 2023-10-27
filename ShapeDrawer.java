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
    ArrayList<Bullets> bulletData;
    ArrayList<Enemies> enemyData;

    int jumpHeight = 95;

    /**
     * .
     */
    ShapeDrawer(JFrame gameFrame) {
        this.gameFrame = gameFrame;
        this.platformData = new ArrayList<Platform>(); 
        this.bulletData = new ArrayList<Bullets>();
        this.enemyData = new ArrayList<Enemies>();
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

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(new Color(0, 0, 0));
        Font font = new Font("Comic Sans MS", Font.BOLD, 24);
        g2d.setFont(font);
        g2d.drawString("" + gameScore, 100, 100);

        // loop over platforms and paint them
        for (Platform i : platformData) {
            switch (i.type) {
                case 0: // normal platform
                    Image platformImage = t.getImage("pixil-frame-0.png");
                    g2d.drawImage(platformImage, i.x, i.y, this);
                    break;
                case 1: // horizontally moving platform
                    g2d.setColor(new Color(0, 0, 0));
                    g2d.fillRect(i.x, i.y, platformWidth, platformHeight);
                    break;
                case 2: // vertically moving platform
                    g2d.setColor(new Color(0, 0, 0));
                    g2d.fillRect(i.x, i.y, platformWidth, platformHeight);
                    break;
                case 3: // breakable platform
                    g2d.setColor(new Color(0, 100, 0));
                    g2d.fillRect(i.x, i.y, platformWidth, platformHeight);
                    break;
                case 4: // booster platform
                    g2d.setColor(new Color(0, 0, 255));
                    g2d.fillRect(i.x, i.y, platformWidth, platformHeight);
                    break;
                default:
                    g2d.setColor(new Color(0, 0, 0));
                    g2d.fillRect(i.x, i.y, platformWidth, platformHeight);
            }
            
        }

        // loops over bullets and paints them
        for (Bullets i : bulletData) {
            g2d.setColor(new Color(0, 0, 0));
            g2d.fillOval(i.x, i.y, i.size, i.size);
        }

        // loops over enemies and paints them
        for (Enemies i : enemyData) {
            g2d.setColor(new Color(100, 50, 50));
            g2d.fillRect(i.x, i.y, i.width, i.height);
        }

        //paints the player
        Image characterImage = t.getImage("character3.png");
        if ((PlayerMovement.counter >= 0 && PlayerMovement.counter <= 10)
            || PlayerMovement.counter > 30) {
            characterImage = t.getImage("character2.png");
        } else if (PlayerMovement.counter > 10 && PlayerMovement.counter <= 25) {
            characterImage = t.getImage("character.png");
        } else {
            characterImage = t.getImage("character3.png");
        }

        g2d.setColor(new Color(0, 0, 0));
        g2d.drawImage(characterImage, x, y, playerWidth, playerHeight, this);

        int greenIntensity = gameScore <= 10000 ? 200 - 200 * gameScore / 10000 : 0;
        int blueIntensity = gameScore  <= 10000 ? 255 - 155 * gameScore / 10000 : 100;
        
        setBackground(new Color(0, greenIntensity, blueIntensity));
    }
}
