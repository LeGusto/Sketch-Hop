import java.util.Random;
import javax.swing.JFrame;

public class Enemies {
    int x;
    int y;
    int width;
    int height;
    JFrame gameFrame;
    ShapeDrawer gamePanel;

    Enemies(int x, int y, JFrame gameFrame, ShapeDrawer gamePanel) {
        this.x = x;
        this.y = y;
        this.gameFrame = gameFrame;
        this.gamePanel = gamePanel;
        this.width = 100;
        this.height = 50;
    }

    public void randomPosition(Random rand) {
        x = rand.nextInt(gameFrame.getWidth());
        System.out.println(x);
        y = -rand.nextInt(gamePanel.jumpHeight * 10, gamePanel.jumpHeight * 20);
    }
}
