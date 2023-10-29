import java.util.Random;
import javax.swing.*;

/**
 * Enemies which kill the player upon collision.
 */
public class Enemies {
    int enemyX;
    int enemyY;
    int enemyWidth;
    int enemyHeight;
    JFrame gameFrame;
    ShapeDrawer gamePanel;

    /**
     * Constructor.
     * @param x x coordinate.
     * @param y y coordinate.
     * @param gameFrame game frame.
     * @param gamePanel game panel.
     */
    Enemies(int x, int y, JFrame gameFrame, ShapeDrawer gamePanel) {
        this.enemyX = x;
        this.enemyY = y;
        this.gameFrame = gameFrame;
        this.gamePanel = gamePanel;
        this.enemyWidth = 100;
        this.enemyHeight = 50;
    }

    /**
     * Sets a random position for the enemy.
     * @param rand Random value with a preset seed.
     */
    public void randomPosition(Random rand) {
        enemyX = rand.nextInt(gameFrame.getWidth());
        System.out.println(enemyX);
        enemyY = -rand.nextInt(gamePanel.jumpHeight * 10, gamePanel.jumpHeight * 20);
    }
}
