import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class Enemies {
    int enemyX;
    int enemyY;
    int enemyWidth;
    int enemyHeight;
    JFrame gameFrame;
    ShapeDrawer gamePanel;

    Enemies(int x, int y, JFrame gameFrame, ShapeDrawer gamePanel) {
        this.enemyX = x;
        this.enemyY = y;
        this.gameFrame = gameFrame;
        this.gamePanel = gamePanel;
        this.enemyWidth = 100;
        this.enemyHeight = 50;
    }

    public void randomPosition(Random rand) {
        enemyX = rand.nextInt(gameFrame.getWidth());
        System.out.println(enemyX);
        enemyY = -rand.nextInt(gamePanel.jumpHeight * 10, gamePanel.jumpHeight * 20);
    }
}
