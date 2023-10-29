import java.awt.Image;
import java.awt.Toolkit;
import java.util.Random;
import javax.swing.*;

/**
 * Enemies which kill the player upon collision.
 */
public class Enemy {
    int enemyX;
    int enemyY;
    int enemyWidth;
    int enemyHeight;
    Image enemyImage;
    JFrame gameFrame;
    ShapeDrawer gamePanel;

    /**
     * Constructor.
     * @param x x coordinate.
     * @param y y coordinate.
     * @param gameFrame game frame.
     * @param gamePanel game panel.
     */
    Enemy(int x, int y, JFrame gameFrame, ShapeDrawer gamePanel) {
        this.enemyX = x;
        this.enemyY = y;
        this.gameFrame = gameFrame;
        this.gamePanel = gamePanel;
        this.enemyWidth = 100;
        this.enemyHeight = 50;
        this.enemyImage = randomImage();
    }

    /**
     * Sets a random position for the enemy.
     * @param rand Random value with a preset seed.
     */
    public void randomPosition(Random rand) {
        enemyX = rand.nextInt(gameFrame.getWidth());
        enemyY = -rand.nextInt(gamePanel.jumpHeight * 10, gamePanel.jumpHeight * 20);
    }

    /**
     * Picks and assigns a random image for the enemy.
     * @return randomly chosen image.
     */
    private Image randomImage() {
        Toolkit t = Toolkit.getDefaultToolkit();
        Random rand = new Random();
        switch (rand.nextInt(3)) {
            case 0: // normal platform
                return t.getImage("Images\\enemy1.png");
            case 1: // horizontally moving platform
                return t.getImage("Images\\enemy2.png");
            case 2: // vertically moving platform
                return t.getImage("Images\\enemy1.png");
            default:
                return t.getImage("Images\\enemy1.png");
        }
    }
}
