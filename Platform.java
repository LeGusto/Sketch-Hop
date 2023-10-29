import java.awt.Image;
import java.awt.Toolkit;

/**
 * Platforms that the player can jump on.
 */
public class Platform {
    int platformX;
    int platformY;
    int type;
    Boolean direction;
    int startPosition;
    int endPosition;
    Image platformImage;

    /**
     * Constructor for non-moving platforms.
     * @param x x coordinate.
     * @param y y coordinate.
     * @param type type of platform.
     */
    Platform(int x, int y, int type) {
        this.platformX = x;
        this.platformY = y;
        this.type = type;
        this.platformImage = randomImage();
    }

    /**
     * Constructor for moving platforms.
     * @param x x coordinate.
     * @param y y coordinate.
     * @param type type of platform.
     * @param direction which way the platform is moving.
     * @param startPosition where the platform starts moving and switches direction.
     * @param endPosition where the platform should switch direction.
     */
    Platform(int x, int y, int type, Boolean direction, int startPosition, int endPosition) {
        this.platformX = x;
        this.platformY = y;
        this.type = type;
        this.direction = direction;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    /**
     * Assigns an image to the platform based on type.
     * @return chosen image.
     */
    private Image randomImage() {
        Toolkit t = Toolkit.getDefaultToolkit();
        switch (this.type) {
            case 0: // normal platform
                return t.getImage("Images\\blackPlatform.png");
            case 1: // horizontally moving platform
                return t.getImage("Images\\orangePlatform.png");
            case 2: // vertically moving platform
                return t.getImage("Images\\redPlatform.png");
            case 3: // breakable platform
                return t.getImage("Images\\greenPlatform.png");
            case 4: // booster platform
                return t.getImage("Images\\bluePlatform.png");
            default:
                return t.getImage("Images\\blackPlatform.png");
        }
    }
}
