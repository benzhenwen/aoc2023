package day_6;

import java.io.File;
import java.util.Scanner;

public class Main_2 {

    public static void main(String[] args) throws Exception {

        Scanner s = new Scanner(new File("data/puzzle_6_data.txt"));

        long raceTime = Long.parseLong(s.nextLine().split(":")[1].replaceAll("\\s+", "").trim());
        long recordTime = Long.parseLong(s.nextLine().split(":")[1].replaceAll("\\s+", "").trim());

        System.out.println(totalRaceSolutions(raceTime, recordTime));

    }

    private static long totalRaceSolutions(long length, long record) {
        long totalWins = 0;
        boolean hasWonBefore = false;
        for(int i = 0; ; i++) {
            long distance = i * (length-i);
            if(distance > record) {
                hasWonBefore = true;
                totalWins++;
            }
            else if(hasWonBefore) return totalWins;
        }
    }

}
