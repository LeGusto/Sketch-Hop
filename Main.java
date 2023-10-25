import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * .
 */
public class Main {

    GUI gui = new GUI();

    public void run() {
        gui.startGame();
    }
    
    public static void main(String[] args) {
        new Main().run();
    }
}