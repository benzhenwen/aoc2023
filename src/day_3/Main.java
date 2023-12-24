package day_3;

import java.io.File;
import java.util.HashSet;
import java.util.Scanner;

public class Main {

    private static final int[][] adjacentOffsets = {
            {-1, -1}, {-1,  0}, {-1,  1},
            { 0, -1},           { 0,  1},
            { 1, -1}, { 1,  0}, { 1,  1}
    };

    private static Cell[][] board;

    public static void main(String[] args) throws Exception{

        board = new Cell[140][140];

        // assemble board
        Scanner s = new Scanner(new File("data/puzzle_3_data.txt"));

        int row = 0;
        while(s.hasNext()) {
            int col = 0;
            for(char c : s.nextLine().toCharArray()) {
                board[row][col] = new Cell(c);
                col++;
            }
            row++;
        }

        // sum
        int sum = 0;

        // go over board
        for(row = 0; row < board.length; row++) {
            for(int col = 0; col < board.length; col++) {
                Cell currentCell = board[row][col];
                if(currentCell.isSymbol()) {
                    for(int offset = 0; offset < adjacentOffsets.length; offset++) {
                        int checkRow = row + adjacentOffsets[offset][0];
                        int checkCol = col + adjacentOffsets[offset][1];
                        if(checkRow >= 0 && checkRow < board.length && checkCol >= 0 && checkCol < board[0].length) { // inbounds
                            if(board[checkRow][checkCol].isInteger()) {
                                sum += getFullInt(checkRow, checkCol);
                            }
                        }
                    }
                }
            }
        }

        System.out.println(sum);
    }

    private static final HashSet<String> seenNumbers = new HashSet<>();

    private static int getFullInt(int row, int col) {

        // seek leftmost value
        while(col >= 0 && board[row][col].isInteger()) col--;
        col++;

        // don't recount numbers
        String coordinate = row + " " + col;
        if(seenNumbers.contains(coordinate)) return 0;
        seenNumbers.add(coordinate);

        // seek all the way right
        String number = "";
        while(col < board[0].length && board[row][col].isInteger()) {
            number += board[row][col].getSymbol();
            col++;
        }

        return Integer.parseInt(number);
    }

}
