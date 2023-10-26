import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.border.Border;

public class PlayerMovement implements KeyListener, ActionListener {
    ShapeDrawer gamePanel;
    Platforms platforms;

    boolean isHoldingA = false;
    boolean isHoldingD = false;
    JFrame gameFrame;
    JButton restartButton;

    Timer jumpTimer = new Timer(20, this);
    Timer gameTimer = new Timer(10, this);
    
    ArrayList<Integer> highscores = new ArrayList<Integer>();
    File scoreFile = new File("high scores.txt");

    int movementPixels = 5; //amount of pixels the player moves each tick to the sides
    static int counter;

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

        gamePanel.setSize(gameFrame.getSize());
        System.out.println(gamePanel.getSize().getHeight());
        gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.LINE_AXIS));

        restartButton = new JButton(new ImageIcon("restartButton.png"));
        //restartButton.setLocation(500, 500);
        restartButton.addActionListener(this);
        gamePanel.add(Box.createHorizontalGlue());
        gamePanel.add(Box.createVerticalGlue());
        gamePanel.add(restartButton);
        gamePanel.add(Box.createHorizontalGlue());
        gamePanel.add(Box.createVerticalGlue());
        
        restartButton.setOpaque(false);
        restartButton.setContentAreaFilled(false);
        restartButton.setBorderPainted(false);
        restartButton.setFocusPainted(false);
        //restartButton.
        restartButton.setVisible(false);
        //restartButton.setPreferredSize(new Dimension(100, 100));
        
        
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

    public void saveScores() {
        try {
            File scoreFile = new File("highscores.txt");
            Scanner sc = new Scanner(scoreFile);

            while (sc.hasNextInt()) {
                highscores.add(sc.nextInt());
            }
            sc.close();

            Collections.sort(highscores);

            for (int e : highscores) {
                if (gamePanel.gameScore > e) {
                    highscores.set(highscores.indexOf(e), gamePanel.gameScore);
                    break;
                }
            }

            Collections.sort(highscores);

            scoreFile.delete();
            scoreFile.createNewFile();

            FileWriter writer = new FileWriter("highscores.txt");

            for (int e : highscores) {
                writer.write(e);
            }
            
            writer.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void restartGame() {
        gamePanel.platformData.clear();

        saveScores();

        gamePanel.gameScore = 0;
        gamePanel.gameDistance = -250;
        gamePanel.x = 100;
        gamePanel.y = 500;
        platforms.generateRandomPlatforms(-gamePanel.jumpHeight * 20, gameFrame.getHeight());

        // create a platform for the player to stand on in the beginning
        platforms.generatePlatform(gamePanel.x, gamePanel.y + 100, 0);

        //gameTimer.start();
        //jumpTimer.start();
        gameFrame.requestFocus();

        
    }

    /**
     * Player performs a jump and updates screen.
     */
    public void jump() {
        counter += 1;

        if (counter >= 20) {
            gamePanel.y += Math.min(20, (calculateMoveValue() - 20 + 1));
            if (gamePanel.y > gameFrame.getHeight() + 200) {
                //gameTimer.stop();
                //jumpTimer.stop();
                //restartButton.setLocation(gameFrame.getWidth() / 2, gameFrame.getHeight() / 2);
                restartButton.setVisible(true);
                gamePanel.repaint();
            }
        } else {

            // checks if player is below 
            if (gamePanel.y - (20 - calculateMoveValue()) <= 500) {
                gamePanel.y = 500;
                platforms.lowerScreen(calculateMoveValue());
            } else {
                //System.out.println(counter);
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

            if (i.type == 4) { // boost player
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
        } else if (e.getSource() == jumpTimer) { //makes the player jump constantly

            checkIfJump();
            System.out.println(counter);

            if (!jumped) {
                jump();
            }

            platforms.movePlatforms();

        } else {
            restartGame();
            restartButton.setVisible(false);
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
