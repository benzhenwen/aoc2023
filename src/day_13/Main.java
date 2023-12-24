package day_13;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {

        Scanner s = new Scanner(new File("data/puzzle_13_data.txt"));

        ArrayList<char[][]> maps = new ArrayList<>();

        ArrayList<char[]> currMap = new ArrayList<>();
        while(s.hasNextLine()) {
            String l = s.nextLine();
            if(l.isBlank()) {
                maps.add(currMap.toArray(new char[0][0]));
                currMap.clear();
            }
            else {
                currMap.add(l.toCharArray());
            }
        }
        maps.add(currMap.toArray(new char[0][0]));

        int output = 0;

        for(char[][] map : maps) {

            int verticalMirror = 1;
            while(verticalMirror < map.length && mirrorDifference(map, verticalMirror) != 1) verticalMirror++;
            if(verticalMirror == map.length) verticalMirror = 0;

            map = flip(map);

            int horizontalMirror = 1;
            while(horizontalMirror < map.length && mirrorDifference(map, horizontalMirror) != 1) horizontalMirror++;
            if(horizontalMirror == map.length) horizontalMirror = 0;


            output += verticalMirror*100 + horizontalMirror;

        }

        System.out.println(output);

    }

    public static int mirrorDifference(char[][] input, int line) {
        int output = 0;
        for(int l = line-1, u = line; l >= 0 && u < input.length; l--, u++) {
            output += compare(input[l], input[u]);
        }
        return output;
    }

    public static char[][] flip(char[][] input) {
        char[][] output = new char[input[0].length][input.length];
        for(int row = 0; row < input.length; row++) for(int col = 0; col < input[0].length; col++) {
            output[col][row] = input[row][col];
        }
        return output;
    }

    public static int compare(char[] line1, char[] line2) {
        int output = 0;
        for(int i = 0; i < line1.length; i++) {
            if(line1[i] != line2[i]) output++;
        }
        return output;
    }

}
