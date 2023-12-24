package day_21;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Main {

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

        for(int iteration = 0; iteration < 64; iteration++) {
            HashSet<Position> newPositions = new HashSet<>();
            for(Position p : currentPositions) {
                for(Direction d : Direction.values()) {
                    Position newPosition = p.clone(d);
                    if(p.inBounds(walkableMap) && walkableMap[newPosition.row][newPosition.col]) {
                        newPositions.add(newPosition);
                    }
                }
            }
            currentPositions = newPositions;

            /*System.out.println(currentPositions);
            for(int i = 0; i < charMap.length; i++) {
                for (int j = 0; j < charMap[0].length; j++) {
                    if(currentPositions.contains(new Position(i, j))) {
                        System.out.print("O ");
                    }
                    else if(!walkableMap[i][j]) {
                        System.out.print("# ");
                    }
                    else System.out.print(". ");
                }
                System.out.println();
            }
            System.out.println();*/
        }

        System.out.println(currentPositions.size());

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
