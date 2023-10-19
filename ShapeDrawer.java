import java.awt.*;
import java.nio.channels.Pipe;
import java.util.Random;
import javax.swing.*;
import java.util.ArrayList;


/**
 * Poep.
 */
public class ShapeDrawer extends JPanel {
    int x = 100;
    int y = 500;
    int playerHeight;
    int platformHeight;
    int playerWidth;
    JFrame gameFrame;
    //JPanel gamePanel;

    Random rand = new Random(2);

    // dumb data structuring
    ArrayList<ArrayList<Integer>> platformCoordinates;

    int jumpHeight = 95;

    ShapeDrawer(JFrame gameFrame) {
        this.gameFrame = gameFrame;
        this.platformCoordinates = new ArrayList<ArrayList<Integer>>(); 
        this.playerHeight = 55;
        this.playerWidth = 34;
        this.platformHeight = 15;
    }
    
    public void generatePlatform(int boundY) {
        for (int i = gameFrame.getHeight(); i > boundY; i -= jumpHeight) {
            for (int j = 0; j < 5; j++) {
                int randomX = rand.nextInt(gameFrame.getWidth());
                int randomY = i - rand.nextInt(jumpHeight);

                
                ArrayList<Integer> list = new ArrayList<>();
                list.add(randomX);
                list.add(randomY);
                platformCoordinates.add(list);
                

                //System.out.println(randomX + " " + randomY);

                
                /*Platform platform = new Platform(randomX, randomY, gameFrame);
                platforms.add(platform);
                //platform.setSize(30, 10);
                gameFrame.add(platform);
                platform.paintComponent(platform.getGraphics());
                System.out.println("working");
                platform.setVisible(false);
                platform.paintComponent(platform.getGraphics());*/
            }
        }
    }

    /**
     * Poep.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);        

        g.setColor(new Color(0, 0, 0));
        for (ArrayList<Integer> i : platformCoordinates) {
            g.fillRect(i.get(0), i.get(1), 60, 15);
        }

        g.fillRect(x, y, playerWidth, playerHeight);
        int i = 0;
        i++;
        
        if (i % 2 == 0) {
            setBackground(new Color(0, 0, 0));
        } else {
            setBackground(new Color(255, 255, 255));
        }        
    }
}
