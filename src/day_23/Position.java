package day_23;

import java.util.Objects;

public class Position {

    public int row, col;

    public Position() {}

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void move(Direction d) {
        switch(d) {
            case UP -> row--;
            case RIGHT -> col++;
            case DOWN -> row++;
            case LEFT -> col--;
        }
    }

    public boolean inBounds(char[][] map) {
        return row >= 0 && row < map.length && col >= 0 && col < map[0].length;
    }

    public char getValue(char[][] map) {
        return map[row][col];
    }

    @Override
    public Position clone() {
        return new Position(row, col);
    }

    public Position clone(Direction d) {
        Position output = new Position(row, col);
        output.move(d);
        return output;
    }

    @Override
    public String toString() {
        return "(" + col + ", " + row + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return row == position.row && col == position.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}
