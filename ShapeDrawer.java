import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;


/**
 * Contains method to draw objects and text on screen.
 */
public class ShapeDrawer extends JPanel {
    int playerX;
    int playerY;
    int playerHeight;
    int platformHeight;
    int platformWidth;
    int playerWidth;
    int gameDistance; //track how far the player has gotten
    int gameScore;
    JFrame gameFrame;
    ShapeDrawer gamePanel;
    ShapeDrawer helpPanel;
    
    Font titleFont = new Font("Comic Sans MS", Font.PLAIN, 100);
    Font scoreFont = new Font("Comic Sans MS", Font.BOLD, 24);
    Font helpFont =  new Font("Comic Sans MS", Font.PLAIN, 60);

    Toolkit t = Toolkit.getDefaultToolkit();

    ArrayList<Platform> platformData;
    ArrayList<Bullet> bulletData;
    ArrayList<Enemy> enemyData;

    int jumpHeight = 95;

    /**
     * Constructor.
     * @param gameFrame the game frame.
     * @param gamePanel the game panel.
     * @param helpPanel the help panel.
     */
    ShapeDrawer(JFrame gameFrame, ShapeDrawer gamePanel, ShapeDrawer helpPanel) {
        this.gameFrame = gameFrame;
        this.platformData = new ArrayList<Platform>(); 
        this.bulletData = new ArrayList<Bullet>();
        this.enemyData = new ArrayList<Enemy>();
        this.playerX = 100;
        this.playerY = 500;
        this.playerHeight = 89;
        this.playerWidth = 55;
        this.platformHeight = 15;
        this.gameDistance = -250;
        this.platformWidth = 60;
        this.gameScore = 0;
        this.gamePanel = gamePanel;
        this.helpPanel = helpPanel;
    }
    
    /**
     * Paints the text, platforms, bullets, enemies and player.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(new Color(0, 0, 0));
        g2d.setFont(titleFont);

        // if in help menu
        if (gamePanel != null && helpPanel == null) {
            
            g2d.setFont(helpFont);
            g2d.drawString("The goal of the game is to jump from platfrom", 200, 200);
            g2d.drawString("to platform and get as high as possible", 200, 275);
            g2d.drawString("A - Move Left", gameFrame.getWidth() / 2 - 300, 400);
            g2d.drawString("D - Move Right", gameFrame.getWidth() / 2 - 300, 475);
            g2d.drawString("Arrow keys - Shoot bullets", gameFrame.getWidth() / 2 - 300, 550);
            
            g2d.setFont(titleFont);
            g2d.drawString("Help", gameFrame.getWidth() / 2 - 100, 100);            
            return;
        } else if (gamePanel != null && helpPanel != null) { // if in main menu
            g2d.drawString("Sketch Hop", gameFrame.getWidth() / 2 - 275, 120);
            return;
        }

        g2d.setFont(scoreFont);
        g2d.drawString("" + gameScore, 100, 100);

        // loop over platforms and paint them
        for (Platform i : platformData) {
            g2d.drawImage(i.platformImage, i.platformX, i.platformY, this);
        }

        // loops over bullets and paints them
        for (Bullet i : bulletData) {
            g2d.setColor(new Color(0, 0, 0));
            g2d.fillOval(i.bulletX, i.bulletY, i.size, i.size);
        }

        // loops over enemies and paints them
        for (Enemy i : enemyData) {
            g2d.drawImage(i.enemyImage, i.enemyX, i.enemyY, i.enemyWidth, i.enemyHeight, this);
        }

        //paints the player
        Image characterImage = t.getImage("Images\\character3.png");
        if ((PlayerMovement.jumpCounter >= 0 && PlayerMovement.jumpCounter <= 10)
            || PlayerMovement.jumpCounter > 30) {
            characterImage = t.getImage("Images\\character2.png");
        } else if (PlayerMovement.jumpCounter > 10 && PlayerMovement.jumpCounter <= 25) {
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
