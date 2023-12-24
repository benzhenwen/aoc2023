package day_5;

import java.io.File;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {

        Long maxNum = Long.MAX_VALUE;

        Scanner s = new Scanner(new File("data/puzzle_5_data.txt"));

        String[] seeds = s.nextLine().split(":")[1].trim().split(" ");

        s.nextLine();

        // make maps
        ArrayList<Map> maps = new ArrayList<>();

        Map currentMap = new Map();
        while(s.hasNextLine()) {
            String line = s.nextLine().trim();
            String[] lineSplit = line.split(" ");
            if(lineSplit[0].equals("")) {
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

        for(String seed : seeds) {
            Long se = Long.parseLong(seed);

            for(Map m : maps) {
                se = m.getOutput(se);
            }

            if(se < maxNum) maxNum = se;
        }

        System.out.println(maxNum);

    }

}
