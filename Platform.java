public class Platform {
    int x;
    int y;
    int type;
    Boolean direction;
    int startPosition;
    int endPosition;

    Platform(int x, int y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    Platform(int x, int y, int type, Boolean direction, int startPosition, int endPosition) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.direction = direction;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }
}
