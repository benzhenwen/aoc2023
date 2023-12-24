package day_6;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {

        Scanner s = new Scanner(new File("data/puzzle_6_data.txt"));

        String[] raceTimes = s.nextLine().split(":")[1].replaceAll("\\s+", " ").trim().split(" ");
        String[] recordTimes = s.nextLine().split(":")[1].replaceAll("\\s+", " ").trim().split(" ");

        long output = 1;

        for(int raceNum = 0; raceNum < raceTimes.length; raceNum++) {
            output *= totalRaceSolutions(Integer.parseInt(raceTimes[raceNum]), Integer.parseInt(recordTimes[raceNum]));
        }

        System.out.println(output);

    }

    private static int totalRaceSolutions(int length, int record) {
        int totalWins = 0;
        boolean hasWonBefore = false;
        for(int i = 0; ; i++) {
            int distance = i * (length-i);
            if(distance > record) {
                hasWonBefore = true;
                totalWins++;
            }
            else if(hasWonBefore) return totalWins;
        }
    }

}
