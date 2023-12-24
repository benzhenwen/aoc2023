package day_10;

import java.io.File;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {

        Scanner s = new Scanner(new File("data/puzzle_10_data.txt"));

        // import map
        ArrayList<char[]> mapArrayList = new ArrayList<>();
        while(s.hasNextLine()) {
            mapArrayList.add(s.nextLine().toCharArray());
        }

        char[][] map = mapArrayList.toArray(new char[0][]);

        int startRow = -1;
        int startCol = -1;

        SSearch:
        for(int row = 0; row < map.length; row++) {
            for(int col = 0; col < map[0].length; col++) {
                if(map[row][col] == 'S') {
                    startRow = row;
                    startCol = col;
                    break SSearch;
                }
            }
        }

        Queue<Point> searchQueue = new LinkedList<>();
        searchQueue.add(new Point(startRow, startCol, 0));

        boolean[][] hasSearchedMap = new boolean[map.length][map[0].length];
        int furthestDistance = -1;

        while(!searchQueue.isEmpty()) {
            Point p = searchQueue.remove();
            if(
                    p.isInBounds(0, map.length, 0, map[0].length) && // in bounds
                    !hasSearchedMap[p.row][p.col] // has not been searched yet
            ) {
                hasSearchedMap[p.row][p.col] = true;
                if(p.distance > furthestDistance) furthestDistance = p.distance;
                char mapChar = map[p.row][p.col];

                if(mapChar == 'S') { // special start case
                    Point adjPoint;

                    adjPoint = new Point(p.row+1, p.col, p.distance+1);
                    if(adjPoint.isInBounds(0, map.length, 0, map[0].length) &&
                            contains(getAdjacentPoints(adjPoint, map[adjPoint.row][adjPoint.col]), p)) searchQueue.add(adjPoint);
                    adjPoint = new Point(p.row-1, p.col, p.distance+1);
                    if(adjPoint.isInBounds(0, map.length, 0, map[0].length) &&
                            contains(getAdjacentPoints(adjPoint, map[adjPoint.row][adjPoint.col]), p)) searchQueue.add(adjPoint);
                    adjPoint = new Point(p.row, p.col+1, p.distance+1);
                    if(adjPoint.isInBounds(0, map.length, 0, map[0].length) &&
                            contains(getAdjacentPoints(adjPoint, map[adjPoint.row][adjPoint.col]), p)) searchQueue.add(adjPoint);
                    adjPoint = new Point(p.row, p.col-1, p.distance+1);
                    if(adjPoint.isInBounds(0, map.length, 0, map[0].length) &&
                            contains(getAdjacentPoints(adjPoint, map[adjPoint.row][adjPoint.col]), p)) searchQueue.add(adjPoint);
                }

                else {
                    searchQueue.addAll(Arrays.asList(getAdjacentPoints(p, map[p.row][p.col])));
                }
            }
        }
        System.out.println(furthestDistance);
    }

    public static boolean contains(Point[] points, Point p) {
        for(Point point : points) {
            if(point.row == p.row && point.col == p.col) return true;
        }
        return false;
    }

    public static Point[] getAdjacentPoints(Point p, char pipeShape) {
        Point[] output = new Point[2];
        switch (pipeShape) {
            case '.':
                return new Point[0];
            case '|':
                output[0] = new Point(p.row-1, p.col, p.distance+1);
                output[1] = new Point(p.row+1, p.col, p.distance+1);
                break;
            case '-':
                output[0] = new Point(p.row, p.col-1, p.distance+1);
                output[1] = new Point(p.row, p.col+1, p.distance+1);
                break;
            case 'L':
                output[0] = new Point(p.row-1, p.col, p.distance+1);
                output[1] = new Point(p.row, p.col+1, p.distance+1);
                break;
            case 'J':
                output[0] = new Point(p.row-1, p.col, p.distance+1);
                output[1] = new Point(p.row, p.col-1, p.distance+1);
                break;
            case '7':
                output[0] = new Point(p.row+1, p.col, p.distance+1);
                output[1] = new Point(p.row, p.col-1, p.distance+1);
                break;
            case 'F':
                output[0] = new Point(p.row+1, p.col, p.distance+1);
                output[1] = new Point(p.row, p.col+1, p.distance+1);
                break;
            default:
                System.out.println("invalid pipe");
                throw new IllegalArgumentException("pipe of character \'" + pipeShape + "\' not supported");
        }
        return output;
    }
}
