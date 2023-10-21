import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class PlayerMovement implements KeyListener, ActionListener {
    //JFrame frame = new JFrame();
    ShapeDrawer gamePanel;

    boolean isHoldingA = false;
    boolean isHoldingD = false;
    //JFrame frame = new JFrame();
    JFrame gameFrame;

    Timer jumpTimer = new Timer(20, this);
    Timer gameTimer = new Timer(10, this);    

    int movementPixels = 5; //amount of pixels the player moves each tick to the sides

    //ShapeDrawer shapeDrawer = new ShapeDrawer();

    PlayerMovement(ShapeDrawer x, JFrame y) {
        gamePanel = x;
        gameFrame = y;
        //frame =  y;
    }

    int counter = 0;

    boolean jumped = false;

    public void run() {
        jumpTimer.start();
        gameFrame.addKeyListener(this);
        gameFrame.requestFocus();
    }

    public void jump() {
        counter += 1;
        
        if (counter >= 20) {
            gamePanel.y += Math.min(20, (counter - 20 + 1));
        } else {
            // checks if player is below 
            if (gamePanel.y - (20 - counter) <= 500) {
                gamePanel.y = 500;
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

                    gamePanel.generatePlatform(-gamePanel.jumpHeight * 20 - gamePanel.gameDistance,
                        -gamePanel.jumpHeight * 10 - gamePanel.gameDistance);
                }
            } else {
                gamePanel.y -= 20 - counter;
            }
        }
    }

    public void checkIfJump() {
        jumped = false;

        for (ArrayList<Integer> i : gamePanel.platformCoordinates) {
            if (!(counter >= 20)) { 
                continue; // checks if the player is falling down
            }
            // calculates the player's position after performing the upcoming movement
            int futureGamePanelY = gamePanel.y;
            futureGamePanelY += Math.min(20, counter - 20 + 1);

            // check if the player is currently above the platform and if the player will be
            // beneath it after peforming the upcoming movement
            if (!(i.get(1) >= gamePanel.y + gamePanel.playerHeight
                && i.get(1) <= futureGamePanelY + gamePanel.playerHeight)) {
                continue;
            }

            // check if player x coordinate is close to platform x coordinate
            if (!(gamePanel.x + gamePanel.playerWidth - i.get(0) <= 60 + gamePanel.playerWidth 
                && gamePanel.x + gamePanel.playerWidth - i.get(0) > 0)) {
                continue;
            }

            gamePanel.y = i.get(1) - gamePanel.platformHeight - gamePanel.playerHeight;
            counter = 0;
            jumped = true;
            gamePanel.repaint();
            break;
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
