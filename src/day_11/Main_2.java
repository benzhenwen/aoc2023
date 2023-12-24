package day_11;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

public class Main_2 {

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

        // get all true points
        ArrayList<Coordinate> galaxyLocations = new ArrayList<>();
        for(row = 0; row < galaxy.length; row++) for(int col = 0; col < galaxy[0].length; col++) {
            if(galaxy[row][col]) galaxyLocations.add(new Coordinate(row, col));
        }

        Stack<Integer> offsetLocations = new Stack<>();

        // expand the galaxy by expanding the points
        for(row = 0; row < galaxy.length; row++) {
            if(isFalse(galaxy[row])) {
                offsetLocations.add(row);
            }
        }

        while(!offsetLocations.empty()) {
            int offsetVal = offsetLocations.pop();
            for(Coordinate c : galaxyLocations) {
                if(c.row > offsetVal) c.row += 999999;
            }
        }

        galaxy = flip(galaxy);

        // expand the galaxy by expanding the points
        for(int col = 0; col < galaxy.length; col++) {
            if(isFalse(galaxy[col])) {
                offsetLocations.add(col);
            }
        }

        while(!offsetLocations.empty()) {
            int offsetVal = offsetLocations.pop();
            for(Coordinate c : galaxyLocations) {
                if(c.col > offsetVal) c.col += 999999;
            }
        }

        long sum = 0;
        for(int i = 0; i < galaxyLocations.size()-1; i++) for(int j = i+1; j < galaxyLocations.size(); j++) {
            sum += distance(galaxyLocations.get(i), galaxyLocations.get(j));
        }

        System.out.println(sum);

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
