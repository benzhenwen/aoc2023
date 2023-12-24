package day_16;

import day_16.Beam.Direction;

public class Position {

    public int row, col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void move(Direction d) {
        switch(d) {
            case NORTH -> row--;
            case EAST -> col++;
            case SOUTH -> row++;
            case WEST -> col--;
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

}
