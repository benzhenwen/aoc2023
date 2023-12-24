package day_12;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {

        Scanner s = new Scanner(new File("data/puzzle_12_data.txt"));

        int rows = 1000;

        char[][] map = new char[rows][];
        int[][] seperations = new int[rows][];

        int index = 0;
        while(s.hasNextLine()) {

            String line = s.nextLine();
            map[index] = line.split(" ")[0].toCharArray();

            String[] numbers = line.split(" ")[1].split(",");

            seperations[index] = new int[numbers.length];
            for(int i = 0; i < numbers.length; i++) {
                seperations[index][i] = Integer.parseInt(numbers[i]);
            }

            index++;

        }

        int output = 0;

        for(int l = 0; l < map.length; l++) {
            char[] line = map[l];
            int[] sepVals = seperations[l];

            ArrayList<Integer> uPoints = new ArrayList<>();
            for(int i = 0; i < line.length; i++) {
                if(line[i] == '?') uPoints.add(i);
            }

            for(int i = 0; i < Math.pow(2, uPoints.size()); i++) {
                boolean[] newMap = new boolean[line.length];

                int qsSeen = 0;
                for(int j = 0; j < line.length; j++) {
                    if(line[j] == '.') newMap[j] = false;
                    else if(line[j] == '#') newMap[j] = true;
                    else {
                        newMap[j] = (i >> qsSeen++) % 2 == 0;
                    }
                }

                if(structuresEqual(spacingStructure(newMap), sepVals)) output++;
            }
        }

        System.out.println(output);

    }

    public static boolean structuresEqual(int[] a, int[] b) {
        if(a.length != b.length) return false;
        for(int i = 0; i < a.length; i++) {
            if(a[i] != b[i]) return false;
        }
        return true;
    }

    public static int[] spacingStructure(boolean[] input) {
        ArrayList<Integer> output = new ArrayList<>();

        int currentSum = 0;
        for(boolean b : input) {
            if(b) {
                currentSum++;
            }
            else if(currentSum > 0) {
                output.add(currentSum);
                currentSum = 0;
            }
        }

        if(currentSum > 0) output.add(currentSum);

        int[] o = new int[output.size()];
        int index = 0;
        for(Integer i : output) o[index++] = i;
        return o;

    }

}
