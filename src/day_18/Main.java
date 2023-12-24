package day_18;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {

    public enum Direction {UP, DOWN, LEFT, RIGHT};

    public static void main(String[] args) throws Exception {

        Scanner s = new Scanner(new File("data/puzzle_18_data.txt"));

        ArrayList<Instruction> instructions = new ArrayList<>();

        // load the file
        while(s.hasNextLine()) {
            String[] line = s.nextLine().split(" ");

            Direction d = stringToDirection(line[0]);
            int length = Integer.parseInt(line[1]);
            String color = line[2].substring(2, 8);

            instructions.add(new Instruction(d, length));
        }
        s.close();

        // get the bounds of the map
        long rows = 0, cols = 0;
        long nRows = 0, nCols = 0;
        Position p = new Position();
        for(Instruction i : instructions) {
            p.move(i.d, i.length);
            if(p.row > rows) rows = p.row;
            if(p.col > cols) cols = p.col;
            if(p.row < nRows) nRows = p.row;
            if(p.col < nCols) nCols = p.col;
        }

        System.out.println(nRows + " " + rows + " | " + nCols + " " + cols);

        Position startingPosition = new Position(-nRows, -nCols);

        // create the map
        Space[][] map = new Space[(int) (rows - nRows + 1)][(int) (cols - nCols + 1)];
        for(int i = 0; i < map.length; i++) for(int j = 0; j < map[0].length; j++) map[i][j] = new Space();

        // traverse the map

        int c = 0;

        Position currentPosition = startingPosition.clone();
        for(Instruction i : instructions) {
            i.run(map, currentPosition);
        }

        for(int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                fill(map, new Position(i, j));
            }
        }

        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[0].length; j++) {
                System.out.print((map[i][j].isDug ? "#" : ".") + " ");
            }
            System.out.println();
        }

        int output = 0;
        for(int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if((map[i][j].isDug)) output++;
            }
        }

        System.out.println(output);

    }

    public static Direction stringToDirection(String input) {
        switch(input) {
            case "U" -> {return Direction.UP;}
            case "D" -> {return Direction.DOWN;}
            case "L" -> {return Direction.LEFT;}
            case "R" -> {return Direction.RIGHT;}
        }
        throw new IllegalArgumentException("not a valid direction string");
    }

    public static void fill(Space[][] map, Position p) {
        if(map[(int) p.row][(int) p.col].isDug || map[(int) p.row][(int) p.col].isNotDug) return;

        LinkedList<Position> toFill = new LinkedList<>();
        boolean[][] checkedMap = new boolean[map.length][map[0].length];

        boolean isOutside = false;

        Queue<Position> toCheck = new LinkedList<>();
        toCheck.add(p.clone());

        while(!toCheck.isEmpty()) {
            Position checkP = toCheck.remove();
            if(!checkP.inBounds(map)) { // this in not in bounds
                isOutside = true;
            }
            else {
                Space s = map[(int) checkP.row][(int) checkP.col];
                if (!s.isDug && !checkedMap[(int) checkP.row][(int) checkP.col]) { // the space is empty and unchecked
                    toFill.add(checkP);
                    checkedMap[(int) checkP.row][(int) checkP.col] = true;
                    for (Direction d : Direction.values()) {
                        Position newP = checkP.clone();
                        newP.move(d);
                        toCheck.add(newP);
                    }
                }
            }
        }

        for(Position fillP : toFill) {
            if(isOutside) map[(int) fillP.row][(int) fillP.col].isNotDug = true;
            else map[(int) fillP.row][(int) fillP.col].isDug = true;
        }
    }
}
