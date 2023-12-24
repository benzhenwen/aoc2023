package day_11;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {

        Scanner s = new Scanner(new File("data/puzzle_11_data.txt"));

        boolean[][] galaxy = new boolean[140][140];

        // load the galaxy
        int row = 0;
        while(s.hasNextLine()) {
            int col = 0;
            for(char c : s.nextLine().toCharArray()) {
                galaxy[row][col++] = c == '#';
            }
            row++;
        }

        // expand the galaxy
        galaxy = expand(galaxy);
        galaxy = flip(galaxy);
        galaxy = expand(galaxy);
        galaxy = flip(galaxy);

        // get all true points
        ArrayList<Coordinate> galaxyLocations = new ArrayList<>();
        for(row = 0; row < galaxy.length; row++) for(int col = 0; col < galaxy[0].length; col++) {
            if(galaxy[row][col]) galaxyLocations.add(new Coordinate(row, col));
        }

        int sum = 0;
        for(int i = 0; i < galaxyLocations.size()-1; i++) for(int j = i+1; j < galaxyLocations.size(); j++) {
            sum += distance(galaxyLocations.get(i), galaxyLocations.get(j));
        }

        System.out.println(sum);

    }

    public static boolean[][] expand(boolean[][] galaxy) {
        ArrayList<boolean[]> g = new ArrayList<>();
        for(int row = 0; row < galaxy.length; row++) {
            g.add(galaxy[row]);
            if(isFalse(galaxy[row])) g.add(galaxy[row]);
        }
        return g.toArray(new boolean[0][0]);
    }

    public static boolean[][] flip(boolean[][] input) {
        boolean[][] output = new boolean[input[0].length][input.length];
        for(int row = 0; row < input.length; row++) for(int col = 0; col < input[0].length; col++) {
            output[col][row] = input[row][col];
        }
        return output;
    }

    public static boolean isFalse(boolean[] input) {
        for(boolean b : input) if(b) return false;
        return true;
    }

    public static int distance(Coordinate a, Coordinate b) {
        return Math.abs(a.row - b.row) + Math.abs(a.col - b.col);
    }
}
