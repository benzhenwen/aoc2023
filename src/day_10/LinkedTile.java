package day_10;

public class LinkedTile {

    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;

    public boolean up, down, left, right;

    public LinkedTile(boolean up, boolean down, boolean left, boolean right) {
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
    }

    public LinkedTile() {};

    public int turnDirection(int direction) { // returns if entering a tile results in a clockwise, straight, or counterclockwise step (-1, 0, or 1)
        if(direction == UP) {
            if(left) return 1;
            if(down) return 0;
            if(right) return -1;
        }
        if(direction == RIGHT) {
            if(up) return 1;
            if(left) return 0;
            if(down) return -1;
        }
        if(direction == DOWN) {
            if(right) return 1;
            if(up) return 0;
            if(left) return -1;
        }
        if(direction == LEFT) {
            if(down) return 1;
            if(right) return 0;
            if(up) return -1;
        }
        throw new IllegalArgumentException("direction not found");
    }

    public boolean isStraight() {
        return (
                (up && down && !left && !right) ||
                (!up && !down && left && right) );
    }

    public int getOtherDirection(int startDirection) {
        if(up && startDirection != UP) return UP;
        if(right && startDirection != RIGHT) return RIGHT;
        if(down && startDirection != DOWN) return DOWN;
        return LEFT;
    }

    public static int invertDirection(int direction) {
        if(direction == UP) return DOWN;
        if(direction == DOWN) return UP;
        if(direction == LEFT) return RIGHT;
        if(direction == RIGHT) return LEFT;
        throw new IllegalArgumentException("invalid direction");
    }

}
