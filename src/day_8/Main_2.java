package day_8;

import java.io.File;
import java.util.*;

public class Main_2 {

    public static void main(String[] args) throws Exception{

        Scanner s = new Scanner(new File("data/puzzle_8_data.txt"));

        // moves[] is an array containing every move, false for left, true for right
        char[] firstLineChars = s.nextLine().toCharArray();
        boolean moves[] = new boolean[firstLineChars.length];
        for(int i = 0; i < firstLineChars.length; i++) {
            moves[i] = firstLineChars[i] == 'R';
        }

        s.nextLine();

        // find all the starting locations integrated into the loading of the map
        ArrayList<String> startingLocations = new ArrayList<>();

        // map contains strings that map to the next locations
        HashMap<String, MapPair> map = new HashMap<>();
        while(s.hasNextLine()) {
            String line = s.nextLine();
            String key = line.substring(0, 3);
            String left = line.substring(7, 10);
            String right = line.substring(12, 15);

            if(key.charAt(2) == 'A') startingLocations.add(key);

            map.put(key, new MapPair(left, right));
        }

        System.out.println(startingLocations);

        // traverse the map!
        String[] locations = startingLocations.toArray(new String[0]);

        int[][] preCyclePatterns = new int[locations.length][];
        int[] cycleStarts = new int[locations.length];
        int[][] cyclePatterns = new int[locations.length][];

        for(int locationsIndex = 0; locationsIndex < locations.length; locationsIndex++) { // for every location
            HashMap<String, Integer> cycleLog = new HashMap<>();
            ArrayList<Integer> cyclePattern = new ArrayList<>();
            ArrayList<Integer> preCyclePattern = new ArrayList<>();

            String currentKey = locations[locationsIndex];
            int steps = 0;
            int lastZFound = 0;

            int cycleLength = -1;

            boolean foundRegularCycle = false;
            boolean foundFullCycle = false;

            while(true) { // following the map
                if(!foundRegularCycle && cycleLog.containsKey(currentKey)) {
                    foundRegularCycle = true;
                    cycleLength = steps - cycleLog.get(currentKey);
                }
                if(foundRegularCycle && currentKey.charAt(2) == 'Z' && cycleLog.containsKey(currentKey)) { // found loop in the map case, repeat the loop or kill
                    if(!foundFullCycle) {
                        foundFullCycle = true;
                        cycleLog.clear();
                        lastZFound = steps;
                        cycleStarts[locationsIndex] = steps - cycleLength;

                        for(int i = 0; i < preCyclePattern.size(); i++) { // clean up the pre cycle loop to only include pre cycle values
                            int value = preCyclePattern.get(i);
                            if(value >= steps - cycleLength) preCyclePattern.remove(i--);
                        }

                    }
                    else {
                        cyclePattern.add(steps - lastZFound);
                        break;
                    }
                }
                if(!foundFullCycle && currentKey.charAt(2) == 'Z') {
                    preCyclePattern.add(steps);
                }
                cycleLog.put(currentKey, steps); // logs when this key was seen
                if (foundFullCycle && lastZFound != steps && currentKey.charAt(2) == 'Z') { // z found
                    cyclePattern.add(steps - lastZFound);
                    lastZFound = steps;
                }
                currentKey = map.get(currentKey).get(moves[steps++%moves.length]); // traverse the map
            }

            cyclePatterns[locationsIndex] = new int[cyclePattern.size()]; // move over cycle pattern
            for(int i = 0; i < cyclePattern.size(); i++) {
                cyclePatterns[locationsIndex][i] = cyclePattern.get(i);
            }
            preCyclePatterns[locationsIndex] = new int[preCyclePattern.size()];
            for(int i = 0; i < preCyclePattern.size(); i++) {
                preCyclePatterns[locationsIndex][i] = preCyclePattern.get(i);
            }

        }
        System.out.println(Arrays.deepToString(preCyclePatterns));
        System.out.println(Arrays.toString(cycleStarts));
        System.out.println(Arrays.deepToString(cyclePatterns));

        // from analysis before this point every loop found only had ony "end point" in the loop so the "cycle pattern" can be ignored
        long cycleLocation = cycleStarts[0];
        long cycleIncrement = cyclePatterns[0][0];

        /*
        from the output above i used an LCM calculator on the values 20221, 13019, 19667, 14681, 18559, 16897 to get the answer
        these numbers are the cycle lengths and each contain one z value
        every cycle start was roughly 2x the value of the length and the program found one "pre-cycle" value
        this program is not working evidently, but it's working enough
        it would have been cool if the solution worked out less nicely and edge cases were actually used
        my program was initially built to support this, but it turned out to not be necessary
         */

    }

}
