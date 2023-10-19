import java.awt.*;
import java.awt.event.*;
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

    GUI() {
        frame = new JFrame("game");
        gamePanel = new ShapeDrawer(frame);
        movement = new PlayerMovement(gamePanel, frame);
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

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setVisible(true);

        gamePanel.setBackground(new Color(255, 0, 0));
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
        gamePanel.generatePlatform(0);
        //Platform platformGenerator = new Platform(-100, -100, frame);
        //platformGenerator.generatePlatform(0);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        playGame();
    }
    
}