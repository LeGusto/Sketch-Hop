import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class PlayerMovement implements KeyListener, ActionListener {
    //JFrame frame = new JFrame();
    ShapeDrawer gamePanel;
    Platforms platforms;

    boolean isHoldingA = false;
    boolean isHoldingD = false;
    boolean boosting = false; //if player is boosted by a booster platform
    //JFrame frame = new JFrame();
    JFrame gameFrame;

    Timer jumpTimer = new Timer(20, this);
    Timer gameTimer = new Timer(10, this);
    

    int movementPixels = 5; //amount of pixels the player moves each tick to the sides
    int counter;

    //ShapeDrawer shapeDrawer = new ShapeDrawer();

    PlayerMovement(ShapeDrawer x, JFrame y) {
        gamePanel = x;
        gameFrame = y;
        platforms = new Platforms(gamePanel, gameFrame);
        this.counter = 0;
        //frame =  y;
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

    /**
     * Player performs a jump and updates screen.
     */
    public void jump() {
        counter += 1;

        if (counter >= 20) {
            gamePanel.y += Math.min(20, (calculateMoveValue() - 20 + 1));
        } else {

            // checks if player is below 
            if (gamePanel.y - (20 - calculateMoveValue()) <= 500) {
                gamePanel.y = 500;
                platforms.lowerScreen(calculateMoveValue());
            } else {
                //if (!boosting) {
                System.out.println(counter);
                gamePanel.y -= 20 - calculateMoveValue();
                //}
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
            if (i.type == 2) {
                if (!i.direction) {
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

    public void movePlatforms() {
        for (Platform i : gamePanel.platformData) {
            if (i.type == 1) { // horizontally moving platforms
                if (i.y >= i.startPosition) {
                    i.direction = true;
                } else if ((i.y <= i.endPosition)) {
                    i.direction = false;
                }

                if (i.direction) {
                    i.x -= 5;
                } else {
                    i.x += 5;
                }
            }

            if (i.type == 2) { // vertically moving platforms
                if (i.y >= i.startPosition) {
                    i.direction = true;
                } else if ((i.y <= i.endPosition)) {
                    i.direction = false;
                }
                
                if (i.direction) {
                    i.y -= 5;
                } else {
                    i.y += 5;
                }
            }
        }
    }
     
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == gameTimer) {
            if (isHoldingA && gamePanel.x > 0) {
                //System.out.println("pressing A");
            
                gamePanel.x -= movementPixels;
                //gamePanel.paintComponent(gamePanel.getGraphics());
            }

            if (isHoldingD && (gamePanel.x + 30) < gameFrame.getWidth()) {
                gamePanel.x += movementPixels;
                //gamePanel.paintComponent(gamePanel.getGraphics());
                //System.out.println(gamePanel.x + " " + gameFrame.getWidth());
            }
        }

        if (e.getSource() == jumpTimer) { //makes the player jump constantly
            //System.out.println("sfklads");

            checkIfJump();

            if (!jumped) {
                jump();
            }
            //gamePanel.paintComponent(gamePanel.getGraphics());

            movePlatforms();
        }

        gamePanel.repaint(); // I don't know why, but repaint stops the flickering...
        //gamePanel.paintComponent(gamePanel.getGraphics());
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
