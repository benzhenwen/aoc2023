package day_11;

public class Coordinate {

    public int row, col;

    public Coordinate(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public boolean isInBounds(int minRow, int maxRow, int minCol, int maxCol) {
        return row >= minRow && row < maxRow && col >= minCol && col < maxCol;
    }

    public String toString() {
        return "(" + row + ", " + col + ")";
    }

}
