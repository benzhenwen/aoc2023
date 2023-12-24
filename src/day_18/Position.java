package day_18;

import day_18.Main.Direction;

public class Position {

    public long row, col;

    public Position () {}

    public Position(long row, long col) {
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

    public void move(Direction d, long amount) {
        switch(d) {
            case UP -> row -= amount;
            case RIGHT -> col += amount;
            case DOWN -> row += amount;
            case LEFT -> col -= amount;
        }
    }

    public boolean inBounds(Object[][] map) {
        return row >= 0 && row < map.length && col >= 0 && col < map[0].length;
    }

    @Override
    public Position clone() {
        return new Position(row, col);
    }

    public String toString() {
        return "(" + col + ", " + row + ")";
    }

    public boolean equals(Position other) {
        return this.row == other.row && this.col == other.col;
    }

}
