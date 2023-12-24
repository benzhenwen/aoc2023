package day_5;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main_2 {

    public static void main(String[] args) throws Exception {

        Long maxNum = Long.MAX_VALUE;

        Scanner s = new Scanner(new File("data/puzzle_5_data.txt"));

        String[] seeds = s.nextLine().split(":")[1].trim().split(" ");
        System.out.println(Arrays.toString(seeds));

        s.nextLine();

        // make maps
        ArrayList<Map> maps = new ArrayList<>();

        Map currentMap = new Map();
        while(s.hasNextLine()) {
            String line = s.nextLine().trim();
            String[] lineSplit = line.split(" ");
            if(lineSplit[0].isEmpty()) {
                maps.add(currentMap);
                currentMap = new Map();
            }
            else if(lineSplit.length == 3) {
                currentMap.Ranges.add(new Range(
                        Long.parseLong(lineSplit[0]),
                        Long.parseLong(lineSplit[1]),
                        Long.parseLong(lineSplit[2])
                ));
            }
        }
        maps.add(currentMap);

        System.out.println(maps.size());

        for(int i = 0; i < seeds.length; i+=2) {
            System.out.println("seed set " + i);

            Long start = Long.parseLong(seeds[i]);
            Long length = Long.parseLong(seeds[i+1]);

            ArrayList<MinMax> temp = new ArrayList<>();
            temp.add(new MinMax(start, start+length));

            Iterable<MinMax> ranges = temp;

            for(Map m : maps) {
                ranges = m.getCriticalRanges(ranges);
                System.out.println("map parsed");
            }

            Long minValue = Long.MAX_VALUE;
            for(MinMax r : ranges) {
                if(r.min < minValue) minValue = r.min;
            }

            System.out.println("---------------------- output " + minValue);

            if(maxNum > minValue) maxNum = minValue;
        }

        System.out.println("output: " + maxNum);

    }

}

