package day_14;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {

        Scanner s = new Scanner(new File("data/puzzle_14_data.txt"));

        ArrayList<char[]> mapTemp = new ArrayList<>();
        while (s.hasNextLine()) {
            mapTemp.add(s.nextLine().toCharArray());
        }

        char[][] map = mapTemp.toArray(new char[0][]);
        map = flip(map); // now the north side is on the left, making computing easier

        int output = 0;

        for (char[] row : map) {
            int nextRockWeight = row.length;
            for (int i = 0; i < row.length; i++) {
                if (row[i] == 'O') { // rock
                    output += nextRockWeight--;
                }
                if (row[i] == '#') {
                    nextRockWeight = row.length - (i+1);
                }
            }
        }

        System.out.println(output);
    }

    public static char[][] flip(char[][] input) {
        char[][] output = new char[input[0].length][input.length];
        for(int row = 0; row < input.length; row++) for(int col = 0; col < input[0].length; col++) {
            output[col][row] = input[row][col];
        }
        return output;
    }


}
