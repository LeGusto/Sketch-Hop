/**
 * Platforms that the player can jump on.
 */
public class Platform {
    int x;
    int y;
    int type;
    Boolean direction;
    int startPosition;
    int endPosition;

    /**
     * Constructor for non-moving platforms.
     * @param x x coordinate.
     * @param y y coordinate.
     * @param type type of platform.
     */
    Platform(int x, int y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
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
        this.x = x;
        this.y = y;
        this.type = type;
        this.direction = direction;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }
}
