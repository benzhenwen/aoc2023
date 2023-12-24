package day_21;

import java.io.File;
import java.util.*;

// my code expanded to reach this solution
// https://github.com/villuna/aoc23/wiki/A-Geometric-solution-to-advent-of-code-2023,-day-21
// very clever solution

public class Main_2 {

    public static void main(String[] args) throws Exception {

        Scanner s = new Scanner(new File("data/puzzle_21_data.txt"));

        ArrayList<char[]> mapTemp = new ArrayList<>();
        while (s.hasNextLine()) {
            mapTemp.add(s.nextLine().toCharArray());
        }

        char[][] charMap = mapTemp.toArray(new char[0][]);
        boolean[][] walkableMap = new boolean[charMap.length][charMap[0].length];
        for(int i = 0; i < charMap.length; i++) {
            for (int j = 0; j < charMap[0].length; j++) {
                walkableMap[i][j] = charMap[i][j] != '#';
            }
        }

        HashSet<Position> currentPositions = new HashSet<>();
        currentPositions.add(positionOfS(charMap));

        HashMap<Position, Integer> positionDistances = new HashMap<>();

        for(int iteration = 0; iteration < 500; iteration++) {
            HashSet<Position> newPositions = new HashSet<>();
            for(Position p : currentPositions) {
                for(Direction d : Direction.values()) {
                    Position newPosition = p.clone(d);
                    if(newPosition.inBounds(walkableMap) && walkableMap[newPosition.row][newPosition.col]) {
                        newPositions.add(newPosition);

                        if(!positionDistances.containsKey(newPosition)) {
                            positionDistances.put(newPosition, iteration+1);
                        }
                    }
                }
            }
            currentPositions = newPositions;
        }

        // account for the full square
        int su = 0;
        for(Integer i : positionDistances.values()) {
            if(i % 2 == 0) su++;
        }
        System.out.println(su);

        // account for the corners
        int sum = 0;
        for(Integer i : positionDistances.values()) {
            if(i % 2 == 0 && i > 65) sum++;
        }
        System.out.println(sum);

        // tiles on an even parity (run iteration = 500) = 7334 / 3717 (corners)
        // tiles on an odd parity (run iteration = 5001) = 7250 / 3547 (corners)

        long evenFull = 7334;
        long oddFull = 7250;
        long evenCorner = 3717;
        long oddCorner = 3547;

        long n = 202300;

        System.out.println((((n+1)*(n+1)) * oddFull) + ((n*n) * evenFull) - ((n+1) * oddCorner) + (n * evenCorner));

    }

    public static Position positionOfS(char[][] map) {
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[0].length; j++) {
                if(map[i][j] == 'S') return new Position(i, j);
            }
        }
        throw new RuntimeException("S not found");
    }

}
