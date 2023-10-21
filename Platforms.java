import java.awt.Shape;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class Platforms {
    ShapeDrawer gamePanel;
    JFrame gameFrame;

    Random rand = new Random(3);

    Platforms(ShapeDrawer gamePanel, JFrame gameFrame) {
        this.gamePanel = gamePanel;
        this.gameFrame = gameFrame;
    }

    /**
     * Creates and adds many platforms with semi-randomized coordinates and types.
     * @param lowerBoundY Y coordinate where the generation should stop.
     * @param upperBoundY Y coordinate where the generation should end.
     */
    public void generateRandomPlatforms(int lowerBoundY, int upperBoundY) {
        for (int i = upperBoundY; i > lowerBoundY; i -= gamePanel.jumpHeight) {

            for (int j = 0; j < gameFrame.getWidth(); j += 0) {
                int randomX = rand.nextInt(j, j + 200);
                int randomY = i - rand.nextInt(gamePanel.jumpHeight - gamePanel.platformHeight - 5);

                generatePlatform(randomX, randomY, rand.nextInt(5));

                j = randomX + gamePanel.platformWidth + 10;
                
            }
        }
    }

    /**
     * Generates and adds a platform to the platform ArrayList.
     * @param x x coordinte.
     * @param y y coordinate.
     * @param type type of platform.
     */
    public void generatePlatform(int x, int y, int type) {
        ArrayList<Integer> initPlatform = new ArrayList<Integer>();
        initPlatform.add(x);
        initPlatform.add(y);
        initPlatform.add(type);
        gamePanel.platformCoordinates.add(initPlatform);
    }

    /**
        Moves down every platform and pregenerates more if needed, keeps track of game distance.
        @param counter The current stage of the players movement in playerMovement.
    */
    public void lowerScreen(int counter) {
        for (ArrayList<Integer> i : gamePanel.platformCoordinates) {
            i.set(1, i.get(1) + 20 - counter);
        }

        // removes platform if it is not on screen anymore. Not possible with
        // for-each loop, because it would delete elements it was iterating over
        for (int i = 0; i < gamePanel.platformCoordinates.size(); i++) {
            if (gamePanel.platformCoordinates.get(i).get(1) > gameFrame.getHeight()) {

                gamePanel.platformCoordinates.remove(i);
                i--;
            }
        }

        gamePanel.gameDistance += 20 - counter;
        // pregenerates platforms if the player covers a certain distance
        if (Math.abs(gamePanel.gameDistance - gamePanel.jumpHeight * 10) <= 100) {
            gamePanel.gameDistance -= gamePanel.jumpHeight * 10;

            generateRandomPlatforms(-gamePanel.jumpHeight * 20 - gamePanel.gameDistance,
                    -gamePanel.jumpHeight * 10 - gamePanel.gameDistance);
        }
    }
}