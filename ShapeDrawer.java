import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;


/**
 * Poep.
 */
public class ShapeDrawer extends JPanel {
    int x = 100;
    int y = 500;
    int playerHeight;
    int platformHeight;
    int platformWidth;
    int playerWidth;
    int gameDistance; //track how far the player has gotten
    JFrame gameFrame;
    //JPanel gamePanel;

    Random rand = new Random(3);

    // dumb data structuring
    ArrayList<ArrayList<Integer>> platformCoordinates;

    int jumpHeight = 95;

    /**
     * .
     */
    ShapeDrawer(JFrame gameFrame) {
        this.gameFrame = gameFrame;
        this.platformCoordinates = new ArrayList<ArrayList<Integer>>(); 
        this.playerHeight = 55;
        this.playerWidth = 34;
        this.platformHeight = 15;
        this.gameDistance = -250;
        this.platformWidth = 60;
    }
    
    /**
     * .
     */
    public void generatePlatform(int lowerBoundY, int upperBoundY) {
        for (int i = upperBoundY; i > lowerBoundY; i -= jumpHeight) {

            for (int j = 0; j < gameFrame.getWidth(); j += 0) {
                int randomX = rand.nextInt(j, j + 200);
                int randomY = i - rand.nextInt(jumpHeight - platformHeight - 5);

                
                ArrayList<Integer> list = new ArrayList<>();
                list.add(randomX);
                list.add(randomY);
                
                int type = rand.nextInt(5);

                list.add(type);

                platformCoordinates.add(list);

                j = randomX + platformWidth + 10;
                
            }
        }
    }

    /**
     * .
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);        

        // loop over platforms and paint them
        g.setColor(new Color(0, 0, 0));
        for (ArrayList<Integer> i : platformCoordinates) {
            switch (i.get(2)) {
                case 0: // normal platform
                    g.fillRect(i.get(0), i.get(1), platformWidth, platformHeight);
                    break;
                case 1: // horizontally moving platform
                    g.fillRect(i.get(0), i.get(1), platformWidth, platformHeight);
                    break;
                case 2: // vertically moving platform
                    g.fillRect(i.get(0), i.get(1), platformWidth, platformHeight);
                    break;
                case 3: // breakable platform
                    g.fillRect(i.get(0), i.get(1), platformWidth, platformHeight);
                    break;
                case 4: // booster platform
                    g.fillRect(i.get(0), i.get(1), platformWidth, platformHeight);
                    break;
                default:
                    g.fillRect(i.get(0), i.get(1), platformWidth, platformHeight);
            }
            
        }
        g.fillRect(x, y, playerWidth, playerHeight);
    
    }
}
