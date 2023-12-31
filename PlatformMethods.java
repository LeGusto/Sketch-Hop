import java.util.Random;
import javax.swing.*;

/**
 * Methods for platform class objects.
 */
public class PlatformMethods {
    ShapeDrawer gamePanel;
    JFrame gameFrame;
    int platformSpeed;

    Random rand = new Random();

    /**
     * Constructor. Platformspeed is the speed at which moving platforms move.
     * @param gamePanel the game panel.
     * @param gameFrame the game frame.
     */
    PlatformMethods(ShapeDrawer gamePanel, JFrame gameFrame) {
        this.gamePanel = gamePanel;
        this.gameFrame = gameFrame;
        this.platformSpeed = 5;
    }

    /**
     * Picks a pseudo-random platform type and returns it. The frequency of special
     * platforms increases the higher the player goes.
     * @return randomly generated platform type.
     */
    private int pickPlatformType() {
        int platformType;
        int randomNum = rand.nextInt(10 + (gamePanel.gameScore / 1000) * 3);
        if (randomNum <= 7) {
            platformType = 0;
        } else {
            platformType = rand.nextInt(1, 5);
        }

        return platformType;
    }

    /**
     * Creates and adds many platforms with semi-randomized coordinates and types.
     * 
     * @param lowerBoundY Y coordinate where the generation should stop.
     * @param upperBoundY Y coordinate where the generation should start.
     */
    public void generateRandomPlatforms(int lowerBoundY, int upperBoundY) {
        // The speed at which platforms become more scarce the higher the score is
        int platformScarcity = Math.min((gamePanel.gameScore / 1000) * 50, 800);
        for (int i = upperBoundY; i > lowerBoundY; i -= gamePanel.jumpHeight) {
            for (int j = 0; j < gameFrame.getWidth(); j += 0) {
                int randomX = rand.nextInt(j, j + 200 + platformScarcity);
                int randomY = i - rand.nextInt(gamePanel.jumpHeight - gamePanel.platformHeight - 5);

                generatePlatform(randomX, randomY, pickPlatformType());

                j = randomX + gamePanel.platformWidth + 10;
            }
        }
    }

    /**
     * Generates and adds a platform to the platform ArrayList.
     * 
     * @param x    x coordinte.
     * @param y    y coordinate.
     * @param type type of platform.
     */
    public void generatePlatform(int x, int y, int type) {
        Platform initPlatform = new Platform(x, y, type);

        // for moving platforms, add boolean for direction of platform
        // and integer limit of  how far the platform should go
        if (type == 1 || type == 2) {
            int randomPosition = rand.nextInt(300);

            initPlatform.direction = false;
            if (initPlatform.type == 1) {
                initPlatform.startPosition = initPlatform.platformX + randomPosition;
                initPlatform.endPosition = initPlatform.platformX - (300 - randomPosition);
            } else {
                initPlatform.startPosition = initPlatform.platformY + randomPosition;
                initPlatform.endPosition = initPlatform.platformY - (300 - randomPosition);
            }
        }

        gamePanel.platformData.add(initPlatform);
    }

    /**
     * Moves down every platform and pregenerates more if needed, keeps track of
     * game distance.@param counter The current stage of the players movement in playerMovement.
     */
    public void lowerScreen(int counter) {
        for (Platform i : gamePanel.platformData) {
            i.platformY += 20 - counter;

            if (i.type == 2) {
                i.startPosition += 20 - counter;
                i.endPosition += 20 - counter;
            }
        }

        // removes platform if it is not on screen anymore. Not possible with
        // for-each loop, because it would delete elements it was iterating over
        for (int i = 0; i < gamePanel.platformData.size(); i++) {
            if (gamePanel.platformData.get(i).platformY > gameFrame.getHeight() + 200) {
                gamePanel.platformData.remove(i);
                i--;
            }
        }

        // removes bullet if it is not on screen anymre
        for (int i = 0; i < gamePanel.bulletData.size(); i++) {
            if (gamePanel.bulletData.get(i).bulletY <= 0) {
                gamePanel.bulletData.remove(i);
                i--;
            }
        }

        gamePanel.gameDistance += 20 - counter;
        gamePanel.gameScore += 20 - counter;

        // pregenerates platforms if the player covers a certain distance
        // pregenerates enemies if the player covers a certain distance
        if (Math.abs(gamePanel.gameDistance - gamePanel.jumpHeight * 10) <= 100) {
            gamePanel.gameDistance -= gamePanel.jumpHeight * 10;

            generateRandomPlatforms(-gamePanel.jumpHeight * 20 - gamePanel.gameDistance,
                    -gamePanel.jumpHeight * 10 - gamePanel.gameDistance);

            for (int i = 0; i < gamePanel.gameScore / 2000; i += 1) {
                Enemy enemy = new Enemy(0, 0, gameFrame, gamePanel);
                enemy.randomPosition(rand);
                gamePanel.enemyData.add(enemy);
            }
        }
    }

    /**
     * Checks if the platform should change direction.
     * @param i platform that should be checked
     * @return boolean to change direction
     */
    public Boolean checkDirection(Platform i) {
        Boolean change = false;

        if (i.type == 1) { // horizontally moving platforms
            if (i.platformX >= i.startPosition || i.platformX <= i.endPosition) {
                change = true;
            }
        } else if (i.type == 2) { // vertically moving platforms
            if (i.platformY >= i.startPosition || i.platformY <= i.endPosition) {
                change = true;
            }
        }

        return change;
    }

    /**
      * Moves special moving platforms.
      */
    public void movePlatforms() {
        for (Platform i : gamePanel.platformData) {
            
            if (checkDirection(i)) { // change direction if necessary
                i.direction = !i.direction;
            }

            if (i.type == 1) { // horizontally moving platforms

                if (i.direction) {
                    i.platformX -= 5;
                } else {
                    i.platformX += 5;
                }
            }

            if (i.type == 2) { // vertically moving platforms

                if (i.direction) {
                    i.platformY -= 5;
                } else {
                    i.platformY += 5;
                }
            }
        }
    }
}