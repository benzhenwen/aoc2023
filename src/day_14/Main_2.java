package day_14;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main_2 {

    public static void main(String[] args) throws Exception {

        Scanner s = new Scanner(new File("data/puzzle_14_data.txt"));

        ArrayList<char[]> mapTemp = new ArrayList<>();
        while (s.hasNextLine()) {
            mapTemp.add(s.nextLine().toCharArray());
        }

        char[][] map = mapTemp.toArray(new char[0][]);
        map = flip(map); // now the north side is on the left, making computing easier

        ArrayList<char[][]> mapLog = new ArrayList<>();
        ArrayList<Integer> weightLog = new ArrayList<>();

        mapLog.add(map);

        int firstWeight = 0;
        for (char[] row : map) {
            for (int i = 0; i < row.length; i++) {
                if (row[i] == 'O') { // rock
                    firstWeight += row.length-i;
                }
            }
        }

        weightLog.add(firstWeight);

        int loopStart = -1;
        int loopLength = -1;

        int currentCycle = 0;
        while(true) {

            // performs a cycle
            for (long rotation = 0; rotation < 4; rotation++) {
                char[][] newMap = new char[map.length][map[0].length];
                for (int r = 0; r < map.length; r++) {
                    char[] row = map[r];
                    char[] newRow = new char[row.length];
                    Arrays.fill(newRow, '.');

                    int nextRockIndex = 0;
                    for (int i = 0; i < row.length; i++) {
                        if (row[i] == 'O') { // rock
                            newRow[nextRockIndex++] = 'O';
                        }
                        if (row[i] == '#') {
                            nextRockIndex = i + 1;
                            newRow[i] = '#';
                        }
                    }

                    newMap[r] = newRow;
                }
                map = newMap;

                map = mirror(map);
                map = flip(map);
            }
            currentCycle++;

            // gets the weight
            int weight = 0;

            for (char[] row : map) {
                for (int i = 0; i < row.length; i++) {
                    if (row[i] == 'O') { // rock
                        weight += row.length-i;
                    }
                }
            }

            // check for overlap
            int in = indexOf(mapLog, map);
            if(in != -1) { // found the loop
                loopStart = in;
                loopLength = currentCycle - in;
                System.out.println(in + " " + currentCycle);
                break;
            }

            // log the cycle index, map, and outcome
            mapLog.add(map);
            weightLog.add(weight);

        }

        System.out.println(weightLog.get(loopStart + (1000000000 - loopStart) % loopLength));

    }

    public static int indexOf(ArrayList<char[][]> maps, char[][] map) {
        for(int i = 0; i < maps.size(); i++) {
            if(equals(maps.get(i), map)) return i;
        }
        return -1;
    }

    public static boolean equals(char[][] a, char[][] b) {
        for(int i = 0; i < a.length; i++) {
            for(int j = 0; j < a[0].length; j++) {
                if(a[i][j] != b[i][j]) return false;
            }
        }
        return true;
    }

    public static char[][] flip(char[][] input) {
        char[][] output = new char[input[0].length][input.length];
        for(int row = 0; row < input.length; row++) for(int col = 0; col < input[0].length; col++) {
            output[col][row] = input[row][col];
        }
        return output;
    }

    public static char[][] mirror(char[][] input) {
        char[][] output = new char[input[0].length][input.length];
        for(int row = 0; row < input.length; row++) for(int col = 0; col < input[0].length; col++) {
            output[row][output[0].length - (col+1)] = input[row][col];
        }
        return output;
    }


}
