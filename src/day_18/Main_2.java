package day_18;

import java.io.File;
import java.util.*;

import day_18.Main.Direction;

public class Main_2 {

    public static void main(String[] args) throws Exception {

        Scanner s = new Scanner(new File("data/puzzle_18_data.txt"));

        ArrayList<Instruction> instructions = new ArrayList<>();

        // load the file
        while(s.hasNextLine()) {
            String[] line = s.nextLine().split(" ");

            Direction d = stringToDirection(line[2].substring(7, 8));
            int length = Integer.parseInt(line[2].substring(2, 7), 16);

            instructions.add(new Instruction(d, length));
        }
        s.close();

        // get the bounds of the map
        long minRow = 0, maxRow = 0, minCol = 0, maxCol = 0;

        Position currentPosition = new Position();

        ArrayList<VerticalLine> dividers = new ArrayList<>(400);
        ArrayList<Long> criticalRows = new ArrayList<>(400);

        for(Instruction i : instructions) {
            if(isVertical(i.d)) { // here we need to create a vertical line
                long col = currentPosition.col;
                long startRow = currentPosition.row;
                currentPosition.move(i.d, i.length); // does movement
                long endRow = currentPosition.row;
                dividers.add(new VerticalLine(col, startRow, endRow));
            }
            else { // here we need to create a horizontal "line"
                if(!criticalRows.contains(currentPosition.row)) criticalRows.add(currentPosition.row);
                currentPosition.move(i.d, i.length); // does movement
            }

        }

        System.out.println(dividers);
        System.out.println(criticalRows);

        // the horizontal perimeter for each critical rows is stored here. it will the grater of the calculated lengths of the adjacent "rectangles"
        HashMap<Long, Long> horizontalPerimeters = new HashMap<>();

        Collections.sort(dividers);
        Collections.sort(criticalRows); // sort these so we can check top to bottom / left to right

        long totalArea = 0;

        long previousCriticalRow = criticalRows.get(0);

        for(int i = 1; i < criticalRows.size(); i++) {

            long aboveHorizontalPerimeter = previousCriticalRow;
            long rowToCheck = previousCriticalRow + 1; // don't want to hit edge of vertical lines

            previousCriticalRow = criticalRows.get(i);
            long belowHorizontalPerimeter = previousCriticalRow;
            long rowInterval = previousCriticalRow - rowToCheck; // the "size" of this group of rows, does not account for the values precisely on the horizontal lines

            long totalLengthInsideRow = 0;
            // for every divider one will "start" being inside the bounds and one will "end"
            // there will only ever be an even number of dividers assuming a full loop
            boolean parity = false;
            long previousDividerCol = -1;

            for(VerticalLine divider : dividers) {
                if(divider.inRange(rowToCheck)) { // this divider intercepts the row we are looking at
                    if(!parity) { // this divider marks the beginning of a section
                        previousDividerCol = divider.col;
                    }
                    else { // this divider marks the end of a section
                        totalLengthInsideRow += divider.col - previousDividerCol + 1; // +1 count the edges
                    }
                    parity = !parity; // flip parity
                }
            }

            // the rectangle representing the area within the interval and within bounds of the dividers
            totalArea += totalLengthInsideRow * rowInterval;

            System.out.println(totalLengthInsideRow);

            // update the perimeters
            long currentAbovePerimeter = -1, currentBelowPerimeter = -1;
            if(horizontalPerimeters.containsKey(aboveHorizontalPerimeter)) currentAbovePerimeter = horizontalPerimeters.get(aboveHorizontalPerimeter);
            if(horizontalPerimeters.containsKey(belowHorizontalPerimeter)) currentBelowPerimeter = horizontalPerimeters.get(belowHorizontalPerimeter);

            if(currentAbovePerimeter < totalLengthInsideRow) horizontalPerimeters.put(aboveHorizontalPerimeter, totalLengthInsideRow);
            if(currentBelowPerimeter < totalLengthInsideRow) horizontalPerimeters.put(belowHorizontalPerimeter, totalLengthInsideRow);

        }

        for(Long k : horizontalPerimeters.keySet()) {
            System.out.println(k + " -> " + horizontalPerimeters.get(k));
        }

        for(Long horizontalPerimeter : horizontalPerimeters.values()) {
            totalArea += horizontalPerimeter;
        }

        System.out.println(totalArea);

    }

    public static Direction stringToDirection(String input) {
        switch(input) {
            case "3" -> {return Direction.UP;}
            case "1" -> {return Direction.DOWN;}
            case "2" -> {return Direction.LEFT;}
            case "0" -> {return Direction.RIGHT;}
        }
        throw new IllegalArgumentException("not a valid direction string");
    }

    public static boolean isVertical(Direction d) {
        return d == Direction.UP || d == Direction.DOWN;
    }
}
