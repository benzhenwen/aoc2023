package day_16;

public class Beam {

    public static enum Direction {NORTH, EAST, WEST, SOUTH};

    // instance vars
    public Direction direction;
    public Position position;

    public Beam(Direction direction, Position position) {
        this.direction = direction;
        this.position = position;
    }

    public void move() {
        position.move(direction);
    }

    public String toString() {
        return position.toString() + " " + direction;
    }

}
