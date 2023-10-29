/**
 * Class which runs the code.
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