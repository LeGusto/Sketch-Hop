import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

class GUI implements ActionListener {

    /**
     * Shut the fuck up.
     */
    JFrame frame; 
    JPanel startPanel;
    JButton playButton;
    JLabel label;
    ImageIcon spongebob;
    PlayerMovement movement;
    Boolean lost = false;
    ShapeDrawer gamePanel;
    Platforms platforms;

    /**
     * Constructor.
     */
    GUI() {
        this.frame = new JFrame("game");
        this.gamePanel = new ShapeDrawer(frame);
        this.movement = new PlayerMovement(gamePanel, frame);
        this.platforms = new Platforms(gamePanel, frame);
    }

    /**
     * Creates GUI elements and displays the main menu.
     */
    public void startGame() {
        startPanel = new JPanel(new FlowLayout());
        playButton = new JButton("Start");

        playButton.addActionListener(this);
        //JButton playButton2 = new JButton("sakfdjdaskfjlskad");
        //JButton playButton3 = new JButton("cc vmxnczvx");

        spongebob = new ImageIcon("coolSpongebob.jpg");
        label = new JLabel(spongebob, JLabel.CENTER);

        startPanel.add(playButton);
        //panel.add(playButton2);
        //panel.add(playButton3);

        frame.add(startPanel, BorderLayout.SOUTH);
        frame.add(label);

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize((int) size.getWidth(), (int) size.getHeight());
        frame.setMinimumSize(new Dimension(1920, 1080));
        frame.setVisible(true);

        //gamePanel.setLayout(null);
        gamePanel.setBackground(new Color(0, 200, 250));
        gamePanel.setVisible(false);
        frame.add(gamePanel);
    }

    /**
     * Hides the main menu, displays the game panel, pregenerates some platforms
     * and initiates player movement.
     */
    public void playGame() {
        startPanel.setVisible(false);
        label.setVisible(false);
        gamePanel.setVisible(true);

        //PlayerMovement something = new PlayerMovement();
        //gamePanel = something.shapeDrawer;
        //frame.addKeyListener(something);
        //something.run();
        movement.run();
        platforms.generateRandomPlatforms(-gamePanel.jumpHeight * 20, frame.getHeight());

        // create a platform for the player to stand on in the beginning
        platforms.generatePlatform(gamePanel.x, gamePanel.y + 100, 0);

    }

    public void loseGame() {
        for (int i = 0; i < gamePanel.platformData.size(); i++) {
            gamePanel.platformData.remove(i);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        playGame();
    }
    
}