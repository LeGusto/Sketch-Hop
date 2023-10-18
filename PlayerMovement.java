import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PlayerMovement implements KeyListener, ActionListener {
    //JFrame frame = new JFrame();
    ShapeDrawer gamePanel;

    boolean isHoldingA = false;
    boolean isHoldingD = false;
    JFrame frame = new JFrame();
    JFrame gameFrame;

    Timer jumpTimer = new Timer(10, this);
    Timer gameTimer = new Timer(10, this);    

    int movementPixels = 5; //amount of pixels the player moves each tick to the sides

    //ShapeDrawer shapeDrawer = new ShapeDrawer();

    PlayerMovement(ShapeDrawer x, JFrame y) {
        gamePanel = x;
        gameFrame = y;
        //frame =  y;
    }

    int counter = 0;

    public void run() {
        jumpTimer.start();
        System.out.println("Hsdfkalfds");
        frame.addKeyListener(this);
        frame.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == gameTimer) {
            if (isHoldingA && gamePanel.x > 0) {
                //System.out.println("pressing A");
            
                gamePanel.x -= movementPixels;
                gamePanel.paintComponent(gamePanel.getGraphics());
            }

            if (isHoldingD && (gamePanel.x + 30) < gameFrame.getWidth()) {

                
                gamePanel.x += movementPixels;
                gamePanel.paintComponent(gamePanel.getGraphics());
                //System.out.println(gamePanel.x + " " + gameFrame.getWidth());
            }
        }

        if (e.getSource() == jumpTimer) { //makes the player jump constantly
            //System.out.println("sfklads");
            counter += 1;
            if ((counter / 10) % 2 == 0) {
                gamePanel.y += (counter % 10 + 1) * Math.pow(-1, counter / 10);
            } else {
                gamePanel.y += (10 - counter % 10) * Math.pow(-1, counter / 10);
            }
            gamePanel.paintComponent(gamePanel.getGraphics());
        }
    }

    

    @Override
    public void keyPressed(KeyEvent e) {
        //System.out.println("Hi");
        if (e.getKeyCode() == 97 || e.getKeyCode() == 65 && !isHoldingA && gamePanel.x > 0) {
                
                gamePanel.x -= movementPixels;
                gamePanel.paintComponent(gamePanel.getGraphics());
            }
        
        if (e.getKeyCode() == 100 || e.getKeyCode() == 68 && !isHoldingD && (gamePanel.x + 30) < gameFrame.getWidth()) {
            gamePanel.x += movementPixels;
                gamePanel.paintComponent(gamePanel.getGraphics());
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
