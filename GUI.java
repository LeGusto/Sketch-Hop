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

    GUI() {
        frame = new JFrame("game");
        gamePanel = new ShapeDrawer(frame);
        movement = new PlayerMovement(gamePanel, frame);
        platforms = new Platforms(gamePanel, frame);
    }

    void startGame() {
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
        frame.setVisible(true);

        gamePanel.setBackground(new Color(0, 200, 250));
        gamePanel.setVisible(false);
        frame.add(gamePanel);
    }

    void playGame() {
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

    @Override
    public void actionPerformed(ActionEvent e) {
        playGame();
    }
    
}