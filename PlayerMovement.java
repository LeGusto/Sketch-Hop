import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class PlayerMovement implements KeyListener, ActionListener {
    ShapeDrawer gamePanel;
    Platforms platforms;

    boolean isHoldingA = false;
    boolean isHoldingD = false;
    boolean boosting = false; //if player is boosted by a booster platform
    JFrame gameFrame;

    Timer jumpTimer = new Timer(20, this);
    Timer gameTimer = new Timer(10, this);
    

    int movementPixels = 5; //amount of pixels the player moves each tick to the sides
    int counter;

    /**
     * Constructor.
     * @param panel game panel
     * @param frame game frame
     */
    PlayerMovement(ShapeDrawer panel, JFrame frame) {
        gamePanel = panel;
        gameFrame = frame;
        platforms = new Platforms(gamePanel, gameFrame);
        this.counter = 0;
    }

    boolean jumped = false;

    public void run() {
        jumpTimer.start();
        gameFrame.addKeyListener(this);
        gameFrame.requestFocus();
    }

    public int calculateMoveValue() {
        int moveValue;

        if (counter < 0) {
            moveValue = counter;
        } else {
            moveValue = counter;
        }

        return moveValue;
    }

    public void restartGame() {
        gamePanel.platformData.clear();
        gamePanel.gameScore = 0;
        gamePanel.gameDistance = -250;
        gamePanel.x = 100;
        gamePanel.y = 500;
        platforms.generateRandomPlatforms(-gamePanel.jumpHeight * 20, gameFrame.getHeight());

        // create a platform for the player to stand on in the beginning
        platforms.generatePlatform(gamePanel.x, gamePanel.y + 100, 0);

        gameTimer.start();
        jumpTimer.start();

        
    }

    /**
     * Player performs a jump and updates screen.
     */
    public void jump() {
        counter += 1;

        if (counter >= 20) {
            gamePanel.y += Math.min(20, (calculateMoveValue() - 20 + 1));
            if (gamePanel.y > gameFrame.getHeight() + 200) {
                gameTimer.stop();
                jumpTimer.stop();
                restartGame();
            }
        } else {

            // checks if player is below 
            if (gamePanel.y - (20 - calculateMoveValue()) <= 500) {
                gamePanel.y = 500;
                platforms.lowerScreen(calculateMoveValue());
            } else {
                System.out.println(counter);
                gamePanel.y -= 20 - calculateMoveValue();
            }
        }
    }

    /**
     * Check if the player is going downwards and handle collision with platforms.
     */
    public void checkIfJump() {
        jumped = false;

        // loops over every platform
        for (int j = 0; j < gamePanel.platformData.size(); j++) {

            Platform i = gamePanel.platformData.get(j);
            if (!(counter >= 20)) { 
                continue; // checks if the player is falling down
            }

            // calculates the player's position after performing the upcoming movement
            int futureGamePanelY = gamePanel.y;
            futureGamePanelY += Math.min(20, calculateMoveValue() - 20 + 1);

            // account for moving platforms
            int futurePlatformHeight = i.y;
            Boolean futureDirection = i.direction;
            if (i.type == 2) {
                if (platforms.checkDirection(i)) {
                    futureDirection = !futureDirection;
                }

                if (!futureDirection) {
                    futurePlatformHeight += platforms.platformSpeed;
                } else {
                    futurePlatformHeight -= platforms.platformSpeed;
                }
            }

            // check if the player is currently above the platform and if the player will be
            // beneath it after peforming the upcoming movement
            if (!(i.y >= gamePanel.y + gamePanel.playerHeight
                && futurePlatformHeight <= futureGamePanelY + gamePanel.playerHeight)) {
                continue;
            }

            // check if player x coordinate is close to platform x coordinate
            if (!(gamePanel.x + gamePanel.playerWidth - i.x <= 60 + gamePanel.playerWidth 
                && gamePanel.x + gamePanel.playerWidth - i.x > 0)) {
                continue;
            }

            if (i.type == 4) {
                boosting = true;
                counter = -20;
            } else if (i.type == 3) {
                gamePanel.platformData.remove(i);
                counter = 0;
            } else {
                counter = 0;
            }
            gamePanel.y = i.y - gamePanel.platformHeight - gamePanel.playerHeight;
            jumped = true;
            gamePanel.repaint();
            break;
        }
    }
     
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == gameTimer) {
            if (isHoldingA && gamePanel.x > 0) {   
                gamePanel.x -= movementPixels;
            }

            if (isHoldingD && (gamePanel.x + 30) < gameFrame.getWidth()) {
                gamePanel.x += movementPixels;
            }
        }

        if (e.getSource() == jumpTimer) { //makes the player jump constantly

            checkIfJump();

            if (!jumped) {
                jump();
            }

            platforms.movePlatforms();
        }

        gamePanel.repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //System.out.println("Hi");
        if (e.getKeyCode() == 97 || e.getKeyCode() == 65 && !isHoldingA && gamePanel.x > 0) { 
            gamePanel.x -= movementPixels;
            gamePanel.repaint();
        }
        
        if (e.getKeyCode() == 100 || e.getKeyCode() == 68
            && !isHoldingD && (gamePanel.x + 30) < gameFrame.getWidth()) {
            gamePanel.x += movementPixels;
            gamePanel.repaint();
        }

        // when the player presses a or A
        isHoldingA = (e.getKeyCode() == 97 || e.getKeyCode() == 65);
        // when the player presses d or D
        isHoldingD = (e.getKeyCode() == 100 || e.getKeyCode() == 68);

        
        if (isHoldingA || isHoldingD) {
            gameTimer.start();
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == 97 || e.getKeyCode() == 65) {
            isHoldingA = false;
        } else if (e.getKeyCode() == 100 || e.getKeyCode() == 68) {
            isHoldingD = false;
        }

        if (!isHoldingA && !isHoldingD) {
            gameTimer.stop();
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    
}
