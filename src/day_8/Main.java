package day_8;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception{

        Scanner s = new Scanner(new File("data/puzzle_8_data.txt"));

        // moves[] is an array containing every move, false for left, true for right
        char[] firstLineChars = s.nextLine().toCharArray();
        boolean moves[] = new boolean[firstLineChars.length];
        for(int i = 0; i < firstLineChars.length; i++) {
            moves[i] = firstLineChars[i] == 'R';
        }

        s.nextLine();

        // map contains strings that map to the next locations
        HashMap<String, MapPair> map = new HashMap<>();
        while(s.hasNextLine()) {
            String line = s.nextLine();
            String key = line.substring(0, 3);
            String left = line.substring(7, 10);
            String right = line.substring(12, 15);

            map.put(key, new MapPair(left, right));
        }

        // traverse the map!
        int steps = 0;
        String currentLocation = "AAA";

        while(!currentLocation.equals("ZZZ")) currentLocation = map.get(currentLocation).get(moves[steps++%moves.length]);

        System.out.println(steps);
    }

}
