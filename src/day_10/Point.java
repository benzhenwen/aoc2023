package day_10;

public class Point {

    public int row, col, distance;

    public Point(int row, int col, int distance) {
        this.row = row;
        this.col = col;
        this.distance = distance;
    }

    public boolean isInBounds(int minRow, int maxRow, int minCol, int maxCol) {
        return row >= minRow && row < maxRow && col >= minCol && col < maxCol;
    }

}
