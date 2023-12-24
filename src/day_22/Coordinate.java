package day_22;

import java.util.Objects;

public class Coordinate {

    public int x, y, z;

    /**
     * creates a coordinate
     * @param x x
     * @param y y
     * @param z z
     */
    public Coordinate(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * creates a coordinate
     * @param input format: "x,y,z"
     */
    public Coordinate(String input) {
        String[] split = input.split(",");
        this.x = Integer.parseInt(split[0]);
        this.y = Integer.parseInt(split[1]);
        this.z = Integer.parseInt(split[2]);
    }

    public void move(int x, int y, int z) {
        this.x += x;
        this.y += y;
        this.z += z;
    }

    public void moveVertical(int z) {
        this.z += z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return x == that.x && y == that.y && z == that.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public String toString() {
        return x + " " + y + " " + z;
    }
}
