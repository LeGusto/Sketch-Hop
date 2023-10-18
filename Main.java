import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Main {//implements KeyListener {
    JFrame frame = new JFrame();
    ShapeDrawer shapeDrawer = new ShapeDrawer();

    public void run() {
        GUI gui = new GUI();
        gui.startGame();
    }
    
    /*Main() {
        frame.setSize(300, 510);
        frame.addKeyListener(this);
        frame.add(shapeDrawer);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void keyPressed(KeyEvent e) {
        System.out.print(e.getKeyChar());
        if (e.getKeyCode() == 97 || e.getKeyCode() == 65) {
            shapeDrawer.x -= 10;
        }

        if (e.getKeyCode() == 100 || e.getKeyCode() == 68) {
            shapeDrawer.x += 10;
        }
        
        shapeDrawer.paintComponent(shapeDrawer.getGraphics());;
    }

    public void keyReleased(KeyEvent e) {

    }

    public void keyTyped(KeyEvent e) {
    }*/
    
    public static void main(String[] args) {
        new Main().run();
    }
}