import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.concurrent.Flow;

import javax.swing.*;

class GUI implements ActionListener, MouseListener {

    JFrame frame; 
    ShapeDrawer startPanel;
    ShapeDrawer helpPanel;
    JButton playButton;
    JButton helpButton;
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
        this.gamePanel = new ShapeDrawer(frame, gamePanel);
        this.helpPanel = new ShapeDrawer(frame, gamePanel);
        this.movement = new PlayerMovement(gamePanel, frame);
        this.platforms = new Platforms(gamePanel, frame);
    }

    /**
     * Creates GUI elements and displays the main menu.
     */
    public void startGame() {
        startPanel = new ShapeDrawer(frame, gamePanel);
        //ImageIcon s = new ImageIcon("startButton.png");
        playButton = new JButton(new ImageIcon("Images\\startButton.png"));
        helpButton = new JButton(new ImageIcon("Images\\helpButton.png"));

        playButton.addActionListener(this);
        playButton.addMouseListener(this);
        playButton.setOpaque(false);
        playButton.setContentAreaFilled(false);
        playButton.setBorderPainted(false);
        playButton.setFocusPainted(false);
        //JButton playButton2 = new JButton("sakfdjdaskfjlskad");
        //JButton playButton3 = new JButton("cc vmxnczvx");

        helpButton.addActionListener(this);
        helpButton.addMouseListener(this);
        helpButton.setOpaque(false);
        helpButton.setContentAreaFilled(false);
        helpButton.setBorderPainted(false);
        helpButton.setFocusPainted(false);

        startPanel.setLayout(null);

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension playButtonSize = playButton.getPreferredSize();

        playButton.setBounds((int) (size.getWidth() - playButtonSize.width) / 2,
            350, playButtonSize.width, playButtonSize.height);
        startPanel.add(playButton);

        helpButton.setBounds((int) (size.getWidth() - playButtonSize.width) / 2,
            600, playButtonSize.width, playButtonSize.height);
        startPanel.add(helpButton);

        startPanel.setBackground(new Color(0, 200, 255));
        
        frame.add(startPanel);
    
        //playButton.setBounds(500, 500, 500, 500);
        //panel.add(playButton2);
        //panel.add(playButton3);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize((int) size.getWidth(), (int) size.getHeight());
        frame.setMinimumSize(size);
        frame.setVisible(true);

        //gamePanel.setLayout(null);

        gamePanel.setVisible(false);
        frame.add(gamePanel);
    }

    public void helpMenu() {
        startPanel.setVisible(false);
        playButton.setVisible(false);
        helpPanel.setVisible(true);
    }

    /**
     * Hides the main menu, displays the game panel, pregenerates some platforms
     * and initiates player movement.
     */
    public void playGame() {
        startPanel.setVisible(false);
        playButton.setVisible(false);
        gamePanel.setVisible(true);

        //PlayerMovement something = new PlayerMovement();
        //gamePanel = something.shapeDrawer;
        //frame.addKeyListener(something);
        //something.run();
        movement.run();
        platforms.generateRandomPlatforms(-gamePanel.jumpHeight * 20, frame.getHeight());

        // create a platform for the player to stand on in the beginning
        platforms.generatePlatform(gamePanel.playerX, gamePanel.playerY + 100, 0);
    }

    public void loseGame() {
        for (int i = 0; i < gamePanel.platformData.size(); i++) {
            gamePanel.platformData.remove(i);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playButton) {
            playGame();
        } else {
            helpMenu();
        }
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == playButton) {
            playButton.setIcon(new ImageIcon("Images\\startbuttonHovered.png"));
        } else {
            helpButton.setIcon(new ImageIcon("Images\\helpbuttonHovered.png"));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == playButton) {
            playButton.setIcon(new ImageIcon("Images\\startbutton.png"));
        } else {
            helpButton.setIcon(new ImageIcon("Images\\helpbutton.png"));
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == playButton) {
            playButton.setIcon(new ImageIcon("Images\\startbuttonPressed.png"));
        } else {
            helpButton.setIcon(new ImageIcon("Images\\helpbuttonPressed.png"));
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getSource() == playButton) {
            playButton.setIcon(new ImageIcon("Images\\startbuttonHovered.png"));
        } else {
            helpButton.setIcon(new ImageIcon("Images\\helpbuttonHovered.png"));
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }
}