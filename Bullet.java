import java.util.ArrayList;

/**
 * Bullets which the player shoots to kill enemies.
 */
public class Bullet {

    int speed;
    int size;
    int direction;
    int bulletX;
    int bulletY;

    /**
     * Constructor.
     * 
     * @param direction where the bullet is heading.
     * @param x         coordinate.
     * @param y         coordinate.
     */
    Bullet(int direction, int x, int y) {
        this.direction = direction;
        this.bulletX = x;
        this.bulletY = y;
        this.speed = 20;
        this.size = 10;
    }

    /**
     * Moves the bullet in the game panel according to its direction.
     */
    public void moveBullet() {
        switch (this.direction) {
            case 0:
                this.bulletY -= speed;
                this.bulletX -= speed;
                break;
            case 1:
                this.bulletY -= speed * 1.5;
                break;
            case 2:
                this.bulletY -= speed;
                this.bulletX += speed;
                break;
            default:
                this.bulletY -= speed * 1.5;
        }
    }

    /**
     * Checks if any bullet collides with an enemy and delets both if so.
     * 
     * @param bullets arraylist of bullets in the game.
     * @param enemies arraylist of enemies in the game.
     */
    public boolean checkCollisions(ArrayList<Bullet> bullets, ArrayList<Enemy> enemies) {
        Bullet bullet = this;
        for (int j = 0; j < enemies.size(); j++) {
            Enemy enemy = enemies.get(j);
            if (!(bullet.bulletY - enemy.enemyY > -bullet.size
                    && bullet.bulletY - enemy.enemyY <= enemy.enemyHeight)) {
                continue;
            }
            if (!((bullet.bulletX - enemy.enemyX > -bullet.size)
                    && bullet.bulletX - enemy.enemyX < enemy.enemyWidth)) {
                continue;
            }

            bullets.remove(bullet);
            enemies.remove(enemy);
            return true;
        }
        return false;
    }
}
