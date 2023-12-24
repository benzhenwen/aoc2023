package day_22;

import java.util.Objects;

public class Block {

    public Coordinate start, end;

    public Block(String input) {
        String[] split = input.split("~");
        start = new Coordinate(split[0]);
        end = new Coordinate(split[1]);
    }

    public boolean intersects(Block other) {
        return (
                ( between(this.start.x, this.end.x, other.start.x) || between(this.start.x, this.end.x, other.end.x) || between(other.start.x, other.end.x, this.start.x)) && // x ranges overlap
                ( between(this.start.y, this.end.y, other.start.y) || between(this.start.y, this.end.y, other.end.y) || between(other.start.y, other.end.y, this.start.y)) && // y ranges overlap
                ( between(this.start.z, this.end.z, other.start.z) || between(this.start.z, this.end.z, other.end.z) || between(other.start.z, other.end.z, this.start.z))    // z ranges overlap
        );
    }

    private boolean between(int a, int b, int x) {
        return (x >= a && x <= b) || (x >= b && x <= a);
    }

    public boolean isCarrying(Block other) {
        other.moveDown();
        boolean output = this.intersects(other);
        other.moveUp();
        return output;
    }

    public void moveUp() {
        start.moveVertical(1);
        end.moveVertical(1);
    }

    public void moveDown() {
        start.moveVertical(-1);
        end.moveVertical(-1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Block block = (Block) o;
        return Objects.equals(start, block.start) && Objects.equals(end, block.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    @Override
    public String toString() {
        return start + "~" + end;
    }
}
