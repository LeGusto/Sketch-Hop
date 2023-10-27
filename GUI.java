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
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

        startPanel = new JPanel(new FlowLayout());
        //ImageIcon s = new ImageIcon("startButton.png");
        playButton = new JButton(new ImageIcon("startButton.png"));

        playButton.addActionListener(this);
        playButton.setOpaque(false);
        playButton.setContentAreaFilled(false);
        playButton.setBorderPainted(false);
        playButton.setFocusPainted(false);
        //JButton playButton2 = new JButton("sakfdjdaskfjlskad");
        //JButton playButton3 = new JButton("cc vmxnczvx");

        spongebob = new ImageIcon("coolSpongebob.jpg");
        label = new JLabel(spongebob, JLabel.CENTER);
        JLabel d = new JLabel("Sketch Hop");

        startPanel.add(playButton, BorderLayout.SOUTH);
        
    
        //playButton.setBounds(500, 500, 500, 500);
        //panel.add(playButton2);
        //panel.add(playButton3);

        frame.add(startPanel, BorderLayout.SOUTH);
        frame.add(label);
        frame.add(d);
        

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize((int) size.getWidth(), (int) size.getHeight());
        frame.setMinimumSize(size);
        frame.setVisible(true);

        //gamePanel.setLayout(null);

        gamePanel.setVisible(false);
        frame.add(gamePanel);
    }

    /**
     * Hides the main menu, displays the game panel, pregenerates some platforms
     * and initiates player movement.
     */
    public void playGame() {
        startPanel.setVisible(false);
        playButton.setVisible(false);
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