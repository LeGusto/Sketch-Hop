import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Contains methods to display the main menu, help menu and game panel.
 */
public class GUI implements ActionListener, MouseListener {

    JFrame frame; 
    ShapeDrawer startPanel;
    ShapeDrawer helpPanel;
    JButton playButton;
    JButton helpButton;
    ImageIcon spongebob;
    PlayerMovement movement;
    Boolean lost = false;
    ShapeDrawer gamePanel;
    PlatformMethods platforms;

    /**
     * Constructor.
     */
    GUI() {
        this.frame = new JFrame("Sketch Hop");
        this.gamePanel = new ShapeDrawer(frame, gamePanel, helpPanel);
        this.helpPanel = new ShapeDrawer(frame, gamePanel, helpPanel);
        this.movement = new PlayerMovement(gamePanel, frame);
        this.platforms = new PlatformMethods(gamePanel, frame);
    }

    /**
     * Creates GUI elements and displays the main menu.
     */
    public void startGame() {
        this.startPanel = new ShapeDrawer(frame, gamePanel, helpPanel);
        
        playButton = new JButton(new ImageIcon("Images\\startButton.png"));
        helpButton = new JButton(new ImageIcon("Images\\helpButton.png"));

        playButton.addActionListener(this);
        playButton.addMouseListener(this);
        playButton.setOpaque(false);
        playButton.setContentAreaFilled(false);
        playButton.setBorderPainted(false);
        playButton.setFocusPainted(false);

        helpButton.addActionListener(this);
        helpButton.addMouseListener(this);
        helpButton.setOpaque(false);
        helpButton.setContentAreaFilled(false);
        helpButton.setBorderPainted(false);
        helpButton.setFocusPainted(false);

        startPanel.setLayout(null);

        Dimension size = new Dimension(1920, 1080);
        Dimension playButtonSize = playButton.getPreferredSize();

        playButton.setBounds((int) (size.getWidth() - playButtonSize.width) / 2,
            350, playButtonSize.width, playButtonSize.height);
        startPanel.add(playButton);

        helpButton.setBounds((int) (size.getWidth() - playButtonSize.width) / 2,
            600, playButtonSize.width, playButtonSize.height);
        startPanel.add(helpButton);

        startPanel.setBackground(new Color(0, 200, 255));
        
        frame.add(startPanel);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize((int) size.getWidth(), (int) size.getHeight());
        frame.setMinimumSize(size);
        frame.setMaximumSize(size);
        frame.setVisible(true);

        gamePanel.setVisible(false);
        frame.add(gamePanel);
    }

    /**
     * Sets up the help menu.
     */
    private void helpMenu() {
        startPanel.setVisible(false);
        playButton.setVisible(false);
        helpPanel.setVisible(true);
        helpPanel.setBackground(new Color(0, 200, 255));
        playButton = new JButton(new ImageIcon("Images\\startButton.png"));

        playButton.addActionListener(this);
        playButton.addMouseListener(this);
        playButton.setOpaque(false);
        playButton.setContentAreaFilled(false);
        playButton.setBorderPainted(false);
        playButton.setFocusPainted(false);

        helpPanel.setLayout(null);

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension playButtonSize = playButton.getPreferredSize();

        playButton.setBounds((int) (size.getWidth() - playButtonSize.width) / 2,
            700, playButtonSize.width, playButtonSize.height);

        helpPanel.add(playButton);
        frame.add(helpPanel);
    }

    /**
     * Hides the main menu, displays the game panel, pregenerates some platforms
     * and initiates player movement.
     */
    private void playGame() {
        startPanel.setVisible(false);
        playButton.setVisible(false);
        gamePanel.setVisible(true);

        movement.run();
        platforms.generateRandomPlatforms(-gamePanel.jumpHeight * 20, frame.getHeight());

        // create a platform for the player to stand on in the beginning
        platforms.generatePlatform(gamePanel.playerX, gamePanel.playerY + 100, 0);
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