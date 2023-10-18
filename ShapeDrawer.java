import java.awt.*;
import javax.swing.*;

/**
 * Poep.
 */
public class ShapeDrawer extends JPanel {
    int x = 100;
    int y = 100;

    /**
     * Poep.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);        

        g.setColor(new Color(0, 0, 0));
        g.fillRect(x, y, 20, 20);
    }
}
