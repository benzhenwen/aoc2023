package day_10;

import java.io.File;
import java.util.*;

public class Main_2 {

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

        // check what links the start tile has right away
        LinkedTile STile = new LinkedTile();

        Queue<Coordinate> searchQueue = new LinkedList<>();
        searchQueue.add(new Coordinate(startRow, startCol));

        boolean[][] hasSearchedMap = new boolean[map.length][map[0].length];

        while(!searchQueue.isEmpty()) {
            Coordinate p = searchQueue.remove();
            if(
                    p.isInBounds(0, map.length, 0, map[0].length) && // in bounds
                    !hasSearchedMap[p.row][p.col] // has not been searched yet
            ) {
                hasSearchedMap[p.row][p.col] = true;
                char mapChar = map[p.row][p.col];

                if(mapChar == 'S') { // special start case
                    Coordinate adjCoordinate;

                    adjCoordinate = new Coordinate(p.row+1, p.col);
                    if(adjCoordinate.isInBounds(0, map.length, 0, map[0].length) &&
                            contains(getAdjacentCoordinates(adjCoordinate, map[adjCoordinate.row][adjCoordinate.col]), p)) {
                        searchQueue.add(adjCoordinate);
                        STile.down = true;
                    }
                    adjCoordinate = new Coordinate(p.row-1, p.col);
                    if(adjCoordinate.isInBounds(0, map.length, 0, map[0].length) &&
                            contains(getAdjacentCoordinates(adjCoordinate, map[adjCoordinate.row][adjCoordinate.col]), p)) {
                        searchQueue.add(adjCoordinate);
                        STile.up = true;
                    }
                    adjCoordinate = new Coordinate(p.row, p.col+1);
                    if(adjCoordinate.isInBounds(0, map.length, 0, map[0].length) &&
                            contains(getAdjacentCoordinates(adjCoordinate, map[adjCoordinate.row][adjCoordinate.col]), p)) {
                        searchQueue.add(adjCoordinate);
                        STile.right = true;
                    }
                    adjCoordinate = new Coordinate(p.row, p.col-1);
                    if(adjCoordinate.isInBounds(0, map.length, 0, map[0].length) &&
                            contains(getAdjacentCoordinates(adjCoordinate, map[adjCoordinate.row][adjCoordinate.col]), p)) {
                        searchQueue.add(adjCoordinate);
                        STile.left = true;
                    }
                }

                else {
                    searchQueue.addAll(Arrays.asList(getAdjacentCoordinates(p, map[p.row][p.col])));
                }
            }
        }

        // form a map describing all the in-loop links
        LinkedTile[][] LTMap = new LinkedTile[map.length][map[0].length];
        for(int row = 0; row < map.length; row++) {
            for(int col = 0; col < map[0].length; col++) {
                if(hasSearchedMap[row][col]) { // is part of loop
                    switch(map[row][col]) {
                        case 'S' -> LTMap[row][col] = STile;
                        case '|' -> LTMap[row][col] = new LinkedTile(true, true, false, false);
                        case '-' -> LTMap[row][col] = new LinkedTile(false, false, true, true);
                        case 'L' -> LTMap[row][col] = new LinkedTile(true, false, false, true);
                        case 'J' -> LTMap[row][col] = new LinkedTile(true, false, true, false);
                        case '7' -> LTMap[row][col] = new LinkedTile(false, true, true, false);
                        case 'F' -> LTMap[row][col] = new LinkedTile(false, true, false, true);
                    }
                }
                else { // not part of loop
                    LTMap[row][col] = new LinkedTile(); // tile has no links
                }
            }
        }

        // traverse the loop fully to determine orientation
        int cRow = startRow;
        int cCol = startCol;
        int traverseDirection = LTMap[cRow][cCol].getOtherDirection(LinkedTile.LEFT);

        int turns = 0;

        do {
            if (traverseDirection == LinkedTile.UP) cRow--;
            else if (traverseDirection == LinkedTile.RIGHT) cCol++;
            else if (traverseDirection == LinkedTile.DOWN) cRow++;
            else if (traverseDirection == LinkedTile.LEFT) cCol--;

            turns += LTMap[cRow][cCol].turnDirection(traverseDirection);
            traverseDirection = LTMap[cRow][cCol].getOtherDirection(LinkedTile.invertDirection(traverseDirection));
        } while (cRow != startRow || cCol != startCol);

        boolean isClockwise = turns > 0; // when traversing the loop was it clockwise or counterclockwise?

        boolean[][] isInsideLoopMap = new boolean[map.length][map[0].length];
        traverseDirection = LTMap[cRow][cCol].getOtherDirection(LinkedTile.LEFT);

        do { // traverse the loop again
            LinkedTile lt = LTMap[cRow][cCol];
            if(lt.isStraight()) { // bars
                if(lt.up) { // vertical bar
                    if(cCol-1 >= 0) isInsideLoopMap[cRow][cCol-1] = isClockwise ^ traverseDirection == LinkedTile.UP;
                    if(cCol+1 < map[0].length) isInsideLoopMap[cRow][cCol+1] = isClockwise ^ traverseDirection == LinkedTile.DOWN;
                }
                else { // horizontal bar
                    if(cRow-1 >= 0) isInsideLoopMap[cRow-1][cCol] = isClockwise ^ traverseDirection == LinkedTile.RIGHT;
                    if(cRow+1 < map.length) isInsideLoopMap[cRow+1][cCol] = isClockwise ^ traverseDirection == LinkedTile.LEFT;
                }
            }
            else { // corners
                boolean isTurnClockwise = lt.turnDirection(traverseDirection) == 1;
                if(!lt.up && cRow-1 >= 0) isInsideLoopMap[cRow-1][cCol] = !isTurnClockwise ^ isClockwise;
                if(!lt.down && cRow+1 < map.length) isInsideLoopMap[cRow+1][cCol] = !isTurnClockwise ^ isClockwise;
                if(!lt.left && cCol-1 >= 0) isInsideLoopMap[cRow][cCol-1] = !isTurnClockwise ^ isClockwise;
                if(!lt.right && cCol+1 < map[0].length) isInsideLoopMap[cRow][cCol+1] = !isTurnClockwise ^ isClockwise;
            }

            if (traverseDirection == LinkedTile.UP) cRow--;
            else if (traverseDirection == LinkedTile.RIGHT) cCol++;
            else if (traverseDirection == LinkedTile.DOWN) cRow++;
            else if (traverseDirection == LinkedTile.LEFT) cCol--;

            traverseDirection = LTMap[cRow][cCol].getOtherDirection(LinkedTile.invertDirection(traverseDirection));
        } while (cRow != startRow || cCol != startCol);

        int output = 0;

        for(int row = 0; row < map.length; row++) {
            for(int col = 0; col < map[0].length; col++) {
                alreadySearchedMap = new boolean[map.length][map[0].length];
                if(isInsideLoop(isInsideLoopMap, hasSearchedMap, row, col)) output++;
            }
        }

        System.out.println(output);

    }

    private static boolean[][] alreadySearchedMap;

    public static boolean isInsideLoop(boolean[][] positiveMap, boolean[][] restrictiveMap, int row, int col) {
        if(row < 0 || row >= positiveMap.length || col < 0 || col >= positiveMap[0].length) return false; // out of bounds
        if(alreadySearchedMap[row][col]) return false; // already searched this spot
        if(restrictiveMap[row][col]) return false; // this tile is part of the loop...
        if(positiveMap[row][col]) return true; // this tile is marked as being inside the loop!

        alreadySearchedMap[row][col] = true;

        // check adj tiles
        if(isInsideLoop(positiveMap, restrictiveMap, row+1, col)) return true;
        if(isInsideLoop(positiveMap, restrictiveMap, row-1, col)) return true;
        if(isInsideLoop(positiveMap, restrictiveMap, row, col+1)) return true;
        if(isInsideLoop(positiveMap, restrictiveMap, row, col-1)) return true;

        // not found...
        return false;
    }

    public static boolean contains(Coordinate[] points, Coordinate p) {
        for(Coordinate point : points) {
            if(point.row == p.row && point.col == p.col) return true;
        }
        return false;
    }

    public static Coordinate[] getAdjacentCoordinates(Coordinate p, char pipeShape) {
        Coordinate[] output = new Coordinate[2];
        switch (pipeShape) {
            case '.':
                return new Coordinate[0];
            case '|':
                output[0] = new Coordinate(p.row-1, p.col);
                output[1] = new Coordinate(p.row+1, p.col);
                break;
            case '-':
                output[0] = new Coordinate(p.row, p.col-1);
                output[1] = new Coordinate(p.row, p.col+1);
                break;
            case 'L':
                output[0] = new Coordinate(p.row-1, p.col);
                output[1] = new Coordinate(p.row, p.col+1);
                break;
            case 'J':
                output[0] = new Coordinate(p.row-1, p.col);
                output[1] = new Coordinate(p.row, p.col-1);
                break;
            case '7':
                output[0] = new Coordinate(p.row+1, p.col);
                output[1] = new Coordinate(p.row, p.col-1);
                break;
            case 'F':
                output[0] = new Coordinate(p.row+1, p.col);
                output[1] = new Coordinate(p.row, p.col+1);
                break;
            default:
                System.out.println("invalid pipe");
                throw new IllegalArgumentException("pipe of character \'" + pipeShape + "\' not supported");
        }
        return output;
    }
}
