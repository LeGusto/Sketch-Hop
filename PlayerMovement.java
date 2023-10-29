import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import javax.swing.*;

public class PlayerMovement implements KeyListener, ActionListener, MouseListener {
    ShapeDrawer gamePanel;
    Platforms platforms;

    boolean isHoldingA = false;
    boolean isHoldingD = false;
    JFrame gameFrame;
    JButton restartButton;
    

    Timer jumpTimer = new Timer(20, this);
    Timer gameTimer = new Timer(10, this);
    Timer shootTimer = new Timer(200, this);
    
    ArrayList<Integer> highscores = new ArrayList<Integer>();
    File scoreFile = new File("high scores.txt");

    int movementPixels = 5; //amount of pixels the player moves each tick to the sides
    static int counter = 0;

    /**
     * Constructor.
     * @param panel game panel
     * @param frame game frame
     */
    PlayerMovement(ShapeDrawer panel, JFrame frame) {
        gamePanel = panel;
        gameFrame = frame;
        platforms = new Platforms(gamePanel, gameFrame);
    }

    boolean shootCooldown = false;
    boolean jumped = false;
    boolean died = false;

    public void run() {
        jumpTimer.start();
        gameFrame.addKeyListener(this);
        gameFrame.requestFocus();

        gamePanel.setSize(gameFrame.getSize());
        gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.LINE_AXIS));

        restartButton = new JButton(new ImageIcon("Images\\restartButton.png"));
        //restartButton.setLocation(500, 500);
        restartButton.addActionListener(this);
        restartButton.addMouseListener(this);
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

    // public void saveScores() {
    //     try {
    //         File scoreFile = new File("highscores.txt");
    //         Scanner sc = new Scanner(scoreFile);

    //         while (sc.hasNextInt()) {
    //             highscores.add(sc.nextInt());
    //         }
    //         sc.close();

    //         Collections.sort(highscores);

    //         for (int e : highscores) {
    //             if (gamePanel.gameScore > e) {
    //                 highscores.set(highscores.indexOf(e), gamePanel.gameScore);
    //                 break;
    //             }
    //         }

    //         Collections.sort(highscores);

    //         scoreFile.delete();
    //         scoreFile.createNewFile();

    //         FileWriter writer = new FileWriter("highscores.txt");

    //         for (int e : highscores) {
    //             writer.write(e);
    //         }
            
    //         writer.close();
            
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }

    /**
     * Resets the game.
     */
    public void restartGame() {
        gamePanel.platformData.clear();
        gamePanel.enemyData.clear();
        gamePanel.bulletData.clear();

        //saveScores();

        gamePanel.gameScore = 0;
        gamePanel.gameDistance = -250;
        gamePanel.playerX = 100;
        gamePanel.playerY = 500;
        died = false;
        counter = 0;
        platforms.generateRandomPlatforms(-gamePanel.jumpHeight * 20, gameFrame.getHeight());

        // create a platform for the player to stand on in the beginning
        platforms.generatePlatform(gamePanel.playerX, gamePanel.playerY + 100, 0);

        //gameTimer.start();
        //jumpTimer.start();
        gameFrame.requestFocus();
        gameTimer.start();

        
    }

    /**
     * Player performs a jump and updates screen.
     */
    public void jump() {
        counter += 1;

        if (counter >= 20) {
            gamePanel.playerY += Math.min(20, (counter - 20 + 1));
            if (gamePanel.playerY > gameFrame.getHeight() + 200) {
                //gameTimer.stop();
                //jumpTimer.stop();
                //restartButton.setLocation(gameFrame.getWidth() / 2, gameFrame.getHeight() / 2);
                restartButton.setVisible(true);
                gamePanel.repaint();
            }
        } else {

            // checks if player is below 
            if (gamePanel.playerY - (20 - counter) <= 500) {
                gamePanel.playerY = 500;
                platforms.lowerScreen(counter);
                for (Bullets i : gamePanel.bulletData) {
                    i.y += 20 - counter;
                }
                for (Enemies i : gamePanel.enemyData) {
                    i.enemyY += 20 - counter;
                    //System.out.println(i.y);
                }

            } else {
                //System.out.println(counter);
                gamePanel.playerY -= 20 - counter;
            }
        }
    }

    /**
     * Check if the player is going downwards and handle collision with platforms.
     */
    public boolean checkIfJump() {
        // loops over every platform
        for (int j = 0; j < gamePanel.platformData.size(); j++) {

            Platform i = gamePanel.platformData.get(j);
            if (!(counter >= 20)) { 
                continue; // checks if the player is falling down
            }

            // calculates the player's position after performing the upcoming movement
            int futureGamePanelY = gamePanel.playerY;
            futureGamePanelY += Math.min(20, counter - 20 + 1);

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
            if (!(i.y >= gamePanel.playerY + gamePanel.playerHeight
                && futurePlatformHeight <= futureGamePanelY + gamePanel.playerHeight)) {
                continue;
            }

            // check if player x coordinate is close to platform x coordinate
            if (!(gamePanel.playerX + gamePanel.playerWidth - i.x <= 60 + gamePanel.playerWidth 
                && gamePanel.playerX + gamePanel.playerWidth - i.x > 0)) {
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
            gamePanel.playerY = i.y - gamePanel.platformHeight - gamePanel.playerHeight;
            gamePanel.repaint();
            return true;
        }

        return false;
    }

    /**
     * Checks if the player is colliding with an enemy.
     * @return whether the player has touched an enemy.
     */
    public Boolean checkIfDeath() {
        Boolean ans = false;
        for (Enemies i : gamePanel.enemyData) {
            if (!(gamePanel.playerY - i.enemyY > -gamePanel.playerHeight
                && gamePanel.playerY - i.enemyY <= i.enemyHeight)) {
                continue;
            }
            if (!((gamePanel.playerX - i.enemyX > -gamePanel.playerWidth)
                && gamePanel.playerX - i.enemyX < i.enemyWidth)) {
                continue;
            }

            ans = true;
            break;
        }

        return ans;
    }

    /**
     * Displays an animation of the player dying after touching an enemy.
     */
    void deathAnimation() {
        counter = counter + 1;
        if (counter <= 15) {
            //gamePanel.playerY -= (50 / (1 + counter));
            gamePanel.playerY -= 15 - counter;
            gamePanel.playerX -= 3;
        } else {
            gamePanel.playerY -= 15 - counter;
            //gamePanel.playerY += 25 - (50 / (1 + counter - 15));
            gamePanel.playerX -= 3;
        }
    }
     
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == gameTimer) {
            if (isHoldingA && gamePanel.playerX > 0) {   
                gamePanel.playerX -= movementPixels;
            }

            if (isHoldingD && (gamePanel.playerX + 30) < gameFrame.getWidth()) {
                gamePanel.playerX += movementPixels;
            }
        } else if (e.getSource() == jumpTimer) { // makes the player jump constantly

            if (died && counter < 60) { // if the player died, overrides usual jump function.
                deathAnimation();
                platforms.movePlatforms();
                for (Bullets i : gamePanel.bulletData) {
                    i.moveBullet();
                    i.checkCollisions(gamePanel.bulletData, gamePanel.enemyData);
                }
                gamePanel.repaint();
                return;
            } else if (died) { // if the death animation has finished.
                restartButton.setVisible(true);
                gamePanel.repaint();
            }

            if (checkIfDeath()) { // checks if the player collides with an enemy
                gameTimer.stop();
                died = true;
                isHoldingA = false;
                isHoldingD = false;
                counter = 0;
                return;
            }

            if (!checkIfJump()) {
                jump();
            }

            platforms.movePlatforms();
            for (int i = 0; i < gamePanel.bulletData.size(); i++) {
                Bullets bullet = gamePanel.bulletData.get(i);
                bullet.moveBullet();
                bullet.checkCollisions(gamePanel.bulletData, gamePanel.enemyData);
            }
        } else if (e.getSource() == shootTimer) { //make the player able to shoot after a cooldown.
            shootTimer.stop();
            shootCooldown = false;
        } else { // when the source of the method call is the restart button
            restartGame();
            restartButton.setVisible(false);
        }

        gamePanel.repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (died) {
            return;
        }

        if (e.getKeyCode() == 97 || e.getKeyCode() == 65 && !isHoldingA && gamePanel.playerX > 0) { 
            gamePanel.playerX -= movementPixels;
            gamePanel.repaint();
            isHoldingA = true;
        }
        
        if (e.getKeyCode() == 100 || e.getKeyCode() == 68
            && !isHoldingD && (gamePanel.playerX + 30) < gameFrame.getWidth()) {
            gamePanel.playerX += movementPixels;
            gamePanel.repaint();
            isHoldingD = true;
        }

        if (shootCooldown) { //returns if shooting is on cooldown.
            return;
        }

        if (e.getKeyCode() == 49) {
            Bullets bullet = new Bullets(0, gamePanel.playerX, gamePanel.playerY);
            bullet.x = bullet.x - bullet.size / 2;
            gamePanel.bulletData.add(bullet);
            shootCooldown = true;
            shootTimer.start();
        }

        if (e.getKeyCode() == 50) {
            Bullets bullet = new Bullets(1, gamePanel.playerX, gamePanel.playerY);
            bullet.x = bullet.x + gamePanel.playerWidth / 2 - bullet.size / 2;
            gamePanel.bulletData.add(bullet);
            shootCooldown = true;
            shootTimer.start();
        }

        if (e.getKeyCode() == 51) {
            Bullets bullet = new Bullets(2, gamePanel.playerX, gamePanel.playerY);
            bullet.x = bullet.x + gamePanel.playerWidth;
            gamePanel.bulletData.add(bullet);
            shootCooldown = true;
            shootTimer.start();
        }
        
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

    @Override
    public void mouseEntered(MouseEvent e) {
        restartButton.setIcon(new ImageIcon("Images\\restartbuttonHovered.png"));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        restartButton.setIcon(new ImageIcon("Images\\restartbutton.png"));
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        restartButton.setIcon(new ImageIcon("Images\\restartbuttonPressed.png"));
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        restartButton.setIcon(new ImageIcon("Images\\restartbuttonHovered.png"));
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    
}
