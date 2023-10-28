import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;


/**
 * Poep.
 */
public class ShapeDrawer extends JPanel {
    int playerX = 100;
    int playerY = 500;
    int playerHeight;
    int platformHeight;
    int platformWidth;
    int playerWidth;
    int gameDistance; //track how far the player has gotten
    int gameScore;
    JFrame gameFrame;
    Shape playerShape;
    ShapeDrawer gamePanel;

    Toolkit t = Toolkit.getDefaultToolkit();

    // dumb data structuring
    ArrayList<Platform> platformData;
    ArrayList<Bullets> bulletData;
    ArrayList<Enemies> enemyData;

    int jumpHeight = 95;

    /**
     * .
     */
    ShapeDrawer(JFrame gameFrame, ShapeDrawer gamePanel) {
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
        this.playerShape = new Rectangle(playerX, playerY, playerWidth, playerHeight);
        this.gamePanel = gamePanel;
    }
    
    /**
     * .
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(new Color(0, 0, 0));
        Font font = new Font("Comic Sans MS", Font.PLAIN, 100);
        g2d.setFont(font);

        try {
            if (!gamePanel.isVisible()) {
                g2d.drawString("Sketch Hop", gameFrame.getWidth() / 2 - 275, 120);
                return;
            }
        } catch (Exception e) {
            System.out.println("this is supposed to happen");
        }

        font = new Font("Comic Sans MS", Font.BOLD, 24);
        g2d.setFont(font);
        g2d.drawString("" + gameScore, 100, 100);

        // loop over platforms and paint them
        for (Platform i : platformData) {
            switch (i.type) {
                case 0: // normal platform
                    Image platformImage = t.getImage("Images\\pixil-frame-0.png");
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
            g2d.fillRect(i.enemyX, i.enemyY, i.enemyWidth, i.enemyHeight);
        }

        //paints the player
        Image characterImage = t.getImage("Images\\character3.png");
        if ((PlayerMovement.counter >= 0 && PlayerMovement.counter <= 10)
            || PlayerMovement.counter > 30) {
            characterImage = t.getImage("Images\\character2.png");
        } else if (PlayerMovement.counter > 10 && PlayerMovement.counter <= 25) {
            characterImage = t.getImage("Images\\character.png");
        } else {
            characterImage = t.getImage("Images\\character3.png");
        }

        g2d.setColor(new Color(0, 0, 0));
        g2d.drawImage(characterImage, playerX, playerY, playerWidth, playerHeight, this);
        
        // makes the sky become darker the higher the player goes, simulating the sky and space
        int greenIntensity = gameScore <= 10000 ? 200 - 200 * gameScore / 10000 : 0;
        int blueIntensity = gameScore  <= 10000 ? 255 - 155 * gameScore / 10000 : 100;
        
        setBackground(new Color(0, greenIntensity, blueIntensity));
    }
}
