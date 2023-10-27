import java.util.ArrayList;

public class Bullets {

    int speed;
    int size;
    int direction;
    int x;
    int y;

    /**
     * Constructor
     * @param direction where the bullet is heading.
     * @param x coordinate.
     * @param y coordinate.
     */
    Bullets(int direction, int x, int y) {
        this.direction = direction;
        this.x = x;
        this.y = y;
        this.speed = 20;
        this.size = 10;
    }

    /**
     * Moves the bullet in the game panel according to its direction.
     */
    void moveBullet() {
        switch (this.direction) {
            case 0:
                this.y -= speed;
                this.x -= speed;
                break;
            case 1:
                this.y -= speed * 1.5;
                break;
            case 2:
                this.y -= speed;
                this.x += speed;
                break;
            default:
                this.y -= speed * 1.5;
        }
    }
}
