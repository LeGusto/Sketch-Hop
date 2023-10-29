import java.util.ArrayList;

/**
 * Bullets which the player shoots to kill enemies.
 */
public class Bullets {

    int speed;
    int size;
    int direction;
    int x;
    int y;

    /**
     * Constructor.
     * 
     * @param direction where the bullet is heading.
     * @param x         coordinate.
     * @param y         coordinate.
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

    /**
     * Checks if any bullet collides with an enemy and delets both if so.
     * 
     * @param bullets arraylist of bullets in the game.
     * @param enemies arraylist of enemies in the game.
     */
    public boolean checkCollisions(ArrayList<Bullets> bullets, ArrayList<Enemies> enemies) {
        Bullets bullet = this;
        for (int j = 0; j < enemies.size(); j++) {
            Enemies enemy = enemies.get(j);
            if (!(bullet.y - enemy.enemyY > -bullet.size
                    && bullet.y - enemy.enemyY <= enemy.enemyHeight)) {
                continue;
            }
            if (!((bullet.x - enemy.enemyX > -bullet.size)
                    && bullet.x - enemy.enemyX < enemy.enemyWidth)) {
                continue;
            }

            System.out.println("Got here");

            bullets.remove(bullet);
            enemies.remove(enemy);
            return true;
        }
        return false;
    }
}
